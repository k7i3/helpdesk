package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.03.15.
 */
@Named
@Stateless
public class UserEJB implements Serializable {
    @Inject
    private EntityManager emForUser;
    private Logger logger = Logger.getLogger("k7i3");

    public User createUser(User user) {
        logger.info("=>=>=>=>=> UserEJB.createUser()");
        emForUser.persist(user);
        return user;
    }
}
