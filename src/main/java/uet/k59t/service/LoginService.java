package uet.k59t.service;

/**
 * Created by Longlaptop on 11/24/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.StudentDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.controller.dto.UserDTO;
import uet.k59t.model.Moderator;
import uet.k59t.model.Role;
import uet.k59t.model.Student;
import uet.k59t.model.Teacher;
import uet.k59t.repository.ModeratorRepository;
import uet.k59t.repository.StudentRepository;
import uet.k59t.repository.TeacherRepository;

import java.util.Date;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ModeratorRepository moderatorRepository;

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO.getRole() == Role.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUsername(teacherDTO.getUsername());
            teacher.setRole(Role.TEACHER);
            teacher.setPassword(teacherDTO.getPassword());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setUnit(teacherDTO.getUnit());
            teacher.setRealname(teacherDTO.getRealname());
            Teacher existedTeacher = teacherRepository.findByUsername(teacher.getUsername());
            if (existedTeacher == null) {
                teacherRepository.save(teacher);
                return teacherDTO;
            } else {
                throw new NullPointerException("Username đã tồn tại");
            }

        } else {
            throw new NullPointerException("Username đã tồn tại");
        }

    }

    public StudentDTO createStudent(StudentDTO studentDTO) {

        if (studentDTO.getRole() == Role.STUDENT) {
            Student student = new Student();
            student.setUsername(studentDTO.getUsername());
            student.setRole(Role.STUDENT);
            student.setPassword(studentDTO.getPassword());
            student.setGrade(studentDTO.getGrade());
            student.setClassname(studentDTO.getClassname());
            student.setEmail(studentDTO.getEmail());
            student.setRealname(studentDTO.getRealname());
            Student existedStudent = studentRepository.findByUsername(student.getUsername());
            if (existedStudent == null) {
                studentRepository.save(student);
                return studentDTO;
            } else {
                throw new NullPointerException("Username đã tồn tại");
            }
        } else {
            throw new NullPointerException("Username đã tồn tại");
        }

    }

    public UserDTO login(UserDTO userDTO) {
        if(studentRepository.findByEmail(userDTO.getUsername() + "@vnu.edu.vn") !=  null){
            Student a = studentRepository.findByEmail(userDTO.getUsername()+ "@vnu.edu.vn");
            if(a.getPassword().equals(studentRepository.findByEmail(userDTO.getUsername()+ "@vnu.edu.vn").getPassword())){
                if (a.getToken() == null) {
                    a.setToken(UUID.randomUUID().toString());
                    a.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                } else {
                    a.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                }
                a = studentRepository.save(a);
                UserDTO result = new UserDTO();
                result.setUsername(a.getUsername());
                result.setPassword(a.getPassword());
                result.setRole(a.getRole());
                result.setToken(a.getToken());
                return result;
            }
        }
        else if(teacherRepository.findByUsername(userDTO.getUsername()) !=  null){
            Teacher b = teacherRepository.findByUsername(userDTO.getUsername());
            if(b.getPassword().equals(teacherRepository.findByUsername(userDTO.getUsername()).getPassword())){
                if (b.getToken() == null) {
                    b.setToken(UUID.randomUUID().toString());
                    b.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                } else {
                    b.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                }
                b = teacherRepository.save(b);
                UserDTO result = new UserDTO();
                result.setUsername(b.getUsername());
                result.setPassword(b.getPassword());
                result.setRole(b.getRole());
                result.setToken(b.getToken());
                return result;
            }
        }
        else if(moderatorRepository.findByUsername(userDTO.getUsername()) != null){
            if(moderatorRepository.findByUsername(userDTO.getUsername()).getPassword().equals(userDTO.getPassword())){
                Moderator moderator = moderatorRepository.findByUsername(userDTO.getUsername());
                if (moderator.getToken() == null) {
                    moderator.setToken(UUID.randomUUID().toString());
                    moderator.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                } else {
                    moderator.setTokenExpiry(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)));
                }
                UserDTO result = new UserDTO();
                result.setUsername(moderator.getUsername());
                result.setRole(moderator.getRole());
                result.setToken(moderator.getToken());
                moderatorRepository.save(moderator);
                return  result;
            }
        }
        else {
            throw  new NullPointerException("Sai username hoac password");
        }
        return null;

    }

    public void logout(String token) {
        if(teacherRepository.findByToken(token) != null){
            Teacher teacher = teacherRepository.findByToken(token);
            teacher.setToken(null);
            teacher.setTokenExpiry(null);
            teacherRepository.save(teacher);
        }
        else if(studentRepository.findByToken(token) != null){
            Student student = studentRepository.findByToken(token);
            student.setToken(null);
            student.setTokenExpiry(null);
            studentRepository.save(student);
        }
        else if(moderatorRepository.findByToken(token) != null){
            Moderator moderator = moderatorRepository.findByToken(token);
            moderator.setTokenExpiry(null);
            moderator.setToken(null);
            moderatorRepository.save(moderator);

        }


    }
}
