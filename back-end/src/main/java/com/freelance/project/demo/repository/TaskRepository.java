package com.freelance.project.demo.repository;

import com.freelance.project.demo.dto.TaskDTO;
import com.freelance.project.demo.models.Pager;
import com.freelance.project.demo.models.Person;
import com.freelance.project.demo.models.Skill;
import com.freelance.project.demo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

    @Query("SELECT t FROM Task t where t.author.personId = :id ")
    Page<Task> findAllByAuthor(@Param("id") int author_id,
                               Pageable pageRequest);

    @Query("SELECT DISTINCT t FROM Task t join t.candidateTasks tc where tc.personId = :id and t.author.personId not in :id")
    Page<Task> findAllByCandidate(@Param("id") int candidate_id,
                                  Pageable pageRequest);

    @Query("SELECT t from Task t join t.assignedUser tc where tc.personId = :id")
    Page<Task> findAllInWork(@Param("id") int candidate_id, Pageable pageRequest);

    @Query("Select t from Task t where t.name like %:name%")
    Page<Task> findByName(@Param("name") String name, Pageable pageRequest);

    Task findByTaskId(int id);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Task t SET t.status = :status WHERE t.taskId = :taskId")
    void updateStatus(@Param("taskId") int taskId, @Param("status") String status);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.assignedUser = :person WHERE t.taskId = :taskId")
    void updateAssignedUser(@Param("person") Person person, @Param("taskId") int taskId);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.assignedUser = null, t.status = 'PUBLISH' WHERE t.taskId = :taskId")
    void deleteAssignAndRevertStatus(@Param("taskId") int taskId);


}
