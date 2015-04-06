package k7i3.code.helpdesk.tnc;

import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by k7i3 on 06.04.15.
 */
@Named
@RequestScoped
public class UserController implements Serializable{
    @Inject
    private UserEJB userEJB;
    private Logger logger = Logger.getLogger("k7i3");

    private User user = new User();
    private List<User> users;
    private List<User> checkboxSelectedUsers;
    private List<UserRole> roles = Arrays.asList(UserRole.values());

    @PostConstruct
    private void doFindAllUsers() {
        users = userEJB.findAllUsers();
    }

    public void doAddUser() {
        userEJB.createUser(user);

        FacesMessage msg = new FacesMessage("Сохранено (пользователь)", user.getLogin());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    //AJAX

    public void onRowEdit(RowEditEvent event) {
        User editedUser = (User) event.getObject();

//        Transport transportForUpdates = transportEJB.findTransportById(unitOfTransport.getId());

        userEJB.updateUser(editedUser);

        FacesMessage msg = new FacesMessage("р:" + editedUser.getRoles().size() + " п:" + editedUser.getProjects().size() + " ф:" + editedUser.getBranches().size(), editedUser.getPassword() + " - " + editedUser.getRoles().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Отмена", ((User) event.getObject()).getPassword());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getCheckboxSelectedUsers() {
        return checkboxSelectedUsers;
    }

    public void setCheckboxSelectedUsers(List<User> checkboxSelectedUsers) {
        this.checkboxSelectedUsers = checkboxSelectedUsers;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }
}
