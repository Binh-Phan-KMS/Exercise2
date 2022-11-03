package com.binh.exercise2.controller;

import com.binh.exercise2.error.NoRecordFoundException;
import com.binh.exercise2.model.Task;
import com.binh.exercise2.service.TaskService;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class TaskController {
    @Autowired
    private TaskService svc;

    @PostMapping("/task")
    public ResponseEntity<?> addTeam(@RequestBody Task task) {
        Task temp = svc.saveTask(task);
        if (temp == null) {
            log.info("ADD NEW TEAM FAILED: " + task);
            Map<String, String> res = new HashMap<>();
            res.put("error",new NoRecordFoundException().toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("ADD NEW TEAM: " + task);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable ObjectId id) {
        Task task = svc.getTask(id);
        if (task == null) {
            log.info("GET TASK BY ID FAILED: " + task);
            return new ResponseEntity<>(task, HttpStatus.BAD_REQUEST);
        }
        log.info("GET TASK BY ID: " + task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/task")
    public ResponseEntity<?> updateTeam(@RequestBody Task task) {
        try {
            svc.updateTask(task);
        } catch(Exception exception) {
            log.info("TASK UPDATED FAILED: " + task);
            Map<String, String> res = new HashMap<>();
            res.put("error",exception.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("UPDATE TASK: " + task);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }

    @DeleteMapping("/task/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable ObjectId id) {
        String response = svc.deleteTask(id);
        if (response == null) {
            log.info("TASK DELETED FAILED");
            return new ResponseEntity<>("Failed to delete user " + id, HttpStatus.BAD_REQUEST);
        }

        log.info("TASK DELETED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
