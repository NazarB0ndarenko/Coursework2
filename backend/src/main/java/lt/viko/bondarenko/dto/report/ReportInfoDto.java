package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.dto.user.IdNameDto;

import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportInfoDto {
    private long id;
    private IdNameDto reporter;
    private IdNameDto accused;
    private ReportType reportType;
    private List<String> evidencesUrls;
    private LocalDateTime createdAt;
    private ReportStatus reportStatus;
}
