package com.aji.agent.service;

import com.aji.agent.entity.AttendanceRecord;
import com.aji.agent.entity.Timetable;
import com.aji.agent.repository.TimetableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentService {

    private final AttendanceService attendanceService;
    private final TimetableRepository timetableRepo;
    private final OpenAIService aiService;

    public AgentService(AttendanceService attendanceService,
                        TimetableRepository timetableRepo,
                        OpenAIService aiService) {
        this.attendanceService = attendanceService;
        this.timetableRepo = timetableRepo;
        this.aiService = aiService;
    }

    public String generateRecommendation(List<String> subjects) {
        if (subjects == null || subjects.isEmpty()) {
            return "No subjects provided for recommendation.";
        }

        // -------------------------------
        // Step 1: Build overall attendance
        // -------------------------------
        StringBuilder attendanceSummary = new StringBuilder();
        for (String subject : subjects) {
            double percent = attendanceService.getAttendancePercent(subject);
            attendanceSummary.append(subject)
                    .append(" : ")
                    .append(String.format("%.2f", percent))
                    .append("%\n"); // safe literal %
        }

        // -------------------------------
        // Step 2: Build timetable info
        // -------------------------------
        StringBuilder timetableStr = new StringBuilder();
        timetableRepo.findAll()
                .stream()
                .filter(t -> subjects.contains(t.getSubjectName()))
                .sorted((a, b) -> {
                    int dayCompare = a.getDay().compareTo(b.getDay());
                    return dayCompare != 0 ? dayCompare : a.getTime().compareTo(b.getTime());
                })
                .forEach(t -> timetableStr.append(t.getDay())
                        .append(" | ")
                        .append(t.getTime())
                        .append(" | ")
                        .append(t.getSubjectName())
                        .append("\n"));

        // -------------------------------
        // Step 3: Build detailed attendance per class
        // -------------------------------
        StringBuilder detailedAttendance = new StringBuilder();
        for (String subject : subjects) {
            List<AttendanceRecord> records = attendanceService.getRecordsBySubject(subject);
            for (AttendanceRecord r : records) {
                detailedAttendance.append(r.getDay())
                        .append(" | ")
                        .append(r.getTime())
                        .append(" | ")
                        .append(subject)
                        .append(" | Present: ")
                        .append(r.isPresent() ? "Yes" : "No")
                        .append("\n");
            }
        }

        // -------------------------------
        // Step 4: Build explicit AI prompt
        // -------------------------------
        String prompt = "You are a smart attendance advisor. Based on the information below, provide detailed recommendations for the student. " +
                "For each subject, indicate which sessions (day + time) must be attended, which can be skipped safely, and any sessions at risk.\n\n" +

                "Attendance summary (overall % per subject):\n" + attendanceSummary + "\n" +
                "Timetable (subject-specific schedule):\n" + timetableStr + "\n" +
                "Detailed Attendance (per day and time):\n" + detailedAttendance + "\n" +

                "Rules:\n" +
                "1. Minimum attendance is 75%\n" +
                "2. Suggest which classes must be attended urgently (mention day and time)\n" +
                "3. Suggest which classes can be skipped safely (mention day and time)\n" +
                "4. Warn if any subject/session is at risk\n" +
                "5. Format the output in Markdown with sections for each subject";

        // -------------------------------
        // Step 5: Call AI service
        // -------------------------------
        return aiService.getAIResponse(prompt);
    }
}
