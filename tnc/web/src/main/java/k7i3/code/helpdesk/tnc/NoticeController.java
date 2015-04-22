package k7i3.code.helpdesk.tnc;


import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

/**
 * Created by k7i3 on 22.04.15.
 */
@Named
@RequestScoped
public class NoticeController {
    @Inject
    private NoticeEJB noticeEJB;
    @Inject
    private UserEJB userEJB;

    private Notice notice = new Notice();
    private List<Notice> notices;
    private List<Notice> checkboxSelectedNotices;

    @PostConstruct
    private void doFindAllNotices() {
        notices = noticeEJB.findAllNotices();
    }

    public void doAddNotice() {
        notice.setDate(new Date());
        notice.setDidBy(userEJB.initUser().getLogin());
        noticeEJB.createNotice(notice);

        FacesMessage msg = new FacesMessage("Сохранено (уведомление)", notice.getDate().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        Notice editedNotice = (Notice) event.getObject();

        noticeEJB.updateNotice(editedNotice);

        FacesMessage msg = new FacesMessage("Сохранено (новость)", editedNotice.getDate().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Отмена", ((Notice) event.getObject()).getDate().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<Notice> getCheckboxSelectedNotices() {
        return checkboxSelectedNotices;
    }

    public void setCheckboxSelectedNotices(List<Notice> checkboxSelectedNotices) {
        this.checkboxSelectedNotices = checkboxSelectedNotices;
    }
}
