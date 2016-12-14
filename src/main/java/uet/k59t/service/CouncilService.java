package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.CouncilDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.model.Council;
import uet.k59t.model.Role;
import uet.k59t.model.Teacher;
import uet.k59t.model.Topic;
import uet.k59t.repository.CouncilRepository;
import uet.k59t.repository.ModeratorRepository;
import uet.k59t.repository.TeacherRepository;
import uet.k59t.repository.TopicRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Longlaptop on 12/14/2016.
 */
@Service
public class CouncilService {
    @Autowired
    private CouncilRepository councilRepository;
    @Autowired
    private ModeratorRepository moderatorRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TopicRepository topicRepository;

    public CouncilDTO createCouncil(String token, CouncilDTO councilDTO) {
        if (moderatorRepository.findByToken(token) != null) {
            Council council = new Council();
            council.setCouncilname(councilDTO.getCouncilname());
            councilRepository.save(council);
            return councilDTO;
        } else throw new NullPointerException("Invalid token");
    }

    public CouncilDTO addTeacherToCouncil(String token, Long councilId, Long teacherId) {
        if (moderatorRepository.findByToken(token) != null) {
            Council council = councilRepository.findOne(councilId);
            List<Teacher> currentTeacherOfCouncil = council.getTeachers();
            currentTeacherOfCouncil.add(teacherRepository.findOne(teacherId));
            councilRepository.save(council);
//            CouncilDTO councilDTO =new CouncilDTO();
//            councilDTO.setCouncilname(council.getCouncilname());
//            councilDTO.setId(council.getId());
//            List<TeacherDTO> returnTeacherList = new ArrayList<>();
//            Teacher teacher = teacherRepository.findOne(teacherId);
//            TeacherDTO teacherDTO = new TeacherDTO();
//            teacherDTO.setRole(Role.TEACHER);
//            teacherDTO.setUsername(teacher.getUsername());
//            teacherDTO.setEmail(teacher.getEmail());
//            teacherDTO.setUnit(teacher.getUnit());
//            teacherDTO.setRealname(teacher.getRealname());
//            returnTeacherList.add(teacherDTO);
//            councilDTO.setTeacherList(returnTeacherList);
//            return councilDTO;
            CouncilDTO returnDto = showCouncil(token,councilId);
            return returnDto;
        } else throw new NullPointerException("Invalid token");
    }

    public CouncilDTO showCouncil(String token, Long councilId) {
        if(moderatorRepository.findByToken(token) != null){
            Council council = councilRepository.findOne(councilId);
            CouncilDTO returnDto = new CouncilDTO();
            returnDto.setCouncilname(council.getCouncilname());
            returnDto.setId(councilId);
            List<Teacher> teacherList = council.getTeachers();
            List<TeacherDTO> returnTeacherList = new ArrayList<>();
            for (Teacher teacher:teacherList){
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setRole(Role.TEACHER);
                teacherDTO.setUsername(teacher.getUsername());
                teacherDTO.setEmail(teacher.getEmail());
                teacherDTO.setUnit(teacher.getUnit());
                teacherDTO.setRealname(teacher.getRealname());
                returnTeacherList.add(teacherDTO);
            }
            returnDto.setTeacherList(returnTeacherList);
            return returnDto;
        }
        else throw new NullPointerException("Invalid token");
    }

    public CouncilDTO gradeATopic(String token, CouncilDTO councilDTO) {
        if(moderatorRepository.findByToken(token) != null){
            Council council = councilRepository.findByCouncilname(councilDTO.getCouncilname());
            Topic topic = topicRepository.findById(councilDTO.getTopicid());
//            council.setTopics(topic);
            council.setComment(councilDTO.getComment());
            council.setScore(councilDTO.getScore());
            councilRepository.save(council);
            CouncilDTO returnDto = showCouncil(token,council.getId());
            returnDto.setComment(councilDTO.getComment());
            returnDto.setScore(councilDTO.getScore());
            returnDto.setTopicid(topic.getId());
            return returnDto;
        }
        else throw new NullPointerException("Invalid token");
    }
}
