package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.security.Principal;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.03.15.
 */
@Named
@Stateless
public class UserEJB implements Serializable {
    @Inject @Users
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public User createUser(User user) {
        logger.info("=>=>=>=>=> UserEJB.createUser()");
        em.persist(user);
        return user;
    }

    public User initUser() {
        Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        if (principal != null) {
            return findUserByName(principal.getName());
        }
        return null;
    }

    public User findUserByName(String name) {
        logger.info("=>=>=>=>=> UserEJB.findUserByName()");

        return em.find(User.class, name);
    }
}
