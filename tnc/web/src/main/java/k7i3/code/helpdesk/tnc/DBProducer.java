package k7i3.code.helpdesk.tnc;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by k7i3 on 29.01.15.
 */
public class DBProducer {
    @Produces
    @PersistenceContext(unitName = "helpdeskPU")
    private EntityManager em;

    @Produces
    @PersistenceContext(unitName = "userPU")
    private EntityManager emForUser;
}
