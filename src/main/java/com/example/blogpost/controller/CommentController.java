package com.example.blogpost.controller;

import com.example.blogpost.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin

public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestParam Long postId, @RequestParam String postedBy, @RequestBody String content){
        try{
            return ResponseEntity.ok(commentService.createComment(postId, postedBy, content));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Long postId)
    {
        try{
            return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("something went wrong.");
        }
    }
    @DeleteMapping("/{commentId}")
    public void deletePostById(@PathVariable Long commentId, @RequestParam Long adminUserId){
        commentService.deleteComment(commentId, adminUserId);
    }


}
