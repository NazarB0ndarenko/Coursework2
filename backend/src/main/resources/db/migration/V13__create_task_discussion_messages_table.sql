CREATE TABLE task_discussion_message (
    id BIGSERIAL PRIMARY KEY,
    author_id BIGINT NOT NULL,
    message TEXT NOT NULL,
    task_id BIGINT NOT NULL,
    posted_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_task_msg_author FOREIGN KEY (author_id) REFERENCES project_members(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_msg_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE
);