package com.example.blogpost.service;

import com.example.blogpost.entity.Post;
import com.example.blogpost.entity.User;
import com.example.blogpost.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public Post savePost(Post post){

        User user = userService.getUserByUsername(post.getPostedBy());
        post.setUser(user);
        post.setDate(new Date());

        return postRepository.save(post);

    }
    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Post getPostById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            post.setViewCount(post.getViewCount()+1);
            return postRepository.save(post);
        }else
        {
            throw new EntityNotFoundException("Post not found.");
        }

    }
    public void likePost(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if(optionalPost.isPresent()){
            Post post =  optionalPost.get();
            post.setLikeCount(post.getLikeCount()+1);
            postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post not found."+ postId);
        }
    }

    public List<Post> searchByName(String name )
    {
        return postRepository.findAllByNameContaining(name);
    }

    @Override
    public List<Post> getPostsByUsername(String username) {

        User user = userService.getUserByUsername(username);

        return postRepository.findByPostedBy(user.getUserName())
                .orElseThrow(()-> new NoSuchElementException("no post found for user"));

    }


}
