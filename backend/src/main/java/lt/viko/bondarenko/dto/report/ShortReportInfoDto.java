package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.dto.user.IdNameDto;
import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShortReportInfoDto {
    private long id;
    private IdNameDto accused;
    private ReportType reportType;
    private ReportStatus reportStatus;
    private LocalDateTime createdAt;
}
