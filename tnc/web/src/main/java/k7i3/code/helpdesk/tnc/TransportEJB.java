package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@Stateless
public class TransportEJB implements Serializable {
    @Inject @Data
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public Transport createTransport(Transport transport) {
        logger.info("=>=>=>=>=> TransportEJB.createTransport()");
        em.persist(transport);
        return transport;
    }

    public Transport updateTransport(Transport transport) {
        logger.info("=>=>=>=>=> TransportEJB.updateTransport()");
        em.merge(transport);
        return transport;
    }

    public List<Transport> findAllAccessibleTransport(User user) {
        logger.info("=>=>=>=>=> TransportEJB.findAllAccessibleTransport");

        if (user == null) return new ArrayList<>();

        Set<String> projects = user.getProjects();
        Set<String> branches = user.getBranches();

        if (projects.isEmpty()) {
            if (branches.isEmpty()) return findAllTransport();
            else return findAllAccessibleTransportByBranches(branches);
        } else {
            if (branches.isEmpty()) return findAllAccessibleTransportByProjects(projects);
            else return findAllAccessibleTransportByProjectsAndBranches(projects, branches);
        }
    }

    public List<Transport> findAllAccessibleTransportByProjectsAndBranches(Set<String> projects, Set<String> branches) {
        logger.info("=>=>=>=>=> TransportEJB.findAllAccessibleTransportByProjectsAndBranches");
        return em.createNamedQuery("findAllAccessibleTransportByProjectsAndBranches", Transport.class).setParameter("projects", projects).setParameter("branches", branches).getResultList();
    }

    public List<Transport> findAllAccessibleTransportByProjects(Set<String> projects) {
        logger.info("=>=>=>=>=> TransportEJB.findAllAccessibleTransportByProjects");
        return em.createNamedQuery("findAllAccessibleTransportByProjects", Transport.class).setParameter("projects", projects).getResultList();
    }

    public List<Transport> findAllAccessibleTransportByBranches(Set<String> branches) {
        logger.info("=>=>=>=>=> TransportEJB.findAllAccessibleTransportByBranches");
        return em.createNamedQuery("findAllAccessibleTransportByBranches", Transport.class).setParameter("branches", branches).getResultList();
    }

    public List<Transport> findAllTransport() {
        logger.info("=>=>=>=>=> TransportEJB.findAllTransport");
        return em.createNamedQuery("findAllTransport", Transport.class).getResultList();
    }

    public List<String> findAllProjects() {
        logger.info("=>=>=>=>=> TransportEJB.findAllProjects");
        return em.createNamedQuery("findAllProjects", String.class).getResultList();
    }

    public List<String> findAllBranches() {
        logger.info("=>=>=>=>=> TransportEJB.findAllBranches");
        return em.createNamedQuery("findAllBranches", String.class).getResultList();
    }

    public List<String> findAllTransportModels() {
        logger.info("=>=>=>=>=> TransportEJB.findAllTransportModels");
        return em.createNamedQuery("findAllTransportModels", String.class).getResultList();
    }

    public List<String> findAllTerminalModels() {
        logger.info("=>=>=>=>=> TransportEJB.findAllTerminalModels");
        return em.createNamedQuery("findAllTerminalModels", String.class).getResultList();
    }

    public List<String> findAllFirmware() {
        logger.info("=>=>=>=>=> TransportEJB.findAllFirmware");
        return em.createNamedQuery("findAllFirmware", String.class).getResultList();
    }

    public List<String> findAllRoutes() {
        logger.info("=>=>=>=>=> TransportEJB.findAllRoutes");
        return em.createNamedQuery("findAllRoutes", String.class).getResultList();
    }

    public Transport findTransportById(Long id) {
        logger.info("=>=>=>=>=> TransportEJB.findTransportById");
        return em.find(Transport.class, id);
    }
}
