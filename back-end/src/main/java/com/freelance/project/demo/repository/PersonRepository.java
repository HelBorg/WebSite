package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
