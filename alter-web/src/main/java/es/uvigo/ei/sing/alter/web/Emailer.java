package es.uvigo.ei.sing.alter.web;

/*
 *  This file is part of ALTER.
 *
 *  ALTER is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  ALTER is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ALTER.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
* Simple use case for the javax.mail API.
*/
public final class Emailer {

  public static void main( String[] aArguments ){
    
    //the domains of these email addresses should be valid,
    //or the example will fail:
    Emailer.sendEmail("webmaster@blah.net",
                      "somebody@somewhere.com",
                      "Testing 1-2-3",
                      "blah blah blah");
   }
 /**
  * Send a single email.
  */
  public static void sendEmail( String aFromEmailAddr,
                         String aToEmailAddr,
                         String aSubject,
                         String aBody,
			 String attachmentPath, String attachmentName) {
			 
    //Here, no Authenticator argument is used (it is null).
    //Authenticators are used to prompt the user for user
    //name and password.
    Session session = Session.getDefaultInstance( fMailServerConfig, null );

 // Define message
    MimeMessage message = new MimeMessage(session);
    
    try {
      message.setFrom( new InternetAddress(aFromEmailAddr) );      
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(aToEmailAddr));
      message.setSubject( aSubject );

      // create the message part 
      MimeBodyPart messageBodyPart = new MimeBodyPart();

      //fill message
      messageBodyPart.setText(aBody);

      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);

      // Part two is attachment
      messageBodyPart = new MimeBodyPart();
      DataSource source = new FileDataSource(attachmentPath);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(attachmentName);
      multipart.addBodyPart(messageBodyPart);

      // Put parts in message
      message.setContent(multipart);

      // Send the message
      Transport.send( message );
    }
    catch (MessagingException ex){
      System.err.println("Cannot send email. " + ex);
      ex.printStackTrace();
    }
  }
  /**
  * Send a single email.
  */
  public static void sendEmail( String aFromEmailAddr,
                         String aToEmailAddr,
                         String aSubject,
                         String aBody ) {

    //Here, no Authenticator argument is used (it is null).
    //Authenticators are used to prompt the user for user
    //name and password.
    Session session = Session.getDefaultInstance( fMailServerConfig, null );

    
    MimeMessage message = new MimeMessage( session );

    try {
      //the "from" address may be set in code, or set in the
      //config file under "mail.from" ; here, the latter style is used
      message.setFrom( new InternetAddress(aFromEmailAddr) );      
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(aToEmailAddr));
      message.setSubject( aSubject );
      message.setText( aBody );

      Transport.send( message );
    }
    catch (MessagingException ex){
      System.err.println("Cannot send email. " + ex);
      ex.printStackTrace();
    }
  }

  /**
  * Allows the config to be refreshed at runtime, instead of
  * requiring a restart.
  */
  public static void refreshConfig() {
    fMailServerConfig.clear();
    fetchConfig();
  }

  // PRIVATE //

  private static Properties fMailServerConfig = new Properties();

  static {
    fetchConfig();
  }

  /**
  * Open a specific text file containing mail server
  * parameters, and populate a corresponding Properties object.
  */
  private static void fetchConfig() {
    InputStream input = null;
    try {
      //If possible, one should try to avoid hard-coding a path in this
      //manner; in a web application, one should place such a file in
      //WEB-INF, and access it using ServletContext.getResourceAsStream.
      //Another alternative is Class.getResourceAsStream.
      //This file contains the javax.mail config properties mentioned above.
      input = Emailer.class.getResourceAsStream("/MyMailServer.txt" );
      fMailServerConfig.load( input );
    }
    catch ( IOException ex ){
      System.err.println( "Cannot open and load mail server properties file." );
    }
    finally {
      try {
        if ( input != null ) input.close();
      }
      catch ( IOException ex ){
        System.err.println( "Cannot close mail server properties file." );
      }
    }
  }
} 

