package de.hsrm.mi.swt_project.demo.userManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserList userList;

    @Autowired
    public UserServiceImpl(UserList userList) {
        this.userList = userList;
    }

    @Override
    public String addUser(String username) throws UserNotUniqueException {

        if (!userList.getUserList().contains(username)) {
            userList.getUserList().add(username);
            return username;
        } else {
            throw new UserNotUniqueException("User is not unique");
        }
    }

    @Override
    public void removeUser(String username) {

        if (userList.getUserList().contains(username)) {
            userList.getUserList().remove(username);
        }
    }
}
