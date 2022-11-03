package com.binh.exercise2.service;

import com.binh.exercise2.constant.Role;
import com.binh.exercise2.error.InvalidInputException;
import com.binh.exercise2.error.NoRecordFoundException;
import com.binh.exercise2.model.Team;
import com.binh.exercise2.model.User;
import com.binh.exercise2.repository.TeamRepository;
import com.binh.exercise2.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TeamRepository teamRepo;

    public User getUserById(ObjectId id) {
        return userRepo.findById(id).orElse(null);
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public User saveUser(User user) {
        if (verifyRole(user.getRole())) {
            return userRepo.save(user);
        }

        return null;
    }

    public String deleteUser(ObjectId id) {
        try {
            userRepo.deleteById(id);
        } catch(IllegalArgumentException exception) {
            return null;
        }
        return "User deleted " + id;
    }

    public User updateUser(User user) {
        if (!verifyRole(user.getRole())) {
            throw new InvalidInputException("Role");
        }

        Team team = teamRepo.findById(user.getTeam().getID()).orElse(null);
        if (team == null) {
            throw new InvalidInputException("Team");
        }

        User existingUser = userRepo.findById(user.getID()).orElse(null);

        try {
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setOffice(user.getOffice());
            existingUser.setTeam(user.getTeam());
            existingUser.setRole(user.getRole());
        } catch(NullPointerException e) {
            throw new NoRecordFoundException();
        }

        userRepo.save(existingUser);
        team.addMembers(existingUser);
        teamRepo.save(team);

        return existingUser;
    }

    private boolean verifyRole(Role role) {
        return role.equals(Role.MEMBER) || role.equals(Role.SCRUM_MASTER) || role.equals(Role.PROJECT_OWNER) || role.equals(Role.OTHER);
    }
}
