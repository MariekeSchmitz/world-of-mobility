package de.hsrm.mi.swt_project.demo.userManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    private UserServiceImpl userService;

    @Autowired
    public UserRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public SendUserDTO addNewUser(@RequestBody GetUserDTO user) {

        try {
            userService.addUser(user.name());
            return SendUserDTO.from(user.name(), "");
        } catch (UserNotUniqueException e) {
            return SendUserDTO.from(user.name(), e.getMessage());
        }
    }

    @DeleteMapping("/logout")
    public SendUserDTO deleteUser(@RequestParam("username") String name) {

        userService.removeUser(name);
        return SendUserDTO.from(name, "");

    }
}
