package me.musclegeeker.demo.repository;

import me.musclegeeker.demo.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by MuscleGeeker on 2017/7/4.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * 通过用户名获取用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);
}
