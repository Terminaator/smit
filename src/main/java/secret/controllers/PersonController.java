package secret.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import secret.data.Person;
import secret.data.dto.PersonDTO;
import secret.repository.PersonRepository;
import secret.service.PersonService;

import java.sql.SQLIntegrityConstraintViolationException;

@Controller
public class PersonController {
    private final PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(value = "/addPerson", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addPerson(@RequestBody PersonDTO personDTO){
        try {
            Person person = new Person();
            person.setRealName(personDTO.getRealName());
            person.setSecretName(personDTO.getSecretName());
            person.setNumber(personDTO.getNumber());
            personService.addPerson(person);
            return new ResponseEntity(person,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPersons(){
        return ResponseEntity.ok(new Gson().toJson(personService.getAllPersons()));
    }

}
