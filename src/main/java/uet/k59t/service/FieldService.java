package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.FieldDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.model.Field;
import uet.k59t.model.Teacher;
import uet.k59t.repository.FieldRepository;
import uet.k59t.repository.TeacherRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 12/11/2016.
 */
@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public List<FieldDTO> showAllFields() {
        List<FieldDTO> fieldDTOs = new ArrayList<FieldDTO>();
        List<Field> existedFields = (List<Field>) fieldRepository.findAll();
        for (Field x : existedFields){
            FieldDTO newFieldDTO = new FieldDTO();
            newFieldDTO.setId(x.getId());
            newFieldDTO.setFieldName(x.getFieldName());
            fieldDTOs.add(newFieldDTO);
        }
        return fieldDTOs;
    }

    public String addFieldToTeacher(long field_id, String token) {
        if(teacherRepository.findByToken(token) != null){
            Teacher teacherToBeAdded = teacherRepository.findByToken(token);
            Field fieldToBeAdded = fieldRepository.findOne(field_id);
            if(fieldToBeAdded.getTeachers().contains(teacherToBeAdded) == false){
                fieldToBeAdded.getTeachers().add(teacherToBeAdded);
                fieldRepository.save(fieldToBeAdded);
                return "Field "+ fieldToBeAdded.getFieldName()+" added "+ teacherToBeAdded.getUsername();
            }
            else{
                throw  new NullPointerException("Field has already contained teacher");
            }
        }
        else throw  new NullPointerException("Invalid token");
    }

    public List<TeacherDTO> showAllInterestedTeacherOfOneField(long field_id) {
        List<TeacherDTO> returnTeachers = new ArrayList<TeacherDTO>();
        if(fieldRepository.findOne(field_id) != null){
            Field field = fieldRepository.findOne(field_id);
            for(Teacher teacher:field.getTeachers()){
                TeacherDTO teacherDTO = new TeacherDTO();
                teacherDTO.setUsername(teacher.getUsername());
                teacherDTO.setEmail(teacher.getEmail());
                teacherDTO.setRealname(teacher.getRealname());
                teacherDTO.setUnit(teacher.getUnit());
                returnTeachers.add(teacherDTO);
            }
            return returnTeachers;
        }
        else throw  new NullPointerException("Invalid field Id");
    }

    public List<FieldDTO> showAllInterestedFieldOfOneTeacher(long teacher_id) {
        List<FieldDTO> returnFieldDTOs = new ArrayList<FieldDTO>();
        List<Field> fields = (List<Field>) fieldRepository.findAll();
        if(teacherRepository.findOne(teacher_id) != null){
            Teacher teacher = teacherRepository.findOne(teacher_id);
            for(Field field:fields){
                if(field.getTeachers().contains(teacher) == true){
                    FieldDTO fieldDTO = new FieldDTO();
                    fieldDTO.setFieldName(field.getFieldName());
                    fieldDTO.setId(field.getId());
                    returnFieldDTOs.add(fieldDTO);

                }
            }
            return returnFieldDTOs;
        }
        else throw new NullPointerException("Invalid teacher ID");
    }
}
