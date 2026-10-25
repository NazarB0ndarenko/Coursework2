package lt.viko.bondarenko.dto.report;

import lt.viko.bondarenko.enums.UserPunishment;
import lt.viko.bondarenko.enums.report.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReportValidationResultDto {
    @NonNull
    private Long reportId;
    @NonNull
    private ReportStatus reportStatus;
    @NonNull
    private UserPunishment userPunishment;
}
