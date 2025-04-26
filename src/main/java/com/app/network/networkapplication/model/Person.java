package com.app.network.networkapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@Node("Person")
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "FRIENDS", direction = Relationship.Direction.OUTGOING)
    public Set<Person> friends;

    public void friendsWith(Person person) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(person);
    }

    // required by Neo4j API for some reason...
    private Person() {}





}
