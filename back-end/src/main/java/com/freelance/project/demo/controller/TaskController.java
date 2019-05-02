package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Task;
import com.freelance.project.demo.service.PersonService;
import com.freelance.project.demo.service.TaskService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    PersonService personService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        logger.info("Request to delete task: id - {}", id);
        taskService.deleteTask(id);
    }


    @PostMapping("/new")
    public ResponseEntity create(@AuthenticationPrincipal UserDetails userDetails){
        logger.info("Request to create task from user: {}", userDetails);
        Task createdTask = taskService.createNew(personService.findByEmail(userDetails.getUsername()));
        Map<Object, Object> model = new HashMap<>();
        model.put("id", createdTask.getTaskId());
        return ok(model);
    }

    @PostMapping("/update/status/{id}/{status}")
    public String updateStatus(@PathVariable int id, @PathVariable String status){
        logger.info("Request to update task status: task id - {}, new status - {}", id, status);
        return taskService.updateStatus(id,status);
    }


    @PostMapping("/update/assigned/{taskId}/{userId}")
    public void updateAssignedUser(@PathVariable int taskId, @PathVariable int userId){
        logger.info("Request to update task's assigned user: user id - {}", userId);
        taskService.updateAssignedUser(userId,taskId);
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable int id) {
        logger.info("Request to get task by id: id - {}", id);
       return taskService.loadTask(id);
    }


    @GetMapping
    public ResponseEntity<Pager<TaskDTO>> getAll(@RequestParam("id") Optional<Integer> id,
                                                 @RequestParam("size") Optional<Integer> pageSize,
                                                 @RequestParam("page") Optional<Integer> pageNumber,
                                                 @RequestParam("sort") Optional<String> sort,
                                                 @RequestParam("pageName") Optional<String> pageName) {
        Pager<TaskDTO> pager = taskService.findAll(id,pageSize, pageNumber, sort, pageName);
        logger.info("Request to get tasks: {}", pager);
        return ResponseEntity.ok().body(pager);
    }

    @PostMapping
    public void updateTask(@RequestBody TaskDTO task){
        logger.info("Request to update task: {}", task);
        taskService.updateTask(task);
    }

}
