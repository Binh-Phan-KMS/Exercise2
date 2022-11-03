package com.binh.exercise2.repository;

import com.binh.exercise2.model.Team;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, ObjectId> {

}
