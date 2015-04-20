package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 06.03.15.
 */
@Named
@Stateless
public class TicketEJB implements Serializable {
    @Inject @Data
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public Ticket updateTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketEJB.updateTicket()");
        em.merge(ticket);
        return ticket;
    }

    public Ticket findTicketById(Long id) {
        logger.info("=>=>=>=>=> TicketEJB.findTicketById");
        return em.find(Ticket.class, id);
    }

    public List<TicketHeader> findAllActiveTicketHeaders() {
        logger.info("=>=>=>=>=> TicketEJB.findAllActiveTicketHeaders");
        return em.createNamedQuery("findAllActiveTicketHeaders", TicketHeader.class).getResultList();
    }

    public List<TicketResult> findAllActiveTicketResults() {
        logger.info("=>=>=>=>=> TicketEJB.findAllActiveTicketResults");
        return em.createNamedQuery("findAllActiveTicketResults", TicketResult.class).getResultList();
    }

    public TicketHeader findTicketHeaderById(String s) {
        return em.find(TicketHeader.class, s);
    }
}
