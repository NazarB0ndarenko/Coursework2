CREATE TABLE project_members (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    project_id BIGINT NOT NULL,
    project_role VARCHAR(50) NOT NULL DEFAULT 'USER',
    join_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_project_member_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    CONSTRAINT fk_project_member_project FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE
);