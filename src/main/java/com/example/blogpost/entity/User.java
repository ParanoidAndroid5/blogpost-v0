package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotNull
    @JsonProperty("username")
    @Column(name = "user_name")
    String userName;

    @NotNull
    String password;


}
