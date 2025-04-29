package com.app.network.networkapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Node(primaryLabel = "Person")
@AllArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "username")
    private String username;

    @Relationship(type = "FRIENDS", direction = Relationship.Direction.OUTGOING)
    public Set<Person> friends;

    @Relationship(type = "MEMBER_OF", direction = Relationship.Direction.OUTGOING)
    public Set<Group> groups;

    public void friendsWith(Person person) {
        if (friends == null) {
            friends = new HashSet<>();
        }
        friends.add(person);
//        person.friendsWith(this);
    }

    public void friendsWith(List<Person> people) {
        for (Person person : people) {
            this.friendsWith(person);
        }
    }

    public void memberOf(Group group) {
        if (group == null) {
            groups = new HashSet<>();
        }
        groups.add(group);
    }

    public void memberOf(List<Group> groups) {
        for (Group group : groups) {
            this.memberOf(group);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        // Compare only IDs
        return id != null && id.equals(person.id);
    }

    @Override
    public int hashCode() {
        return (int) (Math.min(this.id, Integer.MAX_VALUE));
    }

    // required by Neo4j API for some reason...
    private Person() {}

}