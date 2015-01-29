package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@Stateless
public class TransportEJB {
    @Inject
    private EntityManager em;

    public Transport createTransport(Transport transport) {
        em.persist(transport);
        return transport;
    }

    public List<Transport> findAllTransport() {
        return em.createNamedQuery("findAllTransport", Transport.class).getResultList();
    }

    public Transport findTransportById(Long id) {
        return em.find(Transport.class, id);
    }
}
