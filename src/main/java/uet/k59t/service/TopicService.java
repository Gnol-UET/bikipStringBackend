package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.controller.dto.TopicDTO;
import uet.k59t.model.*;
import uet.k59t.repository.ModeratorRepository;
import uet.k59t.repository.StudentRepository;
import uet.k59t.repository.TeacherRepository;
import uet.k59t.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 12/10/2016.
 */
@Service
public class TopicService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModeratorRepository moderatorRepository;
    @Autowired
    private TopicRepository topicRepository;


    public List<TeacherDTO> showAllTeachers(String token) {
        if(teacherRepository.findByToken(token) != null ||
                studentRepository.findByToken(token) != null ||
                moderatorRepository.findByToken(token) != null){
            List<Teacher> listTeachers = (List<Teacher>) teacherRepository.findAll();
            List<TeacherDTO> returnList = new ArrayList<TeacherDTO>();
            for (Teacher teacher: listTeachers) {
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setUsername(teacher.getUsername());
                teacherDTO.setEmail(teacher.getEmail());
                teacherDTO.setUnit(teacher.getUnit());
                teacherDTO.setRealname(teacher.getRealname());

                returnList.add(teacherDTO);
            }
            return returnList;
        }
        else throw new NullPointerException("Invalid token");
    }

    public String openRegister(String token) {
        if(moderatorRepository.findByToken(token) != null){
            Moderator.isOpenForRegister = true;
            if(Moderator.isOpenForRegister == true){
                List<Student> studentList = (List<Student>) studentRepository.findAll();
                ApplicationContext context =
                        new ClassPathXmlApplicationContext("Spring-Mail.xml");
                MailMail mm = (MailMail) context.getBean("mailMail");
                String sender="sendergmailid@gmail.com";//write here sender gmail id
                for (Student student : studentList) {
                    if(student.isAllowance() == true){
                        String receiver = student.getEmail();
                        mm.sendMail(sender,receiver,"Mở đăng ký đề tài","Khoa xin thông báo bạn được phép đăng ký đề tài \n" +
                                "Xin mời vào đường dẫn sau: http://localhost:8080/topic/studentregister" );
                    }
                }
                return "open";
            }
            else throw new NullPointerException("The registration is CLOSED, please come back");
        }
        else throw new NullPointerException("Invalid token");
    }
    public String closeRegister(String token) {
        if(moderatorRepository.findByToken(token) != null){
            Moderator.isOpenForRegister = false;
            return "The registration is CLOSED, please come back";
        }
        else throw new NullPointerException("Invalid token");
    }

    public TopicDTO studentRegister(TopicDTO topicDTO,String token) {
        if(Moderator.isOpenForRegister == true){
            if(studentRepository.findByToken(token) != null) {
                Student student = studentRepository.findByToken(token);
                Teacher teacher = teacherRepository.findOne(topicDTO.getTeacherid());
                if(student.isAllowance() == true){

                    Topic topic = new Topic();
                    topic.setStudent(student);
                    topic.setTeacher(teacher);
                    topic.setAccepted(false);
                    topic.setTopicname(topicDTO.getTopicname());
                    topicDTO.setTeachername(teacher.getRealname());
                    topicRepository.save(topic);


                    return topicDTO;
                }
                else throw new NullPointerException("This student is NOT ALLOWED to register");
            }
            else throw new NullPointerException("Invalid token");
        }
        else throw new NullPointerException("The registration is CLOSED, please come back ");
    }
}
