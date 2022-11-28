package de.hsrm.mi.swt_project.demo.userManagement;

import java.util.LinkedList;

/**
 * List of all Users, up to date at all times. Used to check, if user is unique
 * 
 * @author Tom Gouthier
 */
public class UserList {

    private LinkedList<String> userList;

    public UserList() {
        userList = new LinkedList<>();
    }

    public LinkedList<String> getUserList() {
        return userList;
    }

    public void setUserList(LinkedList<String> userList) {
        this.userList = userList;
    }

}
