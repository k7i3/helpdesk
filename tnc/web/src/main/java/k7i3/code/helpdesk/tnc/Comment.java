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
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @NotNull
    private String createdBy;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private CommentInfo commentInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentInfo> commentInfoHistory = new ArrayList<>();

//    @ElementCollection
//    private List<String> contentHistory = new ArrayList<>();

    public Comment() {
    }

    public Comment(String createdBy, CommentInfo commentInfo) {
        this.createDate = new Date();
        this.createdBy = createdBy;
        this.commentInfo = commentInfo;
    }

    public Long getId() {
        return id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CommentInfo getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfo commentInfo) {
        this.commentInfo = commentInfo;
    }

    public List<CommentInfo> getCommentInfoHistory() {
        return commentInfoHistory;
    }

    public void setCommentInfoHistory(List<CommentInfo> commentInfoHistory) {
        this.commentInfoHistory = commentInfoHistory;
    }
}
