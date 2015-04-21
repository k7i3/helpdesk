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

    public List<TicketHeader> findAllTicketHeaders() {
        logger.info("=>=>=>=>=> TicketEJB.findAllTicketHeaders");
        return em.createNamedQuery("findAllTicketHeaders", TicketHeader.class).getResultList();
    }

    public List<TicketResult> findAllTicketResults() {
        logger.info("=>=>=>=>=> TicketEJB.findAllTicketResults");
        return em.createNamedQuery("findAllTicketResults", TicketResult.class).getResultList();
    }

    public TicketHeader findTicketHeaderById(String s) {
        logger.info("=>=>=>=>=> TicketEJB.findTicketHeaderById");
        return em.find(TicketHeader.class, s);
    }

    public TicketResult findTicketResultById(String s) {
        logger.info("=>=>=>=>=> TicketEJB.findTicketResultById");
        return em.find(TicketResult.class, s);
    }

    public TicketResult createTicketResult(TicketResult ticketResult) {
        logger.info("=>=>=>=>=> TicketEJB.createTicketResult");
        em.persist(ticketResult);
        return ticketResult;
    }

    public TicketHeader createTicketHeader(TicketHeader ticketHeader) {
        logger.info("=>=>=>=>=> TicketEJB.createTicketHeader");
        em.persist(ticketHeader);
        return ticketHeader;
    }

    public TicketHeader updateTicketHeader(TicketHeader ticketHeader) {
        logger.info("=>=>=>=>=> TicketEJB.updateTicketHeader()");
        em.merge(ticketHeader);
        return ticketHeader;
    }

    public TicketResult updateTicketResult(TicketResult ticketResult) {
        logger.info("=>=>=>=>=> TicketEJB.updateTicketHeader()");
        em.merge(ticketResult);
        return ticketResult;
    }
}
