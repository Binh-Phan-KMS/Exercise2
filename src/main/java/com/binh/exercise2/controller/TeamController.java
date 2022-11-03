package com.binh.exercise2.controller;

import com.binh.exercise2.error.NoRecordFoundException;
import com.binh.exercise2.model.Team;
import com.binh.exercise2.model.User;
import com.binh.exercise2.service.TeamService;
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
public class TeamController {
    @Autowired
    private TeamService svc;

    @PostMapping("/team")
    public ResponseEntity<?> addTeam(@RequestBody Team team) {
        Team temp = svc.saveTeam(team);
        if (temp == null) {
            log.info("ADD NEW TEAM FAILED: " + team);
            Map<String, String> res = new HashMap<>();
            res.put("error",new NoRecordFoundException().toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("ADD NEW TEAM: " + team);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<?> getTeamById(@PathVariable ObjectId id) {
        Team team = svc.getTeam(id);
        if (team == null) {
            log.info("GET TEAM BY ID FAILED: " + team);
            return new ResponseEntity<>(team, HttpStatus.BAD_REQUEST);
        }
        log.info("GET TEAM BY ID: " + team);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PutMapping("/team")
    public ResponseEntity<?> updateTeam(@RequestBody Team team) {
        try {
            svc.updateTeam(team);
        } catch(Exception exception) {
            log.info("TEAM UPDATED FAILED: " + team);
            Map<String, String> res = new HashMap<>();
            res.put("error",exception.toString());
            return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
        }

        log.info("UPDATE TEAM: " + team);
        return new ResponseEntity<>(team, HttpStatus.OK);

    }

    @DeleteMapping("/team/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable ObjectId id) {
        String response = svc.deleteTeam(id);
        if (response == null) {
            log.info("TEAM DELETED FAILED");
            return new ResponseEntity<>("Failed to delete user " + id, HttpStatus.BAD_REQUEST);
        }

        log.info("TEAM DELETED SUCCESSFULLY");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
