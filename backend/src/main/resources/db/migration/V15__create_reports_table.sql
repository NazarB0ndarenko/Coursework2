CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    reporter_user_id BIGINT,
    accused_user_id BIGINT,
    report_type VARCHAR(50),
    created_at TIMESTAMP NOT NULL,
    status VARCHAR(50),
    CONSTRAINT fk_report_reporter FOREIGN KEY (reporter_user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    CONSTRAINT fk_report_accused FOREIGN KEY (accused_user_id) REFERENCES users(user_id) ON DELETE SET NULL
);