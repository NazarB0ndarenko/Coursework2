CREATE TABLE task_label_mapping (
    task_id BIGINT NOT NULL,
    label_id BIGINT NOT NULL,
    PRIMARY KEY (task_id, label_id),
    CONSTRAINT fk_task_label_task FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE,
    CONSTRAINT fk_task_label_label FOREIGN KEY (label_id) REFERENCES task_labels(id) ON DELETE CASCADE
);