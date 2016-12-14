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


    //STUDENT
    //student register a topic with topic name, teacherId
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/topic/student/studentregister", method = RequestMethod.POST)
    public TopicDTO studentRegister(@RequestBody TopicDTO topicDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.studentRegister(topicDTO, token);
    }
    //Student see a registered topic by topicID
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/topic/student/registeredtopic/{topic_id}", method = RequestMethod.GET)
    public TopicDTO showATopic(@PathVariable(value = "topic_id") Long topicId, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.showATopic(topicId,token);
    }
    //Student edit a registerd topic by topicID
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/topic/student/registeredtopic/{topic_id}", method = RequestMethod.PUT)
    public TopicDTO editATopic(@PathVariable(value = "topic_id") Long topicId, @RequestBody TopicDTO topicDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.editATopic(topicId, topicDTO, token);
    }
    //Student delete a registered topic by topicID
    @RequiredRoles(Role.STUDENT)
    @RequestMapping(value = "/topic/student/registeredtopic/{topic_id}", method = RequestMethod.DELETE)
    public String deleteATopic(@PathVariable(value = "topic_id") Long topicId, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.deleteATopic(topicId,token);
    }

    //TEACHER
    //teacher show all topics which are registered(delegate) to them
    @RequiredRoles(Role.TEACHER)
    @RequestMapping(value = "/topic/teacher/registeredtopic", method = RequestMethod.GET)
    public List<TopicDTO> showRegisteredTopic(HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.showRegisteredTopic(token);
    }
    //teacher accept a topic by topic id
    @RequiredRoles(Role.TEACHER)
    @RequestMapping(value = "/topic/teacher/registeredtopic/{topic_id}", method = RequestMethod.POST)
    public TopicDTO acceptTopic(@PathVariable(value = "topic_id") Long topicId, @RequestBody AcceptTopicDTO acceptTopicDTO, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("auth-token");
        return topicService.acceptTopic(topicId,acceptTopicDTO,token);
    }



}
