package lt.viko.bondarenko.service;

import lt.viko.bondarenko.enums.report.ReportType;

public interface MailSendingService {
    void sendCode(String toEmail, String code);

    void sendBanNotification(String toEmail, ReportType reportType);
}
