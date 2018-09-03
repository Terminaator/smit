package secret.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import secret.data.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
