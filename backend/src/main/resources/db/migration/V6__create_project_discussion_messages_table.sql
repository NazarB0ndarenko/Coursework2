CREATE TABLE project_discussion_messages (
    id BIGSERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL,
    posted_by BIGINT NOT NULL,
    discussion_id BIGINT NOT NULL,
    CONSTRAINT fk_disc_msg_posted_by FOREIGN KEY (posted_by) REFERENCES project_members(id) ON DELETE CASCADE,
    CONSTRAINT fk_disc_msg_discussion FOREIGN KEY (discussion_id) REFERENCES project_discussions(id) ON DELETE CASCADE
);