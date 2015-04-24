package k7i3.code.helpdesk.tnc;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by k7i3 on 29.01.15.
 */
public class DBProducer {
    @Produces @Data
    @PersistenceContext(unitName = "dataPU")
    private EntityManager emData;

    @Produces @Users
    @PersistenceContext(unitName = "usersPU")
    private EntityManager emUsers;

    @Produces @Notices
    @PersistenceContext(unitName = "noticesPU")
    private EntityManager emNotices;


}
