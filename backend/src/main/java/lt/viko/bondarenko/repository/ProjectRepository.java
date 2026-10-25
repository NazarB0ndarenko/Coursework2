package lt.viko.bondarenko.repository;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lt.viko.bondarenko.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Boolean existsByIdAndConfigIsPublicTrue(Long projectId);

    Long countAllByConfigIsPublicTrue();

    Page<Project> findAllByConfigIsPublicTrue(Pageable pageable);

    @Query("SELECT p FROM Project p JOIN p.members pm WHERE pm.user.id = :userId")
    Page<Project> findProjectsByUserId(@Param("userId") Long userId, Pageable pageable);
}