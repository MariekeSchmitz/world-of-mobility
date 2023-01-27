package de.hsrm.mi.swt_project.demo.usermanagement;

import java.util.LinkedList;
import java.util.List;

/**
 * List of all Users, up to date at all times
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
public class UserList {

    private List<String> users;

    public UserList() {
        users = new LinkedList<>();
    }

    public List<String> getUserList() {
        return users;
    }

    public void setUserList(List<String> userList) {
        this.users = userList;
    }

}
