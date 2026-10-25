package lt.viko.bondarenko.controller;

import lombok.extern.slf4j.Slf4j;
import lt.viko.bondarenko.dto.report.CreateReportDto;
import lt.viko.bondarenko.entity.Evidence;
import lt.viko.bondarenko.service.EvidenceService;
import lt.viko.bondarenko.service.ReportService;
import lt.viko.bondarenko.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
@Tag(name = "Reports", description = "Requires User role")
@Slf4j
public class ReportController {

    public final UserService userService;
    public final ReportService reportService;
    public final EvidenceService evidenceService;

    @Operation(
            summary = "Submit a Report",
            description = "Allows users to submit reports with up to 3 attached files (which will be stored on S3), each file up to 5MB in size. " +
                    "allowed file types: .jpeg, .jpg, .png, .pdf" +
                    "Note: Swagger may not support multiple file attachments."
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createReport(@ModelAttribute CreateReportDto createReportDto) {
        String reporterEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        long reporterId = userService.getUserIdByEmail(reporterEmail);
        long accusedId = userService.getUserIdByEmail(createReportDto.getAccused());
        log.info("Saving to s3");
        List<Evidence> evidences = createReportDto.getEvidences().stream()
                .map(evidenceService::saveEvidence)
                .toList();
        log.info("Saved succsesfuly");
        reportService.createReport(reporterId, accusedId, createReportDto.getReportType(), evidences);

        return ResponseEntity.ok("Report was created successfully");
    }
}
