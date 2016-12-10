package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.FieldDTO;
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
}
