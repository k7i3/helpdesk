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
