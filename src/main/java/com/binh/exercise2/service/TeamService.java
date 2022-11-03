package com.binh.exercise2.service;

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
public class TeamService {

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private UserRepository userRepo;

    public Team saveTeam(Team team) {
        return teamRepo.save(team);
    }

    public Team getTeam(ObjectId id) {
        return teamRepo.findById(id).orElse(null);
    }

    public Team updateTeam(Team team) {
        Team temp = teamRepo.findById(team.getID()).orElse(null);

        try {
            temp.setName(team.getName());
            temp.setOffice(team.getOffice());
            if (!team.getMembers().isEmpty()) {
                temp.setMembers(team.getMembers());
            }
        } catch (NullPointerException exception) {
            throw new NoRecordFoundException();
        }

        teamRepo.save(temp);
        for (int i = 0; i < team.getMembers().size(); i++) {
            User user = team.getMembers().get(i);
            user.setTeam(temp);
            userRepo.save(user);
        }

        return temp;
    }

    public String deleteTeam(ObjectId id) {
        try {
            teamRepo.deleteById(id);
        } catch (IllegalArgumentException exception){
            return null;
        }
        return "Team removed " + id;
    }
}
