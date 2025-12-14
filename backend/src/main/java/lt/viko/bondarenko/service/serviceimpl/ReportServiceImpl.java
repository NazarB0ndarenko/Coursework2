package lt.viko.bondarenko.service.serviceimpl;

import lt.viko.bondarenko.dto.report.InternalReportDto;
import lt.viko.bondarenko.dto.report.ReportInfoDto;
import lt.viko.bondarenko.dto.report.FilterDto;
import lt.viko.bondarenko.dto.report.ReportSearchDto;
import lt.viko.bondarenko.dto.report.ShortReportInfoDto;
import lt.viko.bondarenko.entity.Evidence;
import lt.viko.bondarenko.entity.Report;
import lt.viko.bondarenko.entity.User;
import lt.viko.bondarenko.enums.report.ReportStatus;
import lt.viko.bondarenko.enums.report.ReportType;
import lt.viko.bondarenko.exception.ReportNotFoundException;
import lt.viko.bondarenko.mappper.ReportMapper;
import lt.viko.bondarenko.repository.ReportRepository;
import lt.viko.bondarenko.service.ReportService;
import lt.viko.bondarenko.util.ReportSearchHelper;
import lt.viko.bondarenko.util.ReportSpecification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;

    @Override
    public void createReport(long reporterId, long accusedId, ReportType reportType, List<Evidence> evidences){
        Report report = Report.builder()
                .reporter(new User(reporterId))
                .accused(new User(accusedId))
                .reportType(reportType)
                .reportStatus(ReportStatus.OPEN)
                .evidences(evidences)
                .build();

        reportRepository.save(report);

        log.info("report {} created", report.getId());
    }

    @Override
    public List<ShortReportInfoDto> getReports(ReportSearchDto reportSearchDto) {
        log.info("checking dto for obligatory fields, set defaults if necessary");
        ReportSearchHelper.applyDefaults(reportSearchDto);

        log.info("transforming dto into search criteria");

        FilterDto filterDto = new FilterDto(
                reportSearchDto.getReporterId(),
                reportSearchDto.getAccusedId(),
                reportSearchDto.getReportType(),
                reportSearchDto.getReportStatus());

        Sort.Direction direction = reportSearchDto.getSortOrder().equalsIgnoreCase("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, reportSearchDto.getSortBy());

        PageRequest pageRequest = PageRequest.of(reportSearchDto.getPage(), reportSearchDto.getSize(), sort);
        Specification<Report> specification = new ReportSpecification(filterDto);

        log.info("searching for reports");

        return reportRepository.findAll(specification, pageRequest)
                .map(reportMapper::toShortReportInfoDto)
                .getContent();
    }

    @Override
    public ReportInfoDto getReportDetail(long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("report not found"));

        return reportMapper.toReportInfoDto(report);
    }

    @Override
    public InternalReportDto accusedIdAndReportReason(long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("report not found"));

        return new InternalReportDto(report.getAccused().getId(), report.getAccused().getEmail(), report.getReportType());
    }

    @Override
    public void updateReportInfo(long reportId, ReportStatus reportStatus) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new ReportNotFoundException("report not found"));

        report.setReportStatus(reportStatus);
        reportRepository.save(report);

        log.info("report {} updated", report.getId());
    }
}
