package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

//    @SuppressWarnings("unchecked")
//    public List <Object[]> countTicketsByResults() {
//        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByResults()");
//        return em.createNamedQuery("countTicketsByResults").getResultList();
//    }

    public int countAllTickets() {
        logger.info("=>=>=>=>=> StatisticsEJB.countAllTickets()");
        return ((Number)em.createNamedQuery("countAllTickets").getSingleResult()).intValue();
    }

    public int countTicketsByStatus(TicketStatus ticketStatus) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByStatus(TicketStatus ticketStatus)");
        return ((Number)em.createNamedQuery("countTicketsByStatus").setParameter("status", ticketStatus).getSingleResult()).intValue();
    }

    public int countTicketsByResult(TicketResult ticketResult) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByResult(TicketResult ticketResult)");
        return ((Number)em.createNamedQuery("countTicketsByResult").setParameter("result", ticketResult).getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByStatuses() {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByStatuses()");
        return em.createNamedQuery("countTicketsByStatuses").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByStatusesBetweenDates(Date startDate, Date endDate) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByStatusesBetweenDates()");
        return em.createNamedQuery("countTicketsByStatusesBetweenDates").setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByHeaders() {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByHeaders()");
        return em.createNamedQuery("countTicketsByHeaders").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByProjects() {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByProjects()");
        return em.createNamedQuery("countTicketsByProjects").getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByBranches() {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByBranches()");
        return em.createNamedQuery("countTicketsByBranches").getResultList();
    }
}
