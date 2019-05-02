package com.freelance.project.demo.controller;

import com.freelance.project.demo.dto.PersonDTO;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.repository.PersonRepository;
import com.freelance.project.demo.service.impl.PersonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/v1/persons")
    public class PersonController {


    @Autowired
    private PersonServiceImpl service;

    private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

    @GetMapping
    public Collection<PersonDTO> getAll() {
        logger.info("Request to get all users");
        return service.findAll();
    }

    @PostMapping
    public void saveNewPerson(@RequestBody Person person) {
        logger.info("Request to save new user: {}", person);
        service.create(person);
    }


}
