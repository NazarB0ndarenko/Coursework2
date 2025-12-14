package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.MemberTaskAssignment;
import lt.viko.bondarenko.entity.ProjectMember;
import lt.viko.bondarenko.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface MemberTaskAssignmentRepository extends JpaRepository<MemberTaskAssignment, Long> {
    Optional<MemberTaskAssignment> findByTaskAndMember(Task task, ProjectMember member);
    boolean existsByTaskAndMember(Task task, ProjectMember member);
}