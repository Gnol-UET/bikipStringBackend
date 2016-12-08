package uet.k59t.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uet.k59t.controller.dto.StudentDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.controller.dto.UserDTO;
import uet.k59t.controller.stereotype.RequiredRoles;
import uet.k59t.model.Role;
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public UserDTO login(@RequestBody UserDTO userDTO){
        return loginService.login(userDTO);
    };

    @RequiredRoles({Role.STUDENT, Role.TEACHER})
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        if(token != null) loginService.logout(token);
        else throw new NullPointerException("Không có auth-token");

    }

}
