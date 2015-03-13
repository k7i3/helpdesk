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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LifeCycleInfo)) return false;

        LifeCycleInfo that = (LifeCycleInfo) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (didBy != null ? !didBy.equals(that.didBy) : that.didBy != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = date != null ? date.hashCode() : 0;
        result = 31 * result + (didBy != null ? didBy.hashCode() : 0);
        return result;
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
