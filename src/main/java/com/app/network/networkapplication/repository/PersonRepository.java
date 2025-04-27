package com.app.network.networkapplication.repository;

import com.app.network.networkapplication.model.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long>, PersonRepositoryCustom{
    Person findByName(String name);
    List<Person> findByFriendsName(String name);

    @Query("""
            MATCH (p:Person) WHERE NOT id(p) = $id
            RETURN p 
            ORDER BY rand() 
            LIMIT $limit
    """)
    List<Person> findRandomPeopleById(@Param("id") Long id, @Param("limit") int limit);

}
