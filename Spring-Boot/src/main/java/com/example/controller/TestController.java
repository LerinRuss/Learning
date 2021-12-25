package com.example.controller;

import com.example.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TestController {
    private User users[] = new User[] {
            new User(2, "Anton2"),
            new User(1, "Anton1"),
            new User(3, "Anton3")
    };


    @GetMapping("/kek")
    public User getUserName(@RequestParam("name") String name) {
        for (User user : users) {
            if( user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @GetMapping("/kek/{id}")
    public User getUserId(@PathVariable("id") int id) {
        for (User user : users) {
            if( user.getId() == id) {
                return user;
            }
        }

        return null;
    }
}
