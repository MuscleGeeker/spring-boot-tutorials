package me.musclegeeker.oauth.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import me.musclegeeker.oauth.domain.OAuthTypes;
import me.musclegeeker.oauth.domain.OAuthUser;
import me.musclegeeker.oauth.domain.User;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
public class GithubOAuthService extends OAuthServiceDecorator {

    private static final String PROTECTED_RESOURCE_URL = "https://api.github.com/user";

    public GithubOAuthService(OAuthService oAuthService) {
        super(oAuthService, OAuthTypes.GITHUB);
    }

    @Override
    public OAuthUser getOAuthUser(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        this.signRequest(accessToken, request);
        Response response = request.send();
        OAuthUser oAuthUser = new OAuthUser();
        oAuthUser.setoAuthType(super.getoAuthType());
        Object result = JSON.parse(response.getBody());
        oAuthUser.setoAuthId(JSONPath.eval(result, "$.id").toString());
        oAuthUser.setUser(new User());
        oAuthUser.getUser().setUsername(JSONPath.eval(result, "$.login").toString());
        return oAuthUser;
    }


}
