package de.hsrm.mi.swt_project.demo.userManagement;

public interface UserService {

    String addUser(String username) throws UserNotUniqueException;

    void removeUser(String username);
}
