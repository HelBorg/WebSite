package com.freelance.project.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "task")
public class Task implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id", nullable = false)
    private int taskId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deadline", nullable = false)
    private Date deadline;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_created", nullable = false)
    private Date createdTime;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "performer_id", referencedColumnName = "person_id")
    private Person assignedUser;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "task_skill",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> taskSkills;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "candidate",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> candidateTasks;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "person_id")
    private Person author;

//    @OneToMany(mappedBy = "task")
//    private List<Review> taskReviews;


}