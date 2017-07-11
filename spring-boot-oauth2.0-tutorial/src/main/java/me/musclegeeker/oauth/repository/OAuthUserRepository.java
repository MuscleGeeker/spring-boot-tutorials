package me.musclegeeker.oauth.repository;

import me.musclegeeker.oauth.domain.OAuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
public interface OAuthUserRepository extends JpaRepository<OAuthUser, Integer> {
    OAuthUser findByOAuthTypeAndOAuthId(String oAuthType, String oAuthId);
}
