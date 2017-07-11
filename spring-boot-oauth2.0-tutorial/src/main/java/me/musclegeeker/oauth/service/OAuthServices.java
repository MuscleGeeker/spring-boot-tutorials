package me.musclegeeker.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
@Service
public class OAuthServices {

    @Autowired
    private List<OAuthServiceDecorator> oAuthServiceDecorators;

    public OAuthServiceDecorator getOAuthService(String type) {
        Optional<OAuthServiceDecorator> oAuthService = oAuthServiceDecorators.stream().filter(o -> o.getoAuthType().equals(type))
                .findFirst();
        if (oAuthService.isPresent()) {
            return oAuthService.get();
        }
        return null;
    }

    public List<OAuthServiceDecorator> getAllOAuthServices() {
        return oAuthServiceDecorators;
    }
}
