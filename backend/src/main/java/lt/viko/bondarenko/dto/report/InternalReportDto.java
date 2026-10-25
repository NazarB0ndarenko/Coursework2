package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.enums.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InternalReportDto {
    private long accusedId;
    private String accusedEmail;
    private ReportType reportType;
}
