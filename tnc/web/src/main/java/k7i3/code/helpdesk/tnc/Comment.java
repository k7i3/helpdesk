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
}
