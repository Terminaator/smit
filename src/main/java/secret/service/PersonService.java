package secret.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import secret.data.Person;
import secret.repository.PersonRepository;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    public void addPerson(Person person) throws SQLIntegrityConstraintViolationException {
        if (personRepository.findBySecretName(person.getSecretName()) != null) {
            throw new SQLIntegrityConstraintViolationException("Selle nimega on juba olemas");
        }
        personRepository.save(person);
    }
    public List<Person> getAllPersons(){
        return personRepository.findAll();
    }
}
