package lt.viko.bondarenko.mappper;

import lt.viko.bondarenko.dto.report.ReportInfoDto;
import lt.viko.bondarenko.dto.report.ShortReportInfoDto;
import lt.viko.bondarenko.dto.user.IdNameDto;
import lt.viko.bondarenko.entity.Evidence;
import lt.viko.bondarenko.entity.Report;
import lt.viko.bondarenko.entity.User;

import java.util.List;

import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class ReportMapper {

    public ShortReportInfoDto toShortReportInfoDto(Report report) {
        if (report == null) {
            return null;
        }
        return new ShortReportInfoDto(
                report.getId(),
                mapUserToIdNameDto(report.getAccused()),
                report.getReportType(),
                report.getReportStatus(),
                report.getCreatedAt()
        );
    }

    public ReportInfoDto toReportInfoDto(Report report) {
        if (report == null) {
            return null;
        }
        return new ReportInfoDto(
                report.getId(),
                mapUserToIdNameDto(report.getReporter()),
                mapUserToIdNameDto(report.getAccused()),
                report.getReportType(),
                mapEvidencesToUrls(report.getEvidences()),
                report.getCreatedAt(),
                report.getReportStatus()
        );
    }

    private IdNameDto mapUserToIdNameDto(User user) {
        if (user == null) {
            return null;
        }
        return new IdNameDto(user.getId(), user.getFirstName() + " " + user.getLastName());
    }

    private List<String> mapEvidencesToUrls(List<Evidence> evidences) {
        if (evidences == null || evidences.isEmpty()) {
            return List.of();
        }
        return evidences.stream()
                .map(Evidence::getUrl)
                .collect(Collectors.toList());
    }
}

