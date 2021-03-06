package k7i3.code.helpdesk.tnc;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 24.02.15.
 */
@Named
@RequestScoped
public class TicketController {
    @Inject
    private TransportEJB transportEJB;
    @Inject
    private TicketEJB ticketEJB;
    @Inject
    private UserEJB userEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private Transport unitOfTransport;
    private Ticket ticket = new Ticket();
    private List<TicketStatus> ticketStatuses = Arrays.asList(TicketStatus.values());

//    private List<TicketHeader> ticketHeaders = Arrays.asList(TicketHeader.values());
//    private List<TicketResult> ticketResults = Arrays.asList(TicketResult.values());
    private List<TicketHeader> ticketHeaders;
    private List<TicketHeader> allTicketHeaders;
    private List<TicketResult> ticketResults;

    List<TicketResult> selectedTicketResults;
    private String didBy;
    private String commentContent;

    //Init

    @PostConstruct
    public void init() {
        didBy = userEJB.initUser().getLogin();
        ticketHeaders = ticketEJB.findAllActiveTicketHeaders();
        allTicketHeaders = ticketEJB.findAllTicketHeaders();
        ticketResults = ticketEJB.findAllActiveTicketResults();
    }

    //Do ADD

    public void doAddTransportComment(Transport transportFromView) {
        logger.info("=>=>=>=>=> TicketController.doAddTransportComment()");
        Comment comment = new Comment(new LifeCycleInfo(new Date(), didBy), new CommentInfo(new LifeCycleInfo(new Date(), didBy), commentContent));
        Transport transportFromDb = transportEJB.findTransportById(transportFromView.getId());
        transportFromDb.getComments().add(comment);
        transportFromView.setComments(transportFromDb.getComments()); // for updating view
        transportEJB.updateTransport(transportFromDb);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Сохранено (комментарий к транспорту)", transportFromView.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicketComment(Ticket ticketFromView) {
        logger.info("=>=>=>=>=> TicketController.doAddTicketComment()");
        Comment comment = new Comment(new LifeCycleInfo(new Date(), didBy), new CommentInfo(new LifeCycleInfo(new Date(), didBy), commentContent));
        Ticket ticketFromDb = ticketEJB.findTicketById(ticketFromView.getId());
        ticketFromDb.getComments().add(comment);
        ticketFromView.setComments(ticketFromDb.getComments()); // for updating view
        ticketEJB.updateTicket(ticketFromDb);
        commentContent = null;

        FacesMessage msg = new FacesMessage("Сохранено (комментарий к заявке)", ticketFromView.getTicketInfo().getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doAddTicket() {
        logger.info("=>=>=>=>=> TicketController.doAddTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToAddTicket(unitOfTransport)) {
            FacesMessage msg = new FacesMessage("Уже есть активная заявка", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }
        //TODO update unitOfTransport from the database is going on above in 'if' statement (doCheckForPossibilityToAddTicket(unitOfTransport)), but may be repeat it for logicality
        //unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        prepareNewTicketInfo(ticket.getTicketInfo(), lifeCycleInfo, TicketStatus.OPENED);

        ticket.setCreation(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "ОТКРЫТИЕ: " +  commentContent))); // difference
        unitOfTransport.getTickets().add(ticket); // difference

        setCurrentTicketHeaderAndTicketStatus(ticket); // difference

        transportEJB.updateTransport(unitOfTransport);

        FacesMessage msg = new FacesMessage("Сохранено (заявка)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
//        doReset(); for PF('addTicketDialog').show() via JS (@SessionScoped) and for Primefaces dialog framework (@SessionScoped)
//        RequestContext.getCurrentInstance().closeDialog(null); for Primefaces dialog framework (@SessionScoped)
    }

    //Do DELETE

    public void doDeleteTicket() {
//        TODO add checking for update by another user in this time (doesn't work)
//        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
//        ticket = ticketEJB.findTicketById(ticket.getId());

        unitOfTransport.getTickets().remove(ticket);

        if (unitOfTransport.getTickets().size() == 0) {
            setCurrentTicketHeaderAndTicketStatus(null);
        } else {
            setCurrentTicketHeaderAndTicketStatus(unitOfTransport.getTickets().get(unitOfTransport.getTickets().size() - 1));
        }

        transportEJB.updateTransport(unitOfTransport);
        ticketEJB.deleteTicket(ticket);

        FacesMessage msg = new FacesMessage("Удалено (заявка)", ticket.getCreation().getDate().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doDeleteTicketById() {
//        for deleting ticket-orphans (bug)
//        ticketEJB.deleteTicket(ticketEJB.findTicketById(37L));
//        FacesMessage msg = new FacesMessage("Удалено (заявка)", "id = 37");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do UPDATE

    public void doUpdateTicketInfo() {
        logger.info("=>=>=>=>=> TicketController.doUpdateTicketInfo()");
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());

        Ticket ticketForUpdates = ticketEJB.findTicketById(ticket.getId()); // difference

        ticket.getTicketInfo().setTicketStatus(ticketForUpdates.getTicketInfo().getTicketStatus()); // it needs for right comparing (equals below) in the case where TicketStatus could be changed in parallel

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);

        Boolean isTicketInfoUpdated = false;
        if (!ticket.getTicketInfo().equals(ticketForUpdates.getTicketInfo())) {
            TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
            prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, ticketForUpdates.getTicketInfo().getTicketStatus()); // last argument repeats operation above it because helper method was implemented that for operation change status
            setNewTicketInfo(newTicketInfo, ticketForUpdates);
            isTicketInfoUpdated = true;
        }

        ticketEJB.updateTicket(ticketForUpdates);

        FacesMessage msg = new FacesMessage(isTicketInfoUpdated? "Обновлено (заявка)" : "Информация не изменена", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do CHANGE STATUS

    public void doAcceptTicket() {
        logger.info("=>=>=>=>=> TicketController.doAcceptTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToAcceptTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.ACCEPTED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setAcceptance(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка принята", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doOnServiceTicket() {
        logger.info("=>=>=>=>=> TicketController.doOnServiceTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToOnServiceTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.ON_SERVICE);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setService(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Выезд назначен", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doCloseTicket() {
        logger.info("=>=>=>=>=> TicketController.doCloseTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToCloseTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.CLOSED);
        newTicketInfo.setTicketResults(selectedTicketResults); // difference
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setClosing(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "ЗАКРЫТИЕ: " + commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка закрыта", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doArchiveTicket() {
        logger.info("=>=>=>=>=> TicketController.doArchiveTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToArchiveTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.ARCHIVED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setArchiving(lifeCycleInfo); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка помещена в архив", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doMarkAsIncorrectTicket() {
        logger.info("=>=>=>=>=> TicketController.doMarkAsIncorrectTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToMarkAsIncorrectTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.INCORRECT);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setIncorrectness(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "НЕВАЛИДНАЯ: " + commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка отмечена как невалидная", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doCancelTicket() {
        logger.info("=>=>=>=>=> TicketController.doCancelTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToCancelTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.CANCELED);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.setCancellation(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "ОТМЕНА: " + commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка отменена", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doRepeatedOnServiceTicket() {
        logger.info("=>=>=>=>=> TicketController.doRepeatedOnServiceTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToRepeatedOnServiceTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.REPEATED_ON_SERVICE);
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.getRepeatedService().add(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "ПОВТОРНЫЙ ВЫЕЗД: " + commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Повторный выезд назначен", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void doRepeatedCloseTicket() {
        logger.info("=>=>=>=>=> TicketController.doRepeatedCloseTicket()");

        // TODO (never used) it working automatically by container and i don't now how (but in this case message is not showing)...
        if (!doCheckForPossibilityToRepeatedCloseTicket(ticket)) {
            FacesMessage msg = new FacesMessage("Действие невозможно", unitOfTransport.getTransportInfo().getStateNumber());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            return;
        }

        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        //TODO update ticket from the database is going on above in if statement, but may be repeat it for logicality
        //ticket = ticketEJB.findTicketById(ticket.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);
        TicketInfo newTicketInfo = new TicketInfo(ticket.getTicketInfo());
        prepareNewTicketInfo(newTicketInfo, lifeCycleInfo, TicketStatus.REPEATED_CLOSED);
        newTicketInfo.setTicketResults(selectedTicketResults); // difference
        setNewTicketInfo(newTicketInfo, ticket);

        ticket.getRepeatedClosing().add(lifeCycleInfo); // difference
        ticket.getComments().add(new Comment(lifeCycleInfo, new CommentInfo(lifeCycleInfo, "ПОВТОРНОЕ ЗАКРЫТИЕ: " + commentContent))); // difference

        ticketEJB.updateTicket(ticket);

        duplicateTicketStatusForFiltering(ticket);

        FacesMessage msg = new FacesMessage("Заявка закрыта повторно", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do CHECK

    public Boolean doCheckForPossibilityToAddTicket(Transport transport) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToAddTicket()");
        transport = transportEJB.findTransportById(transport.getId());
        unitOfTransport = transport; // for update value from DB when this method is called in doAddTicket()
        List<Ticket> tickets = transport.getTickets();
        if (tickets.isEmpty()) return true;
        Ticket lastTicket = tickets.get(tickets.size() - 1);
        return lastTicket.getArchiving() != null || lastTicket.getIncorrectness() != null || lastTicket.getCancellation() != null;
    }

    public Boolean doCheckForPossibilityToAcceptTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToAcceptTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getAcceptance() == null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToOnServiceTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToOnServiceTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getService() == null && ticket.getClosing() == null && ticket.getAcceptance() != null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToCloseTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToCloseTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getClosing() == null && ticket.getAcceptance() != null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToArchiveTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToArchiveTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getArchiving() == null && ticket.getClosing() != null && ticket.getAcceptance() != null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToCancelTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToCancelTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getArchiving() == null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToMarkAsIncorrectTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToMarkAsIncorrectTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getArchiving() == null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToRepeatedOnServiceTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToRepeatedOnServiceTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getService() != null && ticket.getClosing() != null && ticket.getRepeatedService().size() == ticket.getRepeatedClosing().size() && ticket.getArchiving() == null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    public Boolean doCheckForPossibilityToRepeatedCloseTicket(Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.doCheckForPossibilityToRepeatedCloseTicket()");
        ticket = ticketEJB.findTicketById(ticket.getId());
        this.ticket = ticket; // for update value from DB when this method is called in 'Do CHANGE STATUS' methods
        return ticket.getService() != null && ticket.getClosing() != null && ticket.getRepeatedService().size() != ticket.getRepeatedClosing().size() && ticket.getArchiving() == null && ticket.getIncorrectness() == null && ticket.getCancellation() == null;
    }

    //HELPER METHODS

    private void prepareNewTicketInfo(TicketInfo newTicketInfo, LifeCycleInfo lifeCycleInfo, TicketStatus ticketStatus) {
        logger.info("=>=>=>=>=> TicketController.prepareNewTicketInfo()");
        newTicketInfo.setModification(lifeCycleInfo);
        newTicketInfo.setTransportInfo(new TransportInfo(unitOfTransport.getTransportInfo()));
        newTicketInfo.setTerminalInfo(new TerminalInfo(unitOfTransport.getTerminal().getTerminalInfo()));
        newTicketInfo.setPointInfo(new PointInfo(unitOfTransport.getPoint().getPointInfo()));
        newTicketInfo.setTicketStatus(ticketStatus);
    }

    private void setNewTicketInfo(TicketInfo newTicketInfo, Ticket ticket) {
        logger.info("=>=>=>=>=> TicketController.setNewTicketInfo()");
        TicketInfo oldTicketInfo = ticket.getTicketInfo(); // = new TicketInfo(ticket.getTicketInfo());
        ticket.getTicketInfoHistory().add(oldTicketInfo);
        ticket.setTicketInfo(newTicketInfo);
    }

    private void duplicateTicketStatusForFiltering(Ticket ticket) {
        unitOfTransport = transportEJB.findTransportById(unitOfTransport.getId());
        unitOfTransport.setCurrentTicketStatus(ticket.getTicketInfo().getTicketStatus().toString());
        transportEJB.updateTransport(unitOfTransport);
    }

    private void setCurrentTicketHeaderAndTicketStatus(Ticket ticket) {
        if (ticket == null) {
            unitOfTransport.setCurrentTicketStatus("нет");
            unitOfTransport.setCurrentTicketHeader("нет");
        } else {
            unitOfTransport.setCurrentTicketStatus(ticket.getTicketInfo().getTicketStatus().toString());
            unitOfTransport.setCurrentTicketHeader(ticket.getTicketInfo().getTicketHeader().toString());
        }
    }

    //GETTERS AND SETTERS

    public Transport getUnitOfTransport() {
        return unitOfTransport;
    }

    public void setUnitOfTransport(Transport unitOfTransport) {
        this.unitOfTransport = unitOfTransport;
    }

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public List<TicketHeader> getTicketHeaders() {
        return ticketHeaders;
    }

    public void setTicketHeaders(List<TicketHeader> ticketHeaders) {
        this.ticketHeaders = ticketHeaders;
    }

    public List<TicketHeader> getAllTicketHeaders() {
        return allTicketHeaders;
    }

    public void setAllTicketHeaders(List<TicketHeader> allTicketHeaders) {
        this.allTicketHeaders = allTicketHeaders;
    }

    public List<TicketStatus> getTicketStatuses() {
        return ticketStatuses;
    }

    public void setTicketStatuses(List<TicketStatus> ticketStatuses) {
        this.ticketStatuses = ticketStatuses;
    }

    public List<TicketResult> getTicketResults() {
        return ticketResults;
    }

    public void setTicketResults(List<TicketResult> ticketResults) {
        this.ticketResults = ticketResults;
    }

    public List<TicketResult> getSelectedTicketResults() {
        return selectedTicketResults;
    }

    public void setSelectedTicketResults(List<TicketResult> selectedTicketResults) {
        this.selectedTicketResults = selectedTicketResults;
    }
}

    //TRASH

//    public void doOpenTicketDialog(Transport transport, String didBy) {
//        this.transport = transport;
//        this.didBy = didBy;
//
//        Map<String,Object> options = new HashMap<>();
//        options.put("modal", true);
//        options.put("draggable", true);
//        options.put("resizable", true);
//
//        RequestContext.getCurrentInstance().openDialog("addTicket", options, null);
//
////        FacesMessage msg = new FacesMessage(didBy, transport.getId().toString());
////        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }

//    public void doReset() {
//        transport = null;
//        didBy = null;
//        ticket = new Ticket();
//        commentContent = null;
//    }