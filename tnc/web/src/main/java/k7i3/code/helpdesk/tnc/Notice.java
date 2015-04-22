package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by k7i3 on 22.04.15.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "findAllNotices", query = "SELECT n FROM Notice n")
})
public class Notice implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String content;
    @NotNull
    private String didBy;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Notice() {
    }

    public Notice(String content, String didBy, Date date) {
        this.content = content;
        this.didBy = didBy;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
