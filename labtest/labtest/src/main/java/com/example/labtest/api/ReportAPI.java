package com.example.labtest.api;

import com.example.labtest.DTO.DepartmentSummaryDTO;
import com.example.labtest.DTO.StudentTranscriptDTO;
import com.example.labtest.DTO.CourseRosterDTO;
import com.example.labtest.DTO.TopPerformerDTO;
import com.example.labtest.service.ReportService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportAPI {

    private ReportService reportservice;

    public ReportAPI(ReportService reportservice) {
        this.reportservice = reportservice;
    }

    // 1. STUDENT TRANSCRIPT
    @GetMapping("/students/{id}/transcript")
    public StudentTranscriptDTO getTranscript(
            @PathVariable int id) {

        return reportservice.getTranscript(id);
    }

    // 2. COURSE ROSTER
    @GetMapping("/courses/{id}/roster")
    public CourseRosterDTO getRoster(
            @PathVariable int id) {

        return reportservice.getRoster(id);
    }

    // 3. DEPARTMENT SUMMARY
    @GetMapping("/departments/summary")
    public List<DepartmentSummaryDTO> getDepartmentSummary() {

        return reportservice.getDepartmentSummary();
    }

    // 4. TOP PERFORMERS
    @GetMapping("/top-performers")
    public List<TopPerformerDTO> getTopPerformers(
            @RequestParam(defaultValue = "5") int limit) {

        return reportservice.getTopPerformers(limit);
    }
}