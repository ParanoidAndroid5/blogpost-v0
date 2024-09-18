package com.example.blogpost.service;

import com.example.blogpost.entity.Post;

import java.util.List;

public interface PostService {
    Post savePost(Post post);

    List<Post> getAllPosts();

    Post getPostById(Long id);

    void likePost(Long postId);

    public List<Post> searchByName(String name );
}
