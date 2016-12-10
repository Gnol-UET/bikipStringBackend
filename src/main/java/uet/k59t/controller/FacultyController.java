package uet.k59t.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import uet.k59t.controller.stereotype.RequiredRoles;
import uet.k59t.model.Moderator;
import uet.k59t.model.Role;
import uet.k59t.repository.ModeratorRepository;
import uet.k59t.service.FacultyService;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@RestController
public class FacultyController {
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private ModeratorRepository moderatorRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void createModerator() {
        Moderator moderator = new Moderator();
        moderator.setUsername("mod");
        moderator.setPassword("1");
        moderatorRepository.save(moderator);
    }

    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/faculty", method = RequestMethod.GET)
    public void createFaculty(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("auth-token");

        facultyService.createFaculty(token);
    }

    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/faculty/createTeacher", method = RequestMethod.POST)
    public void createTeacher(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("auth-token");
        facultyService.createTeacher(multipartFile, token);
    }

    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/faculty/createStudent", method = RequestMethod.POST)
    public void createStudent(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader("auth-token");
        facultyService.createStudent(multipartFile, token);
    }
}
