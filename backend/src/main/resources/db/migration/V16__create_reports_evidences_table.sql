CREATE TABLE reports_evidences (
    report_id BIGINT NOT NULL,
    evidences_id BIGINT NOT NULL,
    PRIMARY KEY (report_id, evidences_id),
    CONSTRAINT fk_report_evidence_report FOREIGN KEY (report_id) REFERENCES reports(id) ON DELETE CASCADE,
    CONSTRAINT fk_report_evidence_evidence FOREIGN KEY (evidences_id) REFERENCES evidences(id) ON DELETE CASCADE
);