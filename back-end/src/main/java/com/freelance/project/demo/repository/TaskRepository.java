package com.freelance.project.demo.repository;

import com.freelance.project.demo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t where t.author.personId = :id")
    Page<Task> findAllByAuthor(@Param("id")int author_id, Pageable pageRequest);

    @Query("Select t from Task t")
    Page<Task> find(Pageable pageRequest);

    List<Task> findAll();

    @Query("SELECT t FROM Task t join t.candidateTasks tc where tc.personId = :id")
    Page<Task> findAllByCandidate(@Param("id")int candidate_id, Pageable pageRequest);

    Task findByTaskId(int id);
}
