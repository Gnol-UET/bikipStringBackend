package uet.k59t.controller.dto;

import uet.k59t.model.Role;

/**
 * Created by Longlaptop on 11/24/2016.
 */
public class StudentDTO extends UserDTO {
    private String email;
    private String grade;
    private String classname;


    public StudentDTO() {
        this.setRole(Role.STUDENT);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }


}
