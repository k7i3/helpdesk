package k7i3.code.helpdesk.tnc;

import com.opencsv.CSVReader;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 29.01.15.
 */
@Named
@RequestScoped
public class TransportController implements Serializable {
    @Inject
    private TransportEJB transportEJB;
    @Inject
    private UserEJB userEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private  List<Transport> accessibleTransport; // transport table

    private List<Transport> transport; // transport table - changed to accessibleTransport
    private List<String> projects;
    private List<String> branches;
    private List<String> transportModels;
    private List<String> terminalModels;
    private List<String> firmware;
    private List<String> routes;

//    private List<Transport> filteredTransport; // transport table - moved to SessionScopedController
    private List<Transport> checkboxSelectedTransport; // transport table - to move or not to move to SessionScopedController?

    private Transport unitOfTransport = new Transport();
    private String didBy;

    //Do FIND

    @PostConstruct
    public void doFindAllAccessibleTransport() {
        User user = userEJB.initUser();
        didBy = user.getLogin();
        accessibleTransport = transportEJB.findAllAccessibleTransport(user); // didBy ==>
        doFindAllProjects();
        doFindAllBranches();
        doFindAllTransportModels();
        doFindAllTerminalModels();
        doFindAllFirmware();
        doFindAllRoutes();

//        RequestContext requestContext = RequestContext.getCurrentInstance();
//        requestContext.execute("PF('transportTable').filter()");
    }

    //    @PostConstruct
    public void doFindAllTransport() {
        transport = transportEJB.findAllTransport();
//        transport = transportEJB.findAllAccessibleTransport(userEJB.initUser()); // didBy ==>
//        doFindAllProjects();
//        doFindAllBranches();
//        doFindAllTransportModels();
//        doFindAllTerminalModels();
//        doFindAllFirmware();
//        doFindAllRoutes();
    }

    public void doFindAllProjects() {
        projects = transportEJB.findAllProjects();
    }

    public void doFindAllBranches() {
        branches = transportEJB.findAllBranches();
    }

    public void doFindAllTransportModels() {
        transportModels = transportEJB.findAllTransportModels();
    }

    public void doFindAllTerminalModels() {
        terminalModels = transportEJB.findAllTerminalModels();
    }

    public void doFindAllFirmware() {
        firmware = transportEJB.findAllFirmware();
    }

    public void doFindAllRoutes() {
        routes = transportEJB.findAllRoutes();
    }

    //Do ADD

    public void doAddTransport() {
        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);

        initCreationAndModification(lifeCycleInfo);

        transportEJB.createTransport(unitOfTransport);

