package com.app.network.networkapplication.controller;


import com.app.network.networkapplication.model.Group;
import com.app.network.networkapplication.model.Person;
import com.app.network.networkapplication.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api/v1/group")
public class GroupController {

    private GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/")
    public ResponseEntity<Response<List<Group>>> addNewGroups(@RequestBody List<Group> newGroups) {
        ResponseEntity<Response<List<Group>>> responseResponseEntity;
        try {
            List<Group> savedGroups = groupService.addNewGroups(newGroups);
            responseResponseEntity = ResponseEntity.ok(
                    Response.<List<Group>>builder()
                            .message("Saved " + savedGroups.size() + " new groups")
                            .status(HttpStatus.ACCEPTED)
                            .responseContent(savedGroups)
                            .build()
            );
        } catch (Exception e) {
            responseResponseEntity = ResponseEntity.badRequest().body(
                    Response.<List<Group>>builder()
                            .message("Error persisting new groups: " + e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .responseContent(null)
                            .build()
            );
        }
        return responseResponseEntity;
    }



    @GetMapping("/people")
    public ResponseEntity<Response<List<Person>>> getPeopleInGroup(@RequestParam("group-name") String groupName, @RequestParam("page_num") int pageNum, @RequestParam("page_size") int pageSize) {
        ResponseEntity<Response<List<Person>>> responseResponseEntity;
        try {
            List<Person> peopleInGroup = this.groupService.getAllPeopleInGroup(groupName, pageNum, pageSize);
            responseResponseEntity = ResponseEntity.ok(
                    Response.<List<Person>>builder()
                            .message("Retrieved " + peopleInGroup.size() + " in group " + groupName)
                            .status(HttpStatus.ACCEPTED)
                            .responseContent(peopleInGroup)
                            .build()
            );
        } catch (Exception e) {
            responseResponseEntity = ResponseEntity.badRequest().body(
                    Response.<List<Person>>builder()
                            .message("Error getting people: " + e.getMessage())
                            .status(HttpStatus.BAD_REQUEST)
                            .responseContent(null)
                            .build()
            );
        }
        return responseResponseEntity;
    }

}
