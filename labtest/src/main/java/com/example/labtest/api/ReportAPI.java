package com.example.labtest.api;

import com.example.labtest.DTO.CourseRosterDTO;
import com.example.labtest.DTO.DepartmentSummaryDTO;
import com.example.labtest.DTO.StudentTranscriptDTO;
import com.example.labtest.DTO.TopPerformerDTO;
import com.example.labtest.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/reports")
public class ReportAPI {

        private ReportService reportService;

        public ReportAPI(ReportService reportService) {
            this.reportService = reportService;
        }

        // Student transcript
        @GetMapping("/students/{id}/transcript")
        public StudentTranscriptDTO transcript(
                @PathVariable int id) {

            return reportService.getTranscript(id);
        }

        // Course roster
        @GetMapping("/courses/{id}/roster")
        public CourseRosterDTO roster(
                @PathVariable int id) {

            return reportService.getRoster(id);
        }

        // Department summary
        @GetMapping("/departments/summary")
        public List<DepartmentSummaryDTO> departmentSummary() {

            return reportService.getDepartmentSummary();
        }

        // Top performers
        @GetMapping("/top-performers")
        public List<TopPerformerDTO> topPerformers(
                @RequestParam(defaultValue = "5") int limit) {

            return reportService.getTopPerformers(limit);
        }
}
