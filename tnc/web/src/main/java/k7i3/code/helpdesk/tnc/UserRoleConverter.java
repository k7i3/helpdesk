package k7i3.code.helpdesk.tnc;

import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * Created by k7i3 on 06.04.15.
 */
@FacesConverter(value="userRoleConverter")
public class UserRoleConverter extends EnumConverter{

    public UserRoleConverter() {
        super(UserRole.class);
    }
}
