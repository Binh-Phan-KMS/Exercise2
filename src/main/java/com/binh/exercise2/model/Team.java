package com.binh.exercise2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Team {
    @Id
    private ObjectId ID;

    private String name;

    private String office;

    @DBRef
    @JsonManagedReference
    private List<User> members;

    public void addMembers(User user) {
        this.members.add(user);
    }
}
