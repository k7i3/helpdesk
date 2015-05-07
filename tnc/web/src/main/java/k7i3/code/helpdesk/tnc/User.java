package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by k7i3 on 20.03.15.
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
        @NamedQuery(name = "findAllUsers", query = "SELECT u FROM User u")
})
public class User implements Serializable{

    @Id
    private String login;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<UserRole> roles = new HashSet<>();
    @ElementCollection
    private Set<String> projects = new HashSet<>();
    @ElementCollection
    private Set<String> branches = new HashSet<>();
    private String company;

    public User() {
    }

    public User(String login, String password) {
        this.login = login.trim();
        this.password = password.trim();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password.trim();
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public void setProjects(Set<String> projects) {
        this.projects = projects;
    }

    public Set<String> getBranches() {
        return branches;
    }

    public void setBranches(Set<String> branches) {
        this.branches = branches;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
