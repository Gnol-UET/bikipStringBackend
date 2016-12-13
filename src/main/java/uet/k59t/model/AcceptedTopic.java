//package uet.k59t.model;
//
//import org.apache.poi.ss.formula.functions.T;
//
//import javax.persistence.*;
//
///**
// * Created by Longlaptop on 12/13/2016.
// */
//@Entity
//public class AcceptedTopic {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "topic_id")
//    private Topic topicId;
//    @OneToOne
//    @JoinColumn(name = "topic_name", referencedColumnName = "topicname")
//    private Topic topicname;
//    @OneToOne
//    @JoinColumn(name = "teacher_id")
//    private Teacher teacher;
//}
//
