package uet.k59t.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uet.k59t.controller.dto.FieldDTO;
import uet.k59t.controller.stereotype.RequiredRoles;
import uet.k59t.model.Field;
import uet.k59t.model.Role;
import uet.k59t.service.FieldService;

import java.util.List;

/**
 * Created by Long on 12/11/2016.
 */
@RestController
public class FieldController {
    @Autowired
    private FieldService fieldService;
//    @RequiredRoles({Role.TEACHER,Role.STUDENT,Role.MODERATOR})
    @RequestMapping(value = "/field" , method = RequestMethod.GET)
    public List<FieldDTO> showAllFields(){
        return fieldService.showAllFields();
    }
}
