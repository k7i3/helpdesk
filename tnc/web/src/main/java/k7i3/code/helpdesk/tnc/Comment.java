package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 29.01.15.
 */
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="creation_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="creation_DidBy"))
    })
    @Embedded
    private LifeCycleInfo creation;
    @AttributeOverrides({
            @AttributeOverride(name="date", column= @Column(name="deletion_Date")),
            @AttributeOverride(name="didBy", column= @Column(name="deletion_DidBy"))
    })
    @Embedded
    private LifeCycleInfo deletion;
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private CommentInfo commentInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<CommentInfo> commentInfoHistory = new ArrayList<>();

//    @ElementCollection
//    private List<String> contentHistory = new ArrayList<>();

    public Comment() {
    }

    public Comment(LifeCycleInfo creation, CommentInfo commentInfo) {
        this.creation = creation;
        this.commentInfo = commentInfo;
    }

    public Long getId() {
        return id;
    }

    public LifeCycleInfo getCreation() {
        return creation;
    }

    public void setCreation(LifeCycleInfo creation) {
        this.creation = creation;
    }

    public LifeCycleInfo getDeletion() {
        return deletion;
    }

    public void setDeletion(LifeCycleInfo deletion) {
        this.deletion = deletion;
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
