package com.app.network.networkapplication.controller;


import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping()
    public ResponseEntity<Response<List<Person>>> getPeople(@RequestParam("page") int page, @RequestParam("size") int size) {
        ResponseEntity<Response<List<Person>>> personResponseEntity;
        try {
            List<Person> people = personService.getPeople(page, size);
            personResponseEntity = ResponseEntity.ok(
                    Response.<List<Person>>builder()
                            .message("Retrieved %d entries from page %d")
                            .status(HttpStatus.ACCEPTED)
                            .responseContent(people)
                            .build()
            );
        } catch (Exception e) {
            personResponseEntity = ResponseEntity.badRequest().build();
        }
        return personResponseEntity;
    }

    @GetMapping("/within-range")
    public ResponseEntity<Response<List<Person>>> getFriendsWithinRange(@RequestParam("id") Long id, @RequestParam("depth") int depth)  {
        ResponseEntity<Response<List<Person>>> responseEntity;
        try {
            List<Person> people = personService.getFriendsWithinRange(id, depth);
            responseEntity = ResponseEntity.ok(
                    Response.<List<Person>>builder()
                            .message("Got friends within depth " + depth)
                            .status(HttpStatus.ACCEPTED)
                            .responseContent(people)
                            .build()
            );
        } catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
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

    @PostMapping("/many")
    public ResponseEntity<List<Person>> savePeople(@RequestBody List<Person> people) {
        ResponseEntity<List<Person>> response;
        try {
            List<Person> savedPeople = personService.savePeople(people);
            response = ResponseEntity.ok(savedPeople);
        } catch (Exception e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PostMapping("/generate-relations")
    public ResponseEntity<List<Person>> generateRelations(@RequestParam(name = "min") int min, @RequestParam(name = "max") int max) {
        ResponseEntity<List<Person>> response;
        try {
            List<Person> builtRelations = personService.generateRelationships(min, max);
            response = ResponseEntity.ok(builtRelations);
        } catch (Exception e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PostMapping("/generate-group-memberships")
    public ResponseEntity<Response<String>> generateGroupMemberships(@RequestParam("min") int min, @RequestParam("max") int max) {
        ResponseEntity<Response<String>> response;
        try {
            personService.generateGroupMemberships(min, max);
            response = ResponseEntity.ok(
                    Response.<String>builder()
                            .message("Successfully generated mock memberships")
                            .status(HttpStatus.ACCEPTED)
                            .responseContent("Yippie")
                            .build()
            );
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body(
                    Response.<String>builder()
                            .message("Failure to generate memberships")
                            .status(HttpStatus.BAD_REQUEST)
                            .responseContent(e.getMessage())
                            .build()
            );
        }
        return response;
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllPeople() {
        ResponseEntity<String> response;
        try {
            personService.deletePeople();
            response = ResponseEntity.ok("Deleted all people");
        } catch (Exception e) {
            response = ResponseEntity.badRequest().body("Error deleting people: " + e.getMessage());
        }
        return response;
    }
}