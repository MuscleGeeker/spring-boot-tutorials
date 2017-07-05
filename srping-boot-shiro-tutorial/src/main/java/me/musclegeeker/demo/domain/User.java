package me.musclegeeker.demo.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by MuscleGeeker on 2017/7/4.
 */
@Entity
public class User implements Serializable {

    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;// 账户

    private String name;// 姓名

    private String password;// 密码

    private String salt;// 加密密码的盐

    private byte state; // 用户的状态：0-等待验证的用户；1-正常用户；2-锁定

    @ManyToMany(fetch = FetchType.EAGER)// 立即从数据库获取数据
    @JoinTable(name = "SysUserRole", joinColumns = {@JoinColumn(name = "uid")}, inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roleList;// 一个用户可以拥有多种角色

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    /**
     * 密码盐
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", state=" + state +
                ", roleList=" + roleList +
                '}';
    }
}
