package uet.k59t.service;

import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.PageOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.AcceptTopicDTO;
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
                                "Xin mời vào đường dẫn sau: http://localhost:8080/topic/student/studentregister" );
                    }
                }
                return "Opened registration ";
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
                List<Topic> existedTopics = (List<Topic>) topicRepository.findAll();
                for(Topic topic : existedTopics){
                    if(topic.getTopicname().equalsIgnoreCase(topicDTO.getTopicname())){
                        throw new NullPointerException("Topic name existed, try another name");
                    }
                }
                Student student = studentRepository.findByToken(token);
                Teacher teacher = teacherRepository.findOne(topicDTO.getTeacherid());
                if(student.isAllowance() == true){

                    Topic topic = new Topic();
                    topic.setStudent(student);
                    topic.setTeacher(teacher);
                    topic.setAccepted(false);
                    topic.setTopicname(topicDTO.getTopicname());
                    topicDTO.setStudentname(student.getUsername());
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

    public List<TopicDTO> showRegisteredTopic(String token) {
        if(teacherRepository.findByToken(token) != null){
            Teacher teacher = teacherRepository.findByToken(token);
            List<TopicDTO> returnList = new ArrayList<TopicDTO>();
            for (Topic topic: topicRepository.findAll()){
                if(topic.getTeacher() == teacher){
                    TopicDTO topicDTO = new TopicDTO();
                    topicDTO.setTeachername(teacher.getRealname());
                    topicDTO.setId(topic.getId());
                    topicDTO.setTeacherid(teacher.getId());
                    topicDTO.setTopicname(topic.getTopicname());
                    topicDTO.setStudentname(topic.getStudent().getUsername());
                    topicDTO.setAccepted(topic.isAccepted());
                    returnList.add(topicDTO);
                }
            }
            return returnList;
        }
        else throw new NullPointerException("Invalid token");
    }

    public TopicDTO acceptTopic(Long topicId, AcceptTopicDTO acceptTopicDTO, String token) {
        if(teacherRepository.findByToken(token) != null){
            Teacher teacher = teacherRepository.findByToken(token);
            List<Topic> allTopic = (List<Topic>) topicRepository.findAll();
            List<Topic> topicOfThisTeacher = new ArrayList<Topic>();
            for (Topic i: allTopic){
                if(i.getTeacher() == teacher){
                    topicOfThisTeacher.add(i);
                }
            }
            for(Topic i: topicOfThisTeacher){
                if(i.getId().equals(topicId)){
                    if(acceptTopicDTO.isaccept() == true) {
                        i.setAccepted(true);
                        topicRepository.save(i);
                        TopicDTO returnDto = new TopicDTO();
                        returnDto.setAccepted(i.isAccepted());
                        returnDto.setId(i.getId());
                        returnDto.setTeacherid(teacher.getId());
                        returnDto.setStudentname(i.getStudent().getUsername());
                        returnDto.setTeachername(i.getTeacher().getRealname());
                        returnDto.setTopicname(i.getTopicname());
                        return returnDto;
                    }
                }
            }
        }
        else throw new NullPointerException("Invalid token");
        return null;
    }

    public TopicDTO showATopic(Long topicId, String token) {
        if(studentRepository.findByToken(token) != null){
            Topic topic = topicRepository.findById(topicId);
            if(topic.getStudent() == studentRepository.findByToken(token)){
                TopicDTO returnDto = new TopicDTO();
                returnDto.setAccepted(topic.isAccepted());
                returnDto.setTeacherid(topic.getTeacher().getId());
                returnDto.setTopicname(topic.getTopicname());
                returnDto.setTeachername(topic.getTeacher().getRealname());
                returnDto.setId(topic.getId());
                returnDto.setStudentname(topic.getStudent().getUsername());
                return returnDto;
            }
            else throw new NullPointerException("This topic is not yours topic");
        }
        else throw new NullPointerException("Invalid token");
    }

    public TopicDTO editATopic(Long topicId, TopicDTO topicDTO, String token) {
        if(studentRepository.findByToken(token) != null){
            Topic topic = topicRepository.findById(topicId);
            if(topic.getStudent() == studentRepository.findByToken(token)){
                List<Topic> existedTopics = (List<Topic>) topicRepository.findAll();
                for(Topic topic1 : existedTopics){
                    if(topic1.getTopicname().equalsIgnoreCase(topicDTO.getTopicname())){
                        throw new NullPointerException("Topic name existed, try another name");
                    }
                }

                TopicDTO returnDto = new TopicDTO();
                topic.setTopicname(topicDTO.getTopicname());
                Teacher teacher = teacherRepository.findOne(topicDTO.getTeacherid());
                topic.setTeacher(teacher);


                topicRepository.save(topic);
                returnDto.setAccepted(topic.isAccepted());
                returnDto.setTeacherid(topic.getTeacher().getId());
                returnDto.setTopicname(topic.getTopicname());
                returnDto.setTeachername(topic.getTeacher().getRealname());
                returnDto.setId(topic.getId());
                returnDto.setStudentname(topic.getStudent().getUsername());
                return returnDto;
            }
            else throw new NullPointerException("This topic is not yours topic");
        }
        else throw new NullPointerException("Invalid token");
    }

    public String deleteATopic(Long topicId, String token) {
        if(studentRepository.findByToken(token) != null){
            Topic topic = topicRepository.findById(topicId);
            if(topic.getStudent() == studentRepository.findByToken(token)){
               topicRepository.delete(topicId);
                return "Student "+topic.getStudent().getUsername()+" has deleted " +topic.getTopicname()+" which tutored by " +topic.getTeacher().getRealname();
            }
            else throw new NullPointerException("This topic is not yours topic");
        }
        else throw new NullPointerException("Invalid token");
    }

    public String openDefense(String token) {
        if(moderatorRepository.findByToken(token) != null){
            Moderator.isOpenForDefense = true;
            if(Moderator.isOpenForDefense == true){
//                List<Student> studentList = (List<Student>) studentRepository.findAll();
                ApplicationContext context =
                        new ClassPathXmlApplicationContext("Spring-Mail.xml");
                MailMail mm = (MailMail) context.getBean("mailMail");
                String sender="sendergmailid@gmail.com";//write here sender gmail id
//
                List<Topic> topicList = (List<Topic>) topicRepository.findAll();
                for(Topic topic:topicList){
                    if(topic.isAccepted() == true){
                        String receiver = topic.getStudent().getEmail();
                        String topicname = topic.getTopicname();
                        mm.sendMail(sender,receiver,"Mở đăng ký bảo vệ khóa luận","Khoa xin thông báo đã cổng mở bảo vệ đề tài \n" +
                                "Để đăng ký bảo vệ đề tài "+topicname+ ", xin mời vào đường dẫn sau: http://localhost:8080/topic/student/defense" );

                    }
                    if(topic.isAccepted() == false){
                        String receiver = topic.getStudent().getUsername();
                        String topicname = topic.getTopicname();
                        mm.sendMail(sender,receiver,"Đề tài không được cấp phép", "Khoa xin thông báo đề tài " +
                                topicname+" của bạn không được giảng viên cấp phép bảo vệ");
                    }
                }

                return "Defense portal opened ";
            }
            else throw new NullPointerException("The Defense portal is CLOSED, please come back");
        }
        else throw new NullPointerException("Invalid token");
    }

    public String closeDefense(String token) {
        if(moderatorRepository.findByToken(token) != null){
            Moderator.isOpenForDefense = false;
            return "The registration is CLOSED, please come back";
        }
        else throw new NullPointerException("Invalid token");
    }

    public String receiveATopic(String token, Long topicId) {
        if(moderatorRepository.findByToken(token) != null){
            List<Topic> topicList = (List<Topic>) topicRepository.findAll();
            for (Topic topic : topicList){
                if(topic.getId().equals(topicId)){
                    if(topic.isAccepted() == true){
                        topic.setReceived(true);
                        topicRepository.save(topic);
                        return "Topic " + topic.getTopicname() +" is received";
                    }
                    else throw new NullPointerException("Topic is not accepted by teacher ");
                }
            }
            return "Topic not found";
        }
        else throw new NullPointerException("Invalid token");
    }

    public String notifyUnReceived(String token) {
        if(moderatorRepository.findByToken(token) != null){
            List<Topic> topicList = (List<Topic>) topicRepository.findAll();
            ApplicationContext context =
                    new ClassPathXmlApplicationContext("Spring-Mail.xml");
            MailMail mm = (MailMail) context.getBean("mailMail");
            String sender="sendergmailid@gmail.com";//write here sender gmail id
            for (Topic topic : topicList){
                if(topic.isReceived() == false){
                    String receiver = topic.getStudent().getEmail();
                    String topicname = topic.getTopicname();
                    mm.sendMail(sender,receiver,"Nộp hồ sơ","Khoa xin thông báo sinh viên "+topic.getStudent().getUsername()+" nộp hồ sơ đề tài"+topicname+" ngay lập tức\n" +
                            "Địa chỉ: Tầng 3, nhà E3 - Đai học Công Nghệ" );

                }
            }
            return "Notification mail sent";
        }
        else throw new NullPointerException("Invalid token");
    }
}
