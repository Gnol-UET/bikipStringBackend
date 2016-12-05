package uet.k59t.service;

/**
 * Created by Longlaptop on 11/24/2016.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.StudentDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.model.Role;
import uet.k59t.model.Student;
import uet.k59t.model.Teacher;
import uet.k59t.repository.StudentRepository;
import uet.k59t.repository.TeacherRepository;

@Service
public class LoginService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private StudentRepository studentRepository;

    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        if (teacherDTO.getRole() == Role.TEACHER) {
            Teacher teacher = new Teacher();
            teacher.setUsername(teacherDTO.getUsername());
            teacher.setRole(Role.TEACHER);
            teacher.setPassword(teacherDTO.getPassword());
            teacher.setEmail(teacherDTO.getEmail());
            teacher.setUnit(teacherDTO.getUnit());
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
}
