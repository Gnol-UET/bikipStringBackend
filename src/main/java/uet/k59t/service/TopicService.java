package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.model.Teacher;
import uet.k59t.repository.ModeratorRepository;
import uet.k59t.repository.StudentRepository;
import uet.k59t.repository.TeacherRepository;

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
}
