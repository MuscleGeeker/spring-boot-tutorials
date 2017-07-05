package me.musclegeeker.demo.service;

import me.musclegeeker.demo.domain.User;
import me.musclegeeker.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by MuscleGeeker on 2017/7/4.
 */
@Service
public class UserService {

    @Resource
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
