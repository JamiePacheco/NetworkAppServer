package com.app.network.networkapplication.repository;

import com.app.network.networkapplication.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findByName(String name);
    List<Person> findByFriendsName(String name);
}
