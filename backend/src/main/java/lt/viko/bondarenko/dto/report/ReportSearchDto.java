package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportSearchDto {
    private Long reporterId;
    private Long accusedId;
    private ReportType reportType;
    private ReportStatus reportStatus;
    private String sortBy;
    private String sortOrder;
    private Integer page;
    private Integer size;
}
