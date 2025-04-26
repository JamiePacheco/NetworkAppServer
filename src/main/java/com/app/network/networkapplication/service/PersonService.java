package com.app.network.networkapplication.service;


import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    private final Logger log = LoggerFactory.getLogger(PersonService.class);

    public Person findPersonByName(String name) {
        if (name == null || name.equals("")) { return null; }

        Person personWithName = this.personRepository.findByName(name);

        return personWithName;
    }

    public Person savePerson(Person person) {
        try {
            Person savedPerson = personRepository.save(person);
            return savedPerson;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

}
