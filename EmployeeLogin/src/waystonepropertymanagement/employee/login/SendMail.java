/*
 * CSCI Capstone 2999 Final Project
 * Waystone Property Management Intranet
 */
package waystonepropertymanagement.employee.login;


import com.sendgrid.*;

import java.io.IOException;
//SendMail class sends an email through sendgrid
public class SendMail {

	public static void sendMail(String email, String pwd) {
			
			Email from = new Email("contact@waystone.com");
		    String subject = "Your Waystone Property Management Password";
		    Email to = new Email(email);
		    Content content = new Content("text/plain", "Dear Waystone Employee,"
					+ " \n\nYour password has been reset. Your temporary password is: " + pwd + ". Please log in with this temporary password and update the password to your choice.");
		    Mail mail = new Mail(from, subject, to, content);

		    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
		    
		    Request request = new Request();
		    try {
		      request.setMethod(Method.POST);
		      request.setEndpoint("mail/send");
		      request.setBody(mail.build());
		      Response response = sg.api(request);
		      System.out.println(response.getStatusCode());
		      System.out.println(response.getBody());
		      System.out.println(response.getHeaders());
		    } catch (IOException e) {
		      System.out.println("Login error -->" + e.getMessage());
		    }
	}
}
