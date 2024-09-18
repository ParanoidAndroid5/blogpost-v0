package com.example.blogpost.controller;

import com.example.blogpost.entity.Post;
import com.example.blogpost.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "*")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody Post post)
    {
        try{
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);

        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId){
        try{
            Post post = postService.getPostById(postId);
            return ResponseEntity.ok(post);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
    @PutMapping("/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable Long postId){
        try{
            postService.likePost(postId);
            return ResponseEntity.ok(new String[] {"Post liked successfully"});
        } catch(EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> getPostsByName(@PathVariable String name){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.searchByName(name));
        }
        catch(EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getPostsByUserName(@PathVariable String username){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
        }
        catch(EntityNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId, @RequestParam Long adminUserId){
        postService.deletePostById(postId, adminUserId);
    }

}
