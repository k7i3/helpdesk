package k7i3.code.helpdesk.tnc;

/**
 * Created by k7i3 on 06.04.15.
 */
public enum UserRole {
    ADMIN("администратор"),
    USER("пользователь"),
    SERVICE("обслуживание");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
