package com.app.network.networkapplication.service;


import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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

    public List<Person> getPeople(int pageNum, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Person> page = personRepository.findAll(pageRequest);
        return page.stream().toList();
    }

    public List<Person> getFriendsWithinRange(Long id, int range) {
        try {
            List<Person> people = personRepository.findFriendsWithinDepth(id, range);
            return people;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
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

    public List<Person> savePeople(List<Person> people) {
        try {
            List<Person> savedPeople = personRepository.saveAll(people);
            return savedPeople;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Person> generateRelationships(int min, int max) {
        Random rand = new Random();
        int pageNum = 0;
        Page<Person> page = null;
        do {
            PageRequest pageable = PageRequest.of(pageNum, 10);
            page = personRepository.findAll(pageable);

            int relations;
            for (Person person : page.get().toList()) {
                relations = rand.nextInt(min, max);
                List<Person> newFriends = personRepository.findRandomPeopleById(person.getId(), relations);
                person.friendsWith(newFriends);
                personRepository.save(person);
            }
            pageNum++;
        } while (pageNum < page.getTotalPages());
        return personRepository.findAll();
    }

    public void deletePeople() {
        personRepository.deleteAll();
    }

}
