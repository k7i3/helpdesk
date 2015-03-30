package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by k7i3 on 20.03.15.
 */
@Entity
@Table(name = "USERS")
public class User {

    public static enum ROLE {
        ADMIN, USER, SERVICE
    }

    @Id
    private String login;
    @NotNull
    private String password;
    @Enumerated(EnumType.STRING)
    private ROLE role;
    @ElementCollection
    private Set<String> projects = new HashSet<>();
    @ElementCollection
    private Set<String> branches = new HashSet<>();

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
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
}
