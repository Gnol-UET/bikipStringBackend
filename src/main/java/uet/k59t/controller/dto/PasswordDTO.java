package uet.k59t.controller.dto;

/**
 * Created by Longlaptop on 12/15/2016.
 */

public class PasswordDTO {
    private String username;
    private String oldpassword;
    private String newpassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldpassword() {
        return oldpassword;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }
}
