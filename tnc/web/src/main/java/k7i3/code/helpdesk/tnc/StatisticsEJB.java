package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
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

    public int countTicketsByResult(TicketResult ticketResult, Date startDate, Date endDate, Set<String> projects, Set<String> branches, Set<TicketHeader> ticketHeaders, Set<TicketStatus> ticketStatuses) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByResult(TicketResult ticketResult)");
        return ((Number)em.createNamedQuery("countTicketsByResult").setParameter("result", ticketResult).setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).setParameter("projects", projects).setParameter("branches", branches).setParameter("ticketHeaders", ticketHeaders).setParameter("ticketStatuses", ticketStatuses).getSingleResult()).intValue();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByStatuses(Date startDate, Date endDate, Set<String> projects, Set<String> branches, Set<TicketHeader> ticketHeaders, Set<TicketStatus> ticketStatuses) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByStatuses()");
        return em.createNamedQuery("countTicketsByStatuses").setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).setParameter("projects", projects).setParameter("branches", branches).setParameter("ticketHeaders", ticketHeaders).setParameter("ticketStatuses", ticketStatuses).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByHeaders(Date startDate, Date endDate, Set<String> projects, Set<String> branches, Set<TicketHeader> ticketHeaders, Set<TicketStatus> ticketStatuses) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByHeaders()");
        return em.createNamedQuery("countTicketsByHeaders").setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).setParameter("projects", projects).setParameter("branches", branches).setParameter("ticketHeaders", ticketHeaders).setParameter("ticketStatuses", ticketStatuses).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByProjects(Date startDate, Date endDate, Set<String> projects, Set<String> branches, Set<TicketHeader> ticketHeaders, Set<TicketStatus> ticketStatuses) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByProjects()");
        return em.createNamedQuery("countTicketsByProjects").setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).setParameter("projects", projects).setParameter("branches", branches).setParameter("ticketHeaders", ticketHeaders).setParameter("ticketStatuses", ticketStatuses).getResultList();
    }

    @SuppressWarnings("unchecked")
    public List <Object[]> countTicketsByBranches(Date startDate, Date endDate, Set<String> projects, Set<String> branches, Set<TicketHeader> ticketHeaders, Set<TicketStatus> ticketStatuses) {
        logger.info("=>=>=>=>=> StatisticsEJB.countTicketsByBranches()");
        return em.createNamedQuery("countTicketsByBranches").setParameter("startDate", startDate, TemporalType.TIMESTAMP).setParameter("endDate", endDate, TemporalType.TIMESTAMP).setParameter("projects", projects).setParameter("branches", branches).setParameter("ticketHeaders", ticketHeaders).setParameter("ticketStatuses", ticketStatuses).getResultList();
    }
}
