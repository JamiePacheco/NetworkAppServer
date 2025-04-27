package com.app.network.networkapplication.repository;

import com.app.network.networkapplication.model.Person;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonRepositoryCustomImpl implements PersonRepositoryCustom {

    private Neo4jTemplate neo4jTemplate;

    public PersonRepositoryCustomImpl(Neo4jTemplate neo4jTemplate) {
        this.neo4jTemplate = neo4jTemplate;
    }

    @Override
    public List<Person> findFriendsWithinDepth(Long id, int range) {
        String query = String.format("""
                    MATCH (start:Person)-[:FRIENDS*1..%d]-(friend)
                    WHERE ID(start) = %d
                    RETURN DISTINCT friend
                """, range, id);

        return this.neo4jTemplate.findAll(query, Person.class);
    }
}
