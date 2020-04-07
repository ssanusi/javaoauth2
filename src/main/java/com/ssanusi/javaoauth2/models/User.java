package com.ssanusi.javaoauth2.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssanusi.javaoauth2.logging.Loggable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Entity
@Table(name = "users")
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;


    @Column(nullable = false)
    @Email
    private String primaryemail;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("user")
    private List<UserRoles> userroles = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("user")
    private List<Useremail> useremails = new ArrayList<>();

    public User() {
    }

    public User(String username, String password, String primaryemail, List<UserRoles> userRoles) {
         setUsername(username);
         setPassword(password);
         setPrimaryemail(primaryemail);
         for(UserRoles ur: userRoles){
             ur.setUser(this);
         }
         setUserroles(userRoles);
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        if(username == null)
            return null;
        else
            return username.toLowerCase();
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public void setPasswordNoEncrypt(String password)
    {
        this.password = password;
    }

    public String getPrimaryemail() {
        if( primaryemail == null)
            return null;
        else
            return primaryemail.toLowerCase();
    }

    public void setPrimaryemail(String primaryemail) {
        this.primaryemail = primaryemail.toLowerCase();
    }

    public List<UserRoles> getUserroles() {
        return userroles;
    }

    public void setUserroles(List<UserRoles> userroles) {
        this.userroles = userroles;
    }

    public List<Useremail> getUseremails() {
        return useremails;
    }

    public void setUseremails(List<Useremail> useremails) {
        this.useremails = useremails;
    }

    @JsonIgnore
    public List<SimpleGrantedAuthority> getAuthority(){
        List<SimpleGrantedAuthority> rtnList = new ArrayList<>();

        for (UserRoles r :
                this.userroles) {
            String myRole = "Role_" + r.getRole().getName().toUpperCase();
            rtnList.add(new SimpleGrantedAuthority(myRole));
        }

        return rtnList;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", primaryemail='" + primaryemail + '\'' +
                ", userroles=" + userroles +
                ", useremails=" + useremails +
                '}';
    }
}