        FacesMessage msg = new FacesMessage("Сохранено (транспорт)", unitOfTransport.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //Do UPDATE

    public void doUpdateTransportInfo() { // is used when updating transportEquipment only (as long as)
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);

        Boolean isTransportInfoUpdated = false;
        if (!unitOfTransport.getTransportInfo().equals(transportForUpdates.getTransportInfo())) {
            TransportInfo newTransportInfo = new TransportInfo(unitOfTransport.getTransportInfo());
            prepareNewTransportInfo(newTransportInfo, lifeCycleInfo);
            setNewTransportInfo(newTransportInfo, transportForUpdates);
            isTransportInfoUpdated = true;
        }

        transportEJB.updateTransport(transportForUpdates);

        FacesMessage msg = new FacesMessage(isTransportInfoUpdated? "Обновлено (транспорт)" : "Информация не изменена", transportForUpdates.getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        unitOfTransport = (Transport) event.getObject();
        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        unitOfTransport.getTransportInfo().setTransportEquipment(transportForUpdates.getTransportInfo().getTransportEquipment()); // TODO this code fixes bug when nothing changed and all TransportEquipment reset to false (and logical right anyway)

//        didBy = "Администратор"; // TODO fix it!

        LifeCycleInfo lifeCycleInfo = new LifeCycleInfo(new Date(), didBy);

        Boolean isTransportInfoUpdated = false;
        if (!unitOfTransport.getTransportInfo().equals(transportForUpdates.getTransportInfo())) {
            TransportInfo newTransportInfo = new TransportInfo(unitOfTransport.getTransportInfo());
            prepareNewTransportInfo(newTransportInfo, lifeCycleInfo);
            setNewTransportInfo(newTransportInfo, transportForUpdates);
            isTransportInfoUpdated = true;
        }

        Boolean isTerminalInfoUpdated = false;
        if (!unitOfTransport.getTerminal().getTerminalInfo().equals(transportForUpdates.getTerminal().getTerminalInfo())) {
            TerminalInfo newTerminalInfo = new TerminalInfo(unitOfTransport.getTerminal().getTerminalInfo());
            prepareNewTerminalInfo(newTerminalInfo, lifeCycleInfo);
            setNewTerminalInfo(newTerminalInfo, transportForUpdates);
            isTerminalInfoUpdated = true;
        }

        transportEJB.updateTransport(transportForUpdates);

        FacesMessage msg = new FacesMessage("Обновлено (транспорт:" + (isTransportInfoUpdated? "да" : "нет") + " терминал: " + (isTerminalInfoUpdated? "да" : "нет") + ")", (transportForUpdates.getTransportInfo().getStateNumber()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Отмена", ((Transport) event.getObject()).getTransportInfo().getStateNumber());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        UploadedFile uploadedFile = event.getFile();
//        File directory = new File("D:\\алена\\HelpDesk\\log");
//        File directory = new File("/home/k7i3/test");
//        File file = File.createTempFile("importTransport", ".csv", directory);
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "uploadedFile.getFileName() -> " + uploadedFile.getFileName(), "file.getAbsolutePath -> " + file.getAbsolutePath() + " ||| " + "file.getCanonicalPath() -> " + file.getCanonicalPath()));


//        Path directory = Paths.get("/home/k7i3/test");
        Path directory = Paths.get("D:\\алена\\HelpDesk\\log");
        if (Files.notExists(directory)) Files.createDirectory(directory);
        Path file = Files.createTempFile(directory, "importTransport", ".csv");

        try (InputStream input = uploadedFile.getInputstream()) {
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, uploadedFile.getFileName() + " загружен", "директория на сервере: " + file));
        }


//        CSVReader reader = new CSVReader(new FileReader(file.toFile()), ',', '"', 1);
        CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file.toFile()), "UTF-8"), ',', '"', 1);
        List<String[]> list = reader.readAll();

//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "List<String[]> list = reader.readAll()", list.isEmpty()? "empty" : "notEmpty"));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "list.toString()", list.toString()));
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "строк в файле: ", list.size() + ""));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Arrays.toString(list.get(0))", Arrays.toString(list.get(0))));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Arrays.toString(list.get(list.size() - 1))", Arrays.toString(list.get(list.size() - 1))));

        list.forEach(this::addTransportFromArray);



//        list.stream().forEach(a -> a[0] = a[0] + "!!!");
//
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "List<String[]> list = reader.readAll()", list.isEmpty()? "empty" : "notEmpty"));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "list.toString()", list.toString()));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "list.size()", list.size() + ""));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Arrays.toString(list.get(0))", Arrays.toString(list.get(0))));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Arrays.toString(list.get(list.size() - 1))", Arrays.toString(list.get(list.size() - 1))));

        //        Stream<String> streamLines = Files.lines(file);
