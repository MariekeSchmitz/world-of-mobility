package de.hsrm.mi.swt_project.demo.userManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserListConfig {

    @Bean
    public UserList userList() {
        UserList userList = new UserList();
        return userList;
    }
}
