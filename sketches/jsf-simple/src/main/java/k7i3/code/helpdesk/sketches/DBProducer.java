package k7i3.code.helpdesk.sketches;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


/**
 * @author Antonio Goncalves
 *         APress Book - Beginning Java EE 7 with Glassfish 4
 *         http://www.apress.com/
 *         http://www.antoniogoncalves.org
 *         --
 */
public class DBProducer {

  @Produces
  @PersistenceContext(unitName = "chapter11PU")
  private EntityManager em;
}
