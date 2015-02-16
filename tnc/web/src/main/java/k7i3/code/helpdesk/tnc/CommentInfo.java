package k7i3.code.helpdesk.tnc;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 13.02.15.
 */
@Entity
public class CommentInfo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Embedded
    private LifeCycleInfo modification;
    @Embedded
    private LifeCycleInfo deletion;
    @NotNull
    private String content;

    public CommentInfo() {
    }

    public CommentInfo(LifeCycleInfo modification, String content) {
        this.modification = modification;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public LifeCycleInfo getModification() {
        return modification;
    }

    public void setModification(LifeCycleInfo modification) {
        this.modification = modification;
    }

    public LifeCycleInfo getDeletion() {
        return deletion;
    }

    public void setDeletion(LifeCycleInfo deletion) {
        this.deletion = deletion;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
