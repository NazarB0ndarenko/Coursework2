package lt.viko.bondarenko.service;

import lt.viko.bondarenko.dto.report.InternalReportDto;
import lt.viko.bondarenko.dto.report.ReportInfoDto;
import lt.viko.bondarenko.dto.report.ReportSearchDto;
import lt.viko.bondarenko.dto.report.ShortReportInfoDto;
import lt.viko.bondarenko.entity.Evidence;
import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;

import java.util.List;

public interface ReportService {

    void createReport(long reporterId, long accusedId, ReportType reportType, List<Evidence> evidences);

    List<ShortReportInfoDto> getReports(ReportSearchDto reportSearchDto);

    ReportInfoDto getReportDetail(long reportId);

    InternalReportDto accusedIdAndReportReason(long reportId);

    void updateReportInfo(long reportId, ReportStatus reportStatus);
}
