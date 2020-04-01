package com.ssanusi.javaoauth2.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
//@ApiModel(value = "useremails", description = "A list of secondary email for user")
@Table(name = "useremail", uniqueConstraints = { @UniqueConstraint(columnNames = {"userid", "useremail"})})
public class Useremail extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long useremailid;

    @Column(nullable = false)
    @Email(message = "Email must be a valid format username@domain.com")
    private String useremail;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    @JsonIgnoreProperties("useremails")
    private User user;

    public Useremail(){
    }

    public Useremail(User user, String useremail){
        setUser(user);
        setUseremail(useremail);
    }

    public long getUseremailid() {
        return useremailid;
    }

    public void setUseremailid(long useremailid) {
        this.useremailid = useremailid;
    }

    public String getUseremail() {
        if(useremail == null)
            return null;
        else
            return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
