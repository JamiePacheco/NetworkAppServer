package com.app.network.networkapplication.repository;

import com.app.network.networkapplication.model.Group;
import com.app.network.networkapplication.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends Neo4jRepository<Group, Long> {

    @Query(value = """
        MATCH (g:`_GROUP` {groupName: $group_name})<-[:MEMBER_OF]->(p:PERSON)
        RETURN p
        ORDER BY p.name
        SKIP $skip
        LIMIT $limit
    """,
    countQuery = """
        MATCH (g:`_GROUP` {groupName: $group_name})<-[:MEMBER_OF]->(p:PERSON)
        RETURN count(p)          
    """
    )
    Page<Person> getPeopleInGroup(@Param("group_name") String group_name, Pageable pageable);

    @Query("""
            MATCH (g:_GROUP)
            RETURN g
            ORDER BY rand() 
            LIMIT $limit
    """)
    List<Group> findRandomGroups(@Param("limit") int limit);


}
