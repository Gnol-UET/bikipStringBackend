package uet.k59t.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uet.k59t.controller.dto.FacultyDTO;
import uet.k59t.service.FacultyService;

/**
 * Created by Longlaptop on 11/24/2016.
 */
@RestController
public class FacultyController {
    @Autowired
    private FacultyService facultyService;

    @RequestMapping(value = "/faculty", method = RequestMethod.GET)
    public void createFaculty(){
        facultyService.createFaculty();
    }

    @RequestMapping(value= "/faculty/createTeacher", method = RequestMethod.POST)
    public void createTeacher(@RequestParam("file") MultipartFile multipartFile){
        facultyService.createTeacher(multipartFile);
    }
}
