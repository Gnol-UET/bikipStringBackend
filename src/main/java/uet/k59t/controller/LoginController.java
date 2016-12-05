package uet.k59t.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uet.k59t.controller.dto.StudentDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.service.LoginService;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/signup/student", method = RequestMethod.POST)
    public StudentDTO createUser(@RequestBody StudentDTO studentDTO){
        return loginService.createStudent(studentDTO);
    }
    @RequestMapping(value = "/signup/teacher", method = RequestMethod.POST)
    public TeacherDTO createtTeacher(@RequestBody TeacherDTO teacherDTO){
        return loginService.createTeacher(teacherDTO);
    }
}
