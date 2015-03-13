package k7i3.code.helpdesk.tnc;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 16.02.15.
 */
@Entity
public class TicketDetails {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Boolean possibilityOfServiceAtTheDefaultPlace;
    @NotNull
    private Boolean isInspected;
    @NotNull
    private String place;
    @NotNull
    private String mobileNumberOfDriver;
    @NotNull
    private String mobileNumberOfAgent;

    public TicketDetails() {
    }

    public TicketDetails(Boolean possibilityOfServiceAtTheDefaultPlace, Boolean isInspected, String place, String mobileNumberOfDriver, String mobileNumberOfAgent) {
        this.possibilityOfServiceAtTheDefaultPlace = possibilityOfServiceAtTheDefaultPlace;
        this.isInspected = isInspected;
        this.place = place;
        this.mobileNumberOfDriver = mobileNumberOfDriver;
        this.mobileNumberOfAgent = mobileNumberOfAgent;
    }

    public TicketDetails(TicketDetails ticketDetails) {
        this.possibilityOfServiceAtTheDefaultPlace = ticketDetails.getPossibilityOfServiceAtTheDefaultPlace();
        this.isInspected = ticketDetails.getIsInspected();
        this.place = ticketDetails.getPlace();
        this.mobileNumberOfDriver = ticketDetails.getMobileNumberOfDriver();
        this.mobileNumberOfAgent = ticketDetails.getMobileNumberOfAgent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketDetails)) return false;

        TicketDetails that = (TicketDetails) o;

        if (isInspected != null ? !isInspected.equals(that.isInspected) : that.isInspected != null) return false;
        if (mobileNumberOfAgent != null ? !mobileNumberOfAgent.equals(that.mobileNumberOfAgent) : that.mobileNumberOfAgent != null)
            return false;
        if (mobileNumberOfDriver != null ? !mobileNumberOfDriver.equals(that.mobileNumberOfDriver) : that.mobileNumberOfDriver != null)
            return false;
        if (place != null ? !place.equals(that.place) : that.place != null) return false;
        if (possibilityOfServiceAtTheDefaultPlace != null ? !possibilityOfServiceAtTheDefaultPlace.equals(that.possibilityOfServiceAtTheDefaultPlace) : that.possibilityOfServiceAtTheDefaultPlace != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = possibilityOfServiceAtTheDefaultPlace != null ? possibilityOfServiceAtTheDefaultPlace.hashCode() : 0;
        result = 31 * result + (isInspected != null ? isInspected.hashCode() : 0);
        result = 31 * result + (place != null ? place.hashCode() : 0);
        result = 31 * result + (mobileNumberOfDriver != null ? mobileNumberOfDriver.hashCode() : 0);
        result = 31 * result + (mobileNumberOfAgent != null ? mobileNumberOfAgent.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    public Boolean getPossibilityOfServiceAtTheDefaultPlace() {
        return possibilityOfServiceAtTheDefaultPlace;
    }

    public void setPossibilityOfServiceAtTheDefaultPlace(Boolean possibilityOfServiceAtTheDefaultPlace) {
        this.possibilityOfServiceAtTheDefaultPlace = possibilityOfServiceAtTheDefaultPlace;
    }

    public Boolean getIsInspected() {
        return isInspected;
    }

    public void setIsInspected(Boolean isInspected) {
        this.isInspected = isInspected;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getMobileNumberOfDriver() {
        return mobileNumberOfDriver;
    }

    public void setMobileNumberOfDriver(String mobileNumberOfDriver) {
        this.mobileNumberOfDriver = mobileNumberOfDriver;
    }

    public String getMobileNumberOfAgent() {
        return mobileNumberOfAgent;
    }

    public void setMobileNumberOfAgent(String mobileNumberOfAgent) {
        this.mobileNumberOfAgent = mobileNumberOfAgent;
    }
}
