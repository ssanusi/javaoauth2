package com.ssanusi.javaoauth2.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@ApiModel(value = "Role", description = "Description what access the user has")
public class Role extends Auditable {

    @ApiModelProperty(name = "role id", value = "primary key for Role", required = true, example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleid;


    @ApiModelProperty(name = "name", value = "The Name of the role this name is hard coded", required = true, example = "DATA")
    @Column(nullable = false, unique = true)
    private String name;

    @ApiModelProperty(name = "UserRoles", value = "A list of the Users who are assigned this role")
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("role")
    private List<UserRoles> userRolesList = new ArrayList<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name.toUpperCase();
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        if(name == null)
            return null;
        else
            return name.toUpperCase();
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public List<UserRoles> getUserRolesList() {
        return userRolesList;
    }

    public void setUserRolesList(List<UserRoles> userRolesList) {
        this.userRolesList = userRolesList;
    }
}
