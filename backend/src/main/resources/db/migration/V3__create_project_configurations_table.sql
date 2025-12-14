CREATE TABLE project_configurations (
    id BIGSERIAL PRIMARY KEY,
    project_id BIGINT NOT NULL,
    is_public BOOLEAN NOT NULL DEFAULT TRUE,
    max_participants INTEGER NOT NULL DEFAULT 10,
    background_image VARCHAR(500),
    CONSTRAINT fk_project_config_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    CONSTRAINT uk_project_config_project UNIQUE (project_id)
);