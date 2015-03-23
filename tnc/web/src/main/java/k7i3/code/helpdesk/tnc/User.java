package k7i3.code.helpdesk.tnc;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by k7i3 on 20.03.15.
 */
@Entity
public class User {
    @Id
    private String login;
    @NotNull
    private String password;
    @ElementCollection
    private Set<String> roles = new HashSet<>();
    @ElementCollection
    private Set<String> projects = new HashSet<>();
    @ElementCollection
    private Set<String> branches = new HashSet<>();
}
