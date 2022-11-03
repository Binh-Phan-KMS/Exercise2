package com.binh.exercise2.controller;

import com.binh.exercise2.error.InvalidInputException;
import com.binh.exercise2.error.NoRecordFoundException;
import com.binh.exercise2.model.User;
import com.binh.exercise2.service.UserService;

import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserService svc;

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        User temp = svc.saveUser(user);
        if (temp == null) {
            log.info("ADD NEW USER FAILED: " + user);
                return new ResponseEntity<>(temp, HttpStatus.BAD_REQUEST);
        }

        log.info("ADD NEW USER: " + user);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id) {
        User user = svc.getUserById(id);
        if (user == null) {
            log.info("GET USER BY ID FAILED: " + user);
            Map<String, String> res = new HashMap<>();
            res.put("error",new NoRecordFoundException().toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("GET USER BY ID: " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<?> findAllUsers() {
        List<User> userList = svc.getUsers();
        if (userList.isEmpty()) {
            log.info("GET ALL USERS FAILED");
            Map<String, String> res = new HashMap<>();
            res.put("error",new NoRecordFoundException().toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("GET ALL USERS: " + userList);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            svc.updateUser(user);
        } catch(Exception exception) {
            log.info("USER UPDATED FAILED: " + user);
            Map<String, String> res = new HashMap<>();
            res.put("error",exception.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("UPDATE USER: " + user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable ObjectId id) {
        String response = svc.deleteUser(id);
        if (response == null) {
            log.info("USER DELETED FAILED");
            return new ResponseEntity<>("Failed to delete user " + id, HttpStatus.BAD_REQUEST);
        }

        log.info("USER DELETED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
