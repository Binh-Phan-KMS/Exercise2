package com.binh.exercise2.repository;

import com.binh.exercise2.model.Task;
import com.binh.exercise2.model.Team;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, ObjectId> {
}
