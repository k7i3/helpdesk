package k7i3.code.helpdesk.tnc;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 30.03.15.
 */
@Named
@SessionScoped
public class UserController implements Serializable {
    @Inject
    private UserEJB userEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private User user;

    private void doInitUser() {
//        if (user == null) {
//            Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
//            if (principal != null) {
//                user = userEJB.findUserByName(principal.getName());
//            }
//        }
        if (user == null) {
            user = userEJB.initUser();
        }
    }

    public Boolean isAdmin() {
        doInitUser();
        return user != null && getUser().getRoles().contains(User.ROLE.ADMIN);
    }

    public Boolean isService() {
        doInitUser();
        return user != null && getUser().getRoles().contains(User.ROLE.SERVICE);
    }

    public Boolean isUser() {
        doInitUser();
        return user != null && getUser().getRoles().contains(User.ROLE.USER);
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
//        return "login?faces-redirect=true";
        return "/main.faces?faces-redirect=true";
    }

    public User getUser() {
        doInitUser();
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
