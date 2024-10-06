package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.example.blogpost.constants.BlogpostConstants.ADMIN_ID;

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

    public static boolean isUserAdmin(Long userId){
       return isUserMatching(ADMIN_ID, userId);
    }
    public static boolean isUserMatching(Long currentUserId, Long otherUserId) {
        return currentUserId.equals(otherUserId);
    }
}
