package uet.k59t.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.k59t.controller.dto.CouncilDTO;
import uet.k59t.controller.stereotype.RequiredRoles;
import uet.k59t.model.Role;
import uet.k59t.service.CouncilService;
import uet.k59t.service.TopicService;

/**
 * Created by Longlaptop on 12/14/2016.
 */
@RestController
public class CouncilController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private CouncilService councilService;

    //Create council
    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/council/create", method = RequestMethod.POST)
    public CouncilDTO createCouncil(@RequestBody CouncilDTO councilDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return councilService.createCouncil(token,councilDTO);
    }
    //Show a council
    @RequiredRoles({Role.MODERATOR, Role.TEACHER})
    @RequestMapping(value = "/council/{council_id}", method = RequestMethod.GET)
    public CouncilDTO showCouncil (@PathVariable(value = "council_id") Long councilId, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return councilService.showCouncil(token, councilId);
    }
    //Add teacher to council
    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/council/{council_id}/addteacher/{teacher_id}", method = RequestMethod.GET)
    public CouncilDTO addTeacher(@PathVariable(value = "teacher_id") Long teacherId,
                                 @PathVariable(value = "council_id") Long councilId,
                                 HttpServletRequest httpServletRequest){
            String token = httpServletRequest.getHeader("auth-token");
        return councilService.addTeacherToCouncil(token,councilId,teacherId);


    }
    //Grade a topic
    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/council/grade", method =RequestMethod.POST)
    public CouncilDTO gradeATopic(@RequestBody CouncilDTO councilDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return councilService.gradeATopic(token,councilDTO);
    }
}
