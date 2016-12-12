package uet.k59t.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uet.k59t.controller.dto.FieldDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.controller.stereotype.RequiredRoles;
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

    @RequestMapping(value = "/field", method = RequestMethod.GET)
    public List<FieldDTO> showAllFields() {
        return fieldService.showAllFields();
    }

    //Teacher add an interested field
    @RequiredRoles(Role.TEACHER)
    @RequestMapping(value = "/field/{field_id}", method = RequestMethod.POST)
    public String addFieldToTeacher(@PathVariable("field_id") long field_id, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("auth-token");
        return fieldService.addFieldToTeacher(field_id, token);
    }

    //Field get all interested teacher
    //Different RequestMethod
    @RequestMapping(value = "/field/showfield/{field_id}", method = RequestMethod.GET)
    public List<TeacherDTO> showAllInterestedTeacherOfOneField(@PathVariable("field_id") long field_id) {
        return fieldService.showAllInterestedTeacherOfOneField(field_id);
    }
    //Teacher get all interested field
    @RequestMapping(value = "/field/showteacher/{teacher_id}", method = RequestMethod.GET)
    public List<FieldDTO> showAllInterestedFieldOfOneTeacher(@PathVariable("teacher_id") long teacher_id){
        return fieldService.showAllInterestedFieldOfOneTeacher(teacher_id);
    }
}

