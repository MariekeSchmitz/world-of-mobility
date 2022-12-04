package de.hsrm.mi.swt_project.demo.userManagement;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for User List. Makes sure that only a single instance of
 * userList is active.
 * Also makes userList autowireable with dependency injection
 * 
 * @author Tom Gouthier
 */
@Configuration
public class UserListConfig {

    /**
     * @return UserList
     */
    @Bean
    public UserList userList() {
        UserList userList = new UserList();
        return userList;
    }
}
