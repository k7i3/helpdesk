package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@Stateless
public class TransportEJB {
    @Inject
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public Transport createTransport(Transport transport) {
        logger.info("=>=>=>=>=> TransportEJB.createTransport()");

        em.persist(transport);
        return transport;
    }

    public List<Transport> findAllTransport() {
        logger.info("=>=>=>=>=> TransportEJB.findAllTransport");

        return em.createNamedQuery("findAllTransport", Transport.class).getResultList();
    }

    public Transport findTransportById(Long id) {
        return em.find(Transport.class, id);
    }
}
