package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {


    List<Skill> findAll();

    /*@Query("select s.skillId from Skill s where s.name= :name")
    Skill findSkillByName(@Param("name") String name);*/

    Skill findSkillByName(String name);

}
