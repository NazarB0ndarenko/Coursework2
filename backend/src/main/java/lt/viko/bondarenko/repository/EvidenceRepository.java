package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.Evidence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

}
