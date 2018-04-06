/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;

import java.io.IOException;
import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * AuthorizationFilter class creates a session for the user to log into their account
 * @author KWelzbacher
 * 
 */

public class AuthorizationFilter implements Filter {
    protected FilterConfig filterConfig;
    
    public AuthorizationFilter(){
        
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try{
            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);
            
            String reqURI = reqt.getRequestURI();
            if(reqURI.indexOf("/index.xhtml") >= 0 || (ses != null && ses.getAttribute("username") != null) || reqURI.indexOf("/public/") >=0
                    || reqURI.contains("javax.faces.resource"))
            {
            	if(!(reqURI.contains("javax.faces.resource"))){
            	resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
                resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
                resp.setDateHeader("Expires", 0);
            	}
                chain.doFilter(request,response);
            }
            else
            {
                resp.sendRedirect(reqt.getContextPath() + "/faces/index.xhtml");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
    }
    
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
}
