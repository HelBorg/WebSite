package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.SkillDTO;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.repository.SkillRepository;
import com.freelance.project.demo.service.impl.SkillServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/skill")
public class SkillController {

    @Autowired
    private SkillRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public Collection<Skill> getAll() {
        logger.info("Request to get all skills");
        return repository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity getSkillByName(@PathVariable("name") String name){
        logger.info("Request to get skill by name: {}", name);
        return ok(repository.findSkillByName(name).getSkillId());
    }

}
