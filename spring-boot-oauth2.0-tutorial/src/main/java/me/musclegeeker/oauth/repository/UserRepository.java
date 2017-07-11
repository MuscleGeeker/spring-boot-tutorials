package me.musclegeeker.oauth.repository;

import me.musclegeeker.oauth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
