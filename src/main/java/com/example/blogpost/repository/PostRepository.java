package com.example.blogpost.repository;

import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findAllByNameContaining(String name);
    List<Post> findByUser(User user);
    Optional<List<Post>> findByUsername(String username);


}
