package k7i3.code.helpdesk.tnc;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 22.04.15.
 */
@Named
@Stateless
public class NoticeEJB implements Serializable{
    @Inject @Notices
    private EntityManager em;
    private Logger logger = Logger.getLogger("k7i3");

    public List<Notice> findAllNotices() {
        logger.info("=>=>=>=>=> NoticeEJB.findAllNotices");
        return em.createNamedQuery("findAllNotices", Notice.class).getResultList();
    }

    public Notice createNotice(Notice notice) {
        logger.info("=>=>=>=>=> NoticeEJB.createNotice");
        em.persist(notice);
        return notice;
    }

    public Notice updateNotice(Notice notice) {
        logger.info("=>=>=>=>=> NoticeEJB.updateNotice");
        em.merge(notice);
        return notice;
    }
}
