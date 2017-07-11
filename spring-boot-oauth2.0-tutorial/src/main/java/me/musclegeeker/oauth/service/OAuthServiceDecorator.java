package me.musclegeeker.oauth.service;

import me.musclegeeker.oauth.domain.OAuthUser;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
public abstract class OAuthServiceDecorator implements OAuthService {

    private final OAuthService oAuthService;

    private final String oAuthType;

    private final String authorizationUrl;

    public OAuthServiceDecorator(OAuthService oAuthService, String type) {
        super();
        this.oAuthService = oAuthService;
        this.oAuthType = type;
        this.authorizationUrl = oAuthService.getAuthorizationUrl(null);
    }

    public String getoAuthType() {
        return oAuthType;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public abstract OAuthUser getOAuthUser(Token accessToken);

    @Override
    public Token getRequestToken() {
        return oAuthService.getRequestToken();
    }

    @Override
    public Token getAccessToken(Token token, Verifier verifier) {
        return oAuthService.getAccessToken(token, verifier);
    }

    @Override
    public void signRequest(Token token, OAuthRequest oAuthRequest) {
        oAuthService.signRequest(token, oAuthRequest);
    }

    @Override
    public String getVersion() {
        return oAuthService.getVersion();
    }

    @Override
    public String getAuthorizationUrl(Token token) {
        return oAuthService.getAuthorizationUrl(token);
    }
}
