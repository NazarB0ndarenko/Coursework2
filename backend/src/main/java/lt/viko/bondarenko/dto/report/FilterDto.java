package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FilterDto {
    private Long reporterId;
    private Long accusedId;
    private ReportType reportType;
    private ReportStatus reportStatus;
}
