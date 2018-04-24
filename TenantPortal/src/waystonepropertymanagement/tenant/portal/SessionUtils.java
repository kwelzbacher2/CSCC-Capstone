/**
 * Roxanne Woodruff
 * CSCI 2999 Capstone 
 * Waystone Property Management Tenant Portal
 * SessionUtils.java
 */
package waystonepropertymanagement.tenant.portal;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Session Utils is a Class that holds an open session with the database as long as the username (tenant email) is valid.
 * @author Roxanne
 *
 */
public class SessionUtils {
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        
    }
    
    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }
    
    public static String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }
    
    public static String getUserId() {
        HttpSession session = getSession();
        if(session != null)
            return (String) session.getAttribute("userid");
        else
            return null;
    }
}