package lt.viko.bondarenko.repository;

import lt.viko.bondarenko.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean countUserByEmail(@Param("email") String email);
}
