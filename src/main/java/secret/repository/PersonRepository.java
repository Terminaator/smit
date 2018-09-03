package secret.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import secret.data.Person;


@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findBySecretName(String name);
}
