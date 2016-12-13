package uet.k59t.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uet.k59t.controller.dto.AcceptTopicDTO;
import uet.k59t.controller.dto.TeacherDTO;
import uet.k59t.controller.dto.TopicDTO;
import uet.k59t.controller.stereotype.RequiredRoles;
import uet.k59t.model.Role;
import uet.k59t.service.TopicService;

import java.util.List;

/**
 * Created by Long on 12/10/2016.
 */
@RestController
public class TopicController {
    @Autowired
    private TopicService topicService;

    @RequiredRoles({Role.MODERATOR,Role.STUDENT,Role.TEACHER})
    @RequestMapping(value = "/showallteachers" , method = RequestMethod.GET)
    public List<TeacherDTO> showAllTeachers(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.showAllTeachers(token);
    }

    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/topic/open", method = RequestMethod.GET)
    public String openRegister(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.openRegister(token);
    }
    @RequiredRoles(Role.MODERATOR)
    @RequestMapping(value = "/topic/close", method = RequestMethod.GET)
    public String closeRegister(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.closeRegister(token);
    }
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/topic/studentregister", method = RequestMethod.POST)
    public TopicDTO studentRegister(@RequestBody TopicDTO topicDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.studentRegister(topicDTO, token);
        //requestBody
    }

    @RequiredRoles(Role.TEACHER)
    @RequestMapping(value = "/topic/teacher/registeredtopic", method = RequestMethod.GET)
    public List<TopicDTO> showRegisteredTopic(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.showRegisteredTopic(token);
    }

    @RequiredRoles(Role.TEACHER)
    @RequestMapping(value = "/topic/teacher/registeredtopic/{topic_id}", method = RequestMethod.POST)
    public TopicDTO acceptTopic(@PathVariable(value = "topic_id") Long topicId, @RequestBody AcceptTopicDTO acceptTopicDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.acceptTopic(topicId,acceptTopicDTO,token);
    }

}
