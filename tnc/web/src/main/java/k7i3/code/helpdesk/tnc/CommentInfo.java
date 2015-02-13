package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by k7i3 on 13.02.15.
 */
@Entity
public class CommentInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;
    @NotNull
    private String updatedBy;
    private String deletedBy;
    @NotNull
    private String content;

    public CommentInfo() {
    }

    public CommentInfo(String updatedBy, String content) {
        this.updateDate = new Date();
        this.updatedBy = updatedBy;
        this.content = content;
    }

    public Long getId() {
        return id;
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

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
