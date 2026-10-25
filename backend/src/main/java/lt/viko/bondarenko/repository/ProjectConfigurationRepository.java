package lt.viko.bondarenko.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import lt.viko.bondarenko.entity.ProjectConfiguration;

public interface ProjectConfigurationRepository extends JpaRepository<ProjectConfiguration, Long> {
}
