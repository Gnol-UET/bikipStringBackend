package uet.k59t.controller.dto;

import uet.k59t.model.Role;

/**
 * Created by Longlaptop on 11/24/2016.
 */
public class TeacherDTO {
    private String username;
    private String password;
    private String email;
    private Role role;
    private String unit;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
