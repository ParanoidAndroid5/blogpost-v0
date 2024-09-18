package com.example.blogpost.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    User user;

    @Column(length = 5000)
    private String content;
    private String username;
    private String img;
    private Date date;
    @ColumnDefault("0")
    private int likeCount;
    @ColumnDefault("0")
    private int viewCount;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

}