//        List <String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
//
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Files.readAllLines(file)", lines.isEmpty()? "empty" : "notEmpty"));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Files.readAllLines(file).toString()", lines.toString()));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Files.readAllLines(file).size()", lines.size() + ""));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Files.readAllLines(file).get(0)", lines.get(0)));
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Files.readAllLines(file).get(lines.size() - 1)", lines.get(lines.size() - 1)));

    }

    //HELPER METHODS

    private void prepareNewTransportInfo(TransportInfo newTransportInfo, LifeCycleInfo lifeCycleInfo) {
        newTransportInfo.setModification(lifeCycleInfo);
    }

    private void setNewTransportInfo(TransportInfo newTransportInfo, Transport transport) {
        TransportInfo oldTransportInfo = transport.getTransportInfo(); // = new TicketInfo(transport.getTransportInfo());
        transport.getTransportInfoHistory().add(oldTransportInfo);
        transport.setTransportInfo(newTransportInfo);
    }

    private void prepareNewTerminalInfo(TerminalInfo newTerminalInfo, LifeCycleInfo lifeCycleInfo) {
        newTerminalInfo.setModification(lifeCycleInfo);
    }

    private void setNewTerminalInfo(TerminalInfo newTerminalInfo, Transport transport) {
        TerminalInfo oldTerminalInfo = transport.getTerminal().getTerminalInfo(); // = new TicketInfo(transport.getTerminal().getTerminalInfo());
        transport.getTerminal().getTerminalInfoHistory().add(oldTerminalInfo);
        transport.getTerminal().setTerminalInfo(newTerminalInfo);
    }

    private void initCreationAndModification(LifeCycleInfo lifeCycleInfo) {
        unitOfTransport.setCreation(lifeCycleInfo);
        unitOfTransport.getTransportInfo().setModification(lifeCycleInfo);
        unitOfTransport.getTerminal().setCreation(lifeCycleInfo);
        unitOfTransport.getTerminal().getTerminalInfo().setModification(lifeCycleInfo);
        unitOfTransport.getPoint().getPointInfo().setModification(lifeCycleInfo);
    }

    private void addTransportFromArray(String[] strings) {
        unitOfTransport = new Transport();
        unitOfTransport.getTransportInfo().setProject(strings[0]);
        unitOfTransport.getTransportInfo().setBranch(strings[1]);
        unitOfTransport.getTransportInfo().setRoute(strings[2]);
        unitOfTransport.getTransportInfo().setStateNumber(strings[3]);
        unitOfTransport.getTransportInfo().setGarageNumber(strings[4]);
        unitOfTransport.getTransportInfo().setModel(strings[5]);
        unitOfTransport.getTransportInfo().getTransportEquipment().setHasPtt(Boolean.parseBoolean(strings[6]));
        unitOfTransport.getTransportInfo().getTransportEquipment().setHasSpeaker(Boolean.parseBoolean(strings[7]));
        unitOfTransport.getTransportInfo().getTransportEquipment().setHasInformer(Boolean.parseBoolean(strings[8]));
        unitOfTransport.getTransportInfo().getTransportEquipment().setHasDvr(Boolean.parseBoolean(strings[9]));
        unitOfTransport.getTerminal().getTerminalInfo().setNumber(Integer.parseInt(strings[10]));
        unitOfTransport.getTerminal().getTerminalInfo().setModel(strings[11]);
        unitOfTransport.getTerminal().getTerminalInfo().setFirmware(strings[12]);
        unitOfTransport.getTerminal().getTerminalInfo().setSerialNumber(strings[13]);
        unitOfTransport.getTerminal().getTerminalInfo().setMobileNumber(strings[14]);
//        unitOfTransport.getPoint().getPointInfo().setDate(Date.from(Instant.parse(strings[15].subSequence(0, strings[15].length()))));

        doAddTransport();
    }

    //GETTERS AND SETTERS

    public List<Transport> getAccessibleTransport() {
        return accessibleTransport;
    }

    public void setAccessibleTransport(List<Transport> accessibleTransport) {
        this.accessibleTransport = accessibleTransport;
    }

    public List<Transport> getTransport() {
        return transport;
    }

    public void setTransport(List<Transport> transport) {
        this.transport = transport;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public List<String> getBranches() {
        return branches;
    }

    public void setBranches(List<String> branches) {
        this.branches = branches;
    }

    public List<String> getTransportModels() {
        return transportModels;
    }

    public void setTransportModels(List<String> transportModels) {
        this.transportModels = transportModels;
    }

    public List<String> getTerminalModels() {
        return terminalModels;
    }

    public void setTerminalModels(List<String> terminalModels) {
        this.terminalModels = terminalModels;
    }

    public List<String> getFirmware() {
        return firmware;
    }

    public void setFirmware(List<String> firmware) {
        this.firmware = firmware;
    }

    public List<String> getRoutes() {
        return routes;
    }

    public void setRoutes(List<String> routes) {
        this.routes = routes;
    }

    public Transport getUnitOfTransport() {
        return unitOfTransport;
    }

    public void setUnitOfTransport(Transport unitOfTransport) {
        this.unitOfTransport = unitOfTransport;
    }

    public List<Transport> getCheckboxSelectedTransport() {
        return checkboxSelectedTransport;
    }

    public void setCheckboxSelectedTransport(List<Transport> checkboxSelectedTransport) {
        this.checkboxSelectedTransport = checkboxSelectedTransport;
    }

    public String getDidBy() {
        return didBy;
    }

    public void setDidBy(String didBy) {
        this.didBy = didBy;
    }
}

    //TRASH

//    public void onCellEdit(CellEditEvent event) {
//        Object oldValue = event.getOldValue();
//        Object newValue = event.getNewValue();
//
//        if(newValue != null && !newValue.equals(oldValue)) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//    }
