package com.app.network.networkapplication.controller;


import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/person")
public class PersonController {

    PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    private final Logger log = LoggerFactory.getLogger(PersonController.class);

    @GetMapping("/{name}")
    public ResponseEntity<Person> getPersonWithName(@PathVariable("name") String name) {
        ResponseEntity<Person> personResponseEntity;
        try {
            Person person = personService.findPersonByName(name);
            personResponseEntity = ResponseEntity.ok(person);
        } catch (Exception e) {
            personResponseEntity = ResponseEntity.notFound().build();
        }
        return personResponseEntity;
    }

    @PostMapping()
    public ResponseEntity<Person> savePerson(@RequestBody Person person) {
        ResponseEntity<Person> response;
        try {
            Person savedPerson = personService.savePerson(person);
            log.info("{}", savedPerson);
            response = ResponseEntity.ok(savedPerson);
        } catch (Exception e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }
}