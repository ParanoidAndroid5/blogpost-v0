package com.example.blogpost.service;

import com.example.blogpost.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long postId, String postedBy , String content);

    List<Comment> getCommentsByPostId(Long postId);

    Comment getCommentById(Long commentId);

    void deleteComment(Long postId, Long userId);
}
