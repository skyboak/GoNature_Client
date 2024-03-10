package client;

import java.io.IOException;



public class ClientController 
{

	  
	  //Instance variables **********************************************
	   
	  //The instance of the client that created this ConsoleChat.
	  Client client;
	  



	/**
	   * Constructs an instance of the ClientConsole UI.
	   *
	   * @param host The host to connect to.
	   * @param port The port to connect on.
	   */
	  
	  public ClientController(String host, int port) 
	  {
	    try 
	    {
	      client = new Client(host, port, this);
	      client.sendToServer("Tried Connecting...");
	    } 
	    catch(IOException exception) 
	    {
	      System.out.println("Error: Can't setup connection!"+ " Terminating client.");
	      System.exit(1); 
	    }
	  }

	  
	  //Instance methods ************************************************

	  
		/*   EXMPLES:
		 * 
		 * public void show(String flag, String oNum) { String[] msg = new String[]
		 * {flag,oNum}; client.handleMessageFromClientUI(msg); }
		 * 
		 * 
		 * 
		 * public void update(String flag, String oNum, String newParkName, String
		 * newTelephone) { // 
		 * String[] {flag,oNum,newParkName,newTelephone};
		 * client.handleMessageFromClientUI(msg); }
		 */

		public void display(String message) 
		  {
		    System.out.println("> " + message);
		  }


}
