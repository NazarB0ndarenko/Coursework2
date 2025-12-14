package lt.viko.bondarenko.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.viko.bondarenko.entity.ProjectInteraction;

public interface ProjectInteractionRepository extends JpaRepository<ProjectInteraction, Long> {
        List<ProjectInteraction> findAllByUserIdAndTypeAndStatus(Long userId, ProjectInteraction.Type type,
                        ProjectInteraction.Status status);

        List<ProjectInteraction> findAllByProjectIdAndTypeAndStatus(Long projectId, ProjectInteraction.Type type,
                        ProjectInteraction.Status status);

        Boolean existsByUserIdAndProjectIdAndStatus(Long userId, Long ProjectId, ProjectInteraction.Status status);
}