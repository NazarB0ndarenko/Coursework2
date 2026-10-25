CREATE TABLE member_task_assignment (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT NOT NULL,
    task_id BIGINT NOT NULL,
    assigned_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_assignment_member FOREIGN KEY (member_id) REFERENCES project_members(id) ON DELETE CASCADE,
    CONSTRAINT fk_assignment_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);