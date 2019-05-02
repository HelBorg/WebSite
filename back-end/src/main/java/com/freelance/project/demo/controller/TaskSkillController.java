package com.freelance.project.demo.controller;


import com.freelance.project.demo.dto.TaskSkillDTO;
import com.freelance.project.demo.service.TaskSkillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/v1/task-skill")
public class TaskSkillController{

    @Autowired
    private TaskSkillService taskSkillService;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);


    @DeleteMapping("/{id}")
    public void deleteTaskSkill(@PathVariable int id){
        logger.info("Request to delete task skill dependency: {}", id);
        taskSkillService.deleteTaskSkill(id);
    }

    @PostMapping("/{taskId}")
    public void createNewSkillsList(@RequestBody TaskSkillDTO taskSkillDTO, @PathVariable int taskId){
        logger.info("Request to create new skills list: task id - {}, taskSkill - {}", taskId, taskSkillDTO);
        taskSkillService.addNewTaskSkill(taskSkillDTO, taskId);
    }
}
