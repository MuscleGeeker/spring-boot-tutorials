package me.musclegeeker.oauth.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by MuscleGeeker on 2017/7/10.
 */
@Entity
public class OAuthUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    private User user;

    private String oAuthType;

    private String oAuthId;

    public OAuthUser() {
    }

    public OAuthUser(User user, String oAuthType, String oAuthId) {
        this.user = user;
        this.oAuthType = oAuthType;
        this.oAuthId = oAuthId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getoAuthType() {
        return oAuthType;
    }

    public void setoAuthType(String oAuthType) {
        this.oAuthType = oAuthType;
    }

    public String getoAuthId() {
        return oAuthId;
    }

    public void setoAuthId(String oAuthId) {
        this.oAuthId = oAuthId;
    }

    @Override
    public String toString() {
        return "OAuthUser{" +
                "id=" + id +
                ", user=" + user +
                ", oAuthType='" + oAuthType + '\'' +
                ", oAuthId='" + oAuthId + '\'' +
                '}';
    }
}
