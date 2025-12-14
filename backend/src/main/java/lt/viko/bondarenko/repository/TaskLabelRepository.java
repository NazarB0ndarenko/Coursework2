package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.TaskLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskLabelRepository extends JpaRepository<TaskLabel, Long> {
}
