package uet.k59t.controller.dto;

import uet.k59t.model.Teacher;

import java.util.List;

/**
 * Created by Longlaptop on 12/14/2016.
 */
public class CouncilDTO {
    private Long id;
    private String councilname;
    private String comment;
    private Long score;
    private Long topicid;
    private List<TeacherDTO> teacherList;

    public Long getTopicid() {
        return topicid;
    }

    public void setTopicid(Long topicid) {
        this.topicid = topicid;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public List<TeacherDTO> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<TeacherDTO> teacherList) {
        this.teacherList = teacherList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCouncilname() {
        return councilname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }



    public void setCouncilname(String councilname) {
        this.councilname = councilname;
    }
}
