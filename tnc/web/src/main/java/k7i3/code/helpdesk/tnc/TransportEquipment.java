package k7i3.code.helpdesk.tnc;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by k7i3 on 16.02.15.
 */
@Embeddable
public class TransportEquipment {
    @NotNull
    private Boolean hasSpeaker;
    @NotNull
    private Boolean hasPtt;
    @NotNull
    private Boolean hasDvr;
    @NotNull
    private Boolean hasInformer;

    public TransportEquipment() {
    }

    public TransportEquipment(Boolean hasSpeaker, Boolean hasPtt, Boolean hasDvr, Boolean hasInformer) {
        this.hasSpeaker = hasSpeaker;
        this.hasPtt = hasPtt;
        this.hasDvr = hasDvr;
        this.hasInformer = hasInformer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportEquipment)) return false;

        TransportEquipment that = (TransportEquipment) o;

        if (hasDvr != null ? !hasDvr.equals(that.hasDvr) : that.hasDvr != null) return false;
        if (hasInformer != null ? !hasInformer.equals(that.hasInformer) : that.hasInformer != null) return false;
        if (hasPtt != null ? !hasPtt.equals(that.hasPtt) : that.hasPtt != null) return false;
        if (hasSpeaker != null ? !hasSpeaker.equals(that.hasSpeaker) : that.hasSpeaker != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hasSpeaker != null ? hasSpeaker.hashCode() : 0;
        result = 31 * result + (hasPtt != null ? hasPtt.hashCode() : 0);
        result = 31 * result + (hasDvr != null ? hasDvr.hashCode() : 0);
        result = 31 * result + (hasInformer != null ? hasInformer.hashCode() : 0);
        return result;
    }

    public Boolean getHasSpeaker() {
        return hasSpeaker;
    }

    public void setHasSpeaker(Boolean hasSpeaker) {
        this.hasSpeaker = hasSpeaker;
    }

    public Boolean getHasPtt() {
        return hasPtt;
    }

    public void setHasPtt(Boolean hasPtt) {
        this.hasPtt = hasPtt;
    }

    public Boolean getHasDvr() {
        return hasDvr;
    }

    public void setHasDvr(Boolean hasDvr) {
        this.hasDvr = hasDvr;
    }

    public Boolean getHasInformer() {
        return hasInformer;
    }

    public void setHasInformer(Boolean hasInformer) {
        this.hasInformer = hasInformer;
    }
}
