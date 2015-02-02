package k7i3.code.helpdesk.tnc;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by k7i3 on 28.01.15.
 */
@Entity
public class Terminal {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private int number;
    @NotNull
    private String firmware;
    @NotNull
    private String mobile;
    @OneToOne (cascade = CascadeType.PERSIST)
    private Point lastPoint;
    @OneToMany (cascade = CascadeType.PERSIST)
    private List<Ticket> tickets = new ArrayList<>();

    public Terminal() {
    }

    public Terminal(Integer number, String firmware, String mobile, Point lastPoint, List<Ticket> tickets) {
        this.number = number;
        this.firmware = firmware;
        this.mobile = mobile;
        this.lastPoint = lastPoint;
        this.tickets = tickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Point getLastPoint() {
        return lastPoint;
    }

    public void setLastPoint(Point lastPoint) {
        this.lastPoint = lastPoint;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
