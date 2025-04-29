package com.app.network.networkapplication.service;


import com.app.network.networkapplication.model.Group;
import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.repository.GroupRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class GroupService {
    private GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Group> getAllGroups(int pageNum, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        Page<Group> page = groupRepository.findAll(pageable);
        return page.stream().toList();
    }

    public List<Person> getAllPeopleInGroup(String group_name, int pageNum, int pageSize) {
        PageRequest pageable = PageRequest.of(pageNum, pageSize);
        Page<Person> page = groupRepository.getPeopleInGroup(group_name, pageable);
        return page.stream().toList();
    }

    public List<Group> addNewGroups(List<Group> newGroups) {
        return this.groupRepository.saveAll(newGroups);
    }

}