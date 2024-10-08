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
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody Post post){

        if(post.getImg() == null || post.getImg().isEmpty())
        {
            post.setImg("https://cdn.buttercms.com/oEgJ8IVRNySxVYWtxo9B");
        }



        Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);


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
    @GetMapping("/{postId}/likes")
    public ResponseEntity<Integer> getPostLikes(@PathVariable Long postId) {
        try {
            int likesCount = postService.getLikesCount(postId);
            return ResponseEntity.ok(likesCount);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
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

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getPostsByUserName(@PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUsername(username));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{postId}")
    public void deletePostById(@PathVariable Long postId, @RequestParam Long adminUserId){
        postService.deletePostById(postId, adminUserId);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) {
        try {
            Post existingPost = postService.getPostById(postId);

            if (updatedPost.getName() != null && !updatedPost.getName().isEmpty()) {
                existingPost.setName(updatedPost.getName());
            }

            if (updatedPost.getContent() != null && !updatedPost.getContent().isEmpty()) {
                existingPost.setContent(updatedPost.getContent());
            }

            if (updatedPost.getImg() != null && !updatedPost.getImg().isEmpty()) {
                existingPost.setImg(updatedPost.getImg());
            }



            Post savedPost = postService.savePost(existingPost);
            return ResponseEntity.ok(savedPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
