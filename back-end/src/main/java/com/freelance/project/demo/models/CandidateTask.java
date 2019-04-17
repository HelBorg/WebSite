//package java.com.freelance.project.demo.models;
//
//import lombok.Data;
//import com.freelance.project.demo.models.Person;
//import com.freelance.project.demo.models.Task;
//import javax.persistence.*;
//
//@Data
//@Entity
//@Table(name = "candidate")
//public class CandidateTask {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "candidate_id", nullable = false)
//    private Long candidate_id;
//
//    @ManyToOne
//    @JoinColumn(name = "person_id")
//    private Person  person;
//
//    @ManyToOne
//    @JoinTable(name = "task_id")
//    private Task task;
//}
