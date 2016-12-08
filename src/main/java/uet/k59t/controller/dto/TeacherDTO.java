package uet.k59t.controller.dto;

import uet.k59t.model.Role;

/**
 * Created by Longlaptop on 11/24/2016.
 */
public class TeacherDTO extends UserDTO {
    private String email;
    private String unit;

    public TeacherDTO() {
        this.setRole(Role.TEACHER);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
