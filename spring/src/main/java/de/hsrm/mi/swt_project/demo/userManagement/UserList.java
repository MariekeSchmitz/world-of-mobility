package de.hsrm.mi.swt_project.demo.userManagement;

import java.util.LinkedList;
import java.util.List;

/**
 * List of all Users, up to date at all times. Used to check, if user is unique
 * 
 * @author Tom Gouthier
 */
public class UserList {

    private List<String> userList;

    public UserList() {
        userList = new LinkedList<>();
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

}
