CREATE TABLE project_discussions (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL,
    posted_by BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    CONSTRAINT fk_discussion_posted_by FOREIGN KEY (posted_by) REFERENCES project_members(id) ON DELETE CASCADE,
    CONSTRAINT fk_discussion_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);