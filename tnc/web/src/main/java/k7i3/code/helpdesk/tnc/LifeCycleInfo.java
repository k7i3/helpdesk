package k7i3.code.helpdesk.tnc;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by k7i3 on 16.02.15.
 */
@Embeddable
public class LifeCycleInfo {
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @NotNull
    private String didBy;

    public LifeCycleInfo() {
    }

    public LifeCycleInfo(Date date, String didBy) {
        this.date = date;
        this.didBy = didBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }
}
