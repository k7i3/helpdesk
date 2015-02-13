package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by k7i3 on 29.01.15.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String owner;
    @NotNull
    private String content;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    @ElementCollection
    private List<String> contentHistory = new ArrayList<>();

    public Comment() {
    }

    public Comment(String owner, String content, Date createDate, Date updateDate, Date deleteDate, List<String> contentHistory) {
        this.owner = owner;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.deleteDate = deleteDate;
        this.contentHistory = contentHistory;
    }

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public List<String> getContentHistory() {
        return contentHistory;
    }

    public void setContentHistory(List<String> contentHistory) {
        this.contentHistory = contentHistory;
    }
}
