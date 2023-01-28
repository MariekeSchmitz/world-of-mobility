package de.hsrm.mi.swt_project.demo.usermanagement.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for User List. Makes sure that only a single instance of
 * userList is active.
 * Also makes userList autowireable via dependency injection
 * 
 * @author Tom Gouthier, Marie Bohnert
 */
@Configuration
public class UserListConfig {

    /**
     * @return UserList
     */
    @Bean
    public UserList userList() {
        return new UserList();
    }
}
