package com.binh.exercise2.service;

import com.binh.exercise2.model.Task;
import com.binh.exercise2.model.User;
import com.binh.exercise2.repository.TaskRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository repo;

    public Task saveTask(Task task) {
        if (task.getAssignee() == null) {
            task.setAssignee(new User());
        }

        if (task.getEstimatedTime() == null) {
            task.setEstimatedTime(0);
        }

        LocalDateTime dateTime = LocalDateTime.now().plusHours(task.getEstimatedTime());
        task.setDeadline(dateTime);
        return repo.save(task);
    }

    public Task getTask(ObjectId id) {
        return repo.findById(id).orElse(null);
    }

    public Task updateTask(Task task) {
        Task temp = repo.findById(task.getID()).orElse(null);
        temp.setTitle(task.getTitle());
        temp.setDescription(task.getDescription());
        temp.setStatus(task.getStatus());
        temp.setEstimatedTime(task.getEstimatedTime());

        repo.save(temp);

        return temp;
    }

    public String deleteTask(ObjectId id) {
        repo.deleteById(id);
        return "Team removed " + id;
    }


}
