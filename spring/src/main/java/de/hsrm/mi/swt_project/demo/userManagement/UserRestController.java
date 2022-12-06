package de.hsrm.mi.swt_project.demo.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST-Controller for all user related topics.
 * These involve login and logout.
 * Accessable by client side.
 * 
 * @author Tom Gouthier
 */
@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserServiceImpl userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * When posting a user to /login, the user is added to the List of Users if its
     * unique
     * 
     * @return SendUserDTO
     * 
     * @author Tom Gouthier
     */
    @PostMapping("/login")
    public SendUserDTO addNewUser(@RequestBody GetUserDTO user) {

        try {
            userService.addUser(user.name());
            return SendUserDTO.from(user.name(), "");
        } catch (UserNotUniqueException|UsernameTooShortException e) {
            return SendUserDTO.from(user.name(), e.getMessage());
        }
    }

    /**
     * When posting a user to /logout, the user is deleted of the list of Users and
     * therefore logged out
     * 
     * @return SendUserDTO
     * 
     * @author Tom Gouthier
     */
    @DeleteMapping("/logout")
    public SendUserDTO deleteUser(@RequestParam("username") String name) {

        userService.removeUser(name);
        return SendUserDTO.from(name, "");

    }
}
