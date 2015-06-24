package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.06.15.
 */
@Named
@Stateless
public class StatisticsEJB implements Serializable {
    @Inject @Data
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public int countAllTickets() {
        return ((Number)em.createNamedQuery("countAllTickets").getSingleResult()).intValue();
    }

    public int countOpenedTickets() {
        return ((Number)em.createNamedQuery("countOpenedTickets").getSingleResult()).intValue();
    }
}
