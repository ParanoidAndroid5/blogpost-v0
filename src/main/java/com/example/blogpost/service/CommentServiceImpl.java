package com.example.blogpost.service;

import com.example.blogpost.entity.Comment;
import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.User;
import com.example.blogpost.repository.CommentRepository;
import com.example.blogpost.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Comment createComment(Long postId, String postedBy , String content){
        Optional<Post> optionalPost = postRepository.findById(postId);

        User user = userService.getUserByUsername(postedBy);

        if(optionalPost.isPresent())
        {
            Comment comment = new Comment();

            comment.setPost(optionalPost.get());
            comment.setContent(content);
            comment.setCreatedAt(new Date());
            comment.setUser(user);
            comment.setPostedBy(user.getUserName());

            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("post not found.");

    }

    public List<Comment> getCommentsByPostId(Long postId)
    {
        return commentRepository.findByPostId(postId);
    }
}

