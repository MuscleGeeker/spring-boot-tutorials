package me.musclegeeker.oauth.config;

import me.musclegeeker.oauth.API.GithubApi;
import me.musclegeeker.oauth.domain.OAuthTypes;
import me.musclegeeker.oauth.service.GithubOAuthService;
import me.musclegeeker.oauth.service.OAuthServiceDecorator;
import org.scribe.builder.ServiceBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by MuscleGeeker on 2017/7/11.
 */
@Configuration
public class GithubOAuthConfig {

    // 回调地址
    private static final String CALLBACK_URL = "%s/oauth/%s/callback";

    @Value("${OAuth.github.clientId}")
    private String githubClientId;

    @Value("${OAuth.github.clientSecret}")
    private String githubClientSecret;

    @Value("${OAuth.github.state}")
    private String state;

    @Value("${demo.host}")
    private String host;

    @Bean
    public GithubApi getGithubApi() {
        return new GithubApi(state);
    }

    @Bean
    public OAuthServiceDecorator getGithubOAuthService() {
        return new GithubOAuthService(new ServiceBuilder()
                .provider(getGithubApi())
                .apiKey(githubClientId)
                .apiSecret(githubClientSecret)
                .callback(String.format(CALLBACK_URL, host, OAuthTypes.GITHUB))
                .build());
    }
}
