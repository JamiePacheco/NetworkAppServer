package com.app.network.networkapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Primary;
import org.springframework.data.neo4j.core.schema.*;

import java.util.Set;

@Data
@Node(primaryLabel = "_GROUP")
@AllArgsConstructor()
public class Group {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "groupName")
    private String groupName;

    @JsonIgnore
    @Relationship(type = "MEMBER_OF", direction = Relationship.Direction.INCOMING)
    private Set<Person> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        // Compare only IDs
        return id != null && id.equals(group.id);
    }

    @Override
    public int hashCode() {
        return (int) (Math.min(this.id, Integer.MAX_VALUE));
    }

    // required by Neo4j API for some reason...
    private Group() {}


}
