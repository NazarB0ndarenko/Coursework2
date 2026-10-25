package lt.viko.bondarenko.repository;

import java.util.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import lt.viko.bondarenko.entity.ProjectMember;
import lt.viko.bondarenko.entity.User;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    Long countAllByUserId(Long userId);

    Long countAllByUserIdAndProjectRole(Long userId, ProjectMember.Role role);

    Long countAllByProjectId(Long projectId);

    Boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    Boolean existsByUserIdAndProjectIdAndProjectRole(Long userId, Long projectId, ProjectMember.Role role);

    Page<ProjectMember> findByProjectId(Long projectId, Pageable pageable);

    List<ProjectMember> findByProjectIdAndProjectRole(Long projectId, ProjectMember.Role role);

    Optional<ProjectMember> findByUserIdAndProjectId(Long userId, Long projectId);

    @Query("SELECT pm.user FROM ProjectMember pm WHERE pm.project.id = :projectId")
    List<User> findUsersByProjectId(Long projectId);
}
