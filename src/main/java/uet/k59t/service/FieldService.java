package uet.k59t.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uet.k59t.controller.dto.FieldDTO;
import uet.k59t.model.Field;
import uet.k59t.repository.FieldRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Long on 12/11/2016.
 */
@Service
public class FieldService {
    @Autowired
    private FieldRepository fieldRepository;
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
}
