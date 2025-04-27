package com.app.network.networkapplication.repository;

import com.app.network.networkapplication.model.Person;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepositoryCustom {

    List<Person> findFriendsWithinDepth(Long id, int range);
}