package client;

import java.io.IOException;
import controller.*;
import java.util.ArrayList;


import ocsf.client.AbstractClient;
import logic.Message;
public class Client extends AbstractClient
{
	  //Instance variables **********************************************
	  
	  /**
	   * The interface type variable.  It allows the implementation of 
	   * the display method in the client.
	   */
	  ClientController clientUI; 

	  
	  //Constructors ****************************************************
	  
	  /**
	   * Constructs an instance of the chat client.
	   *
	   * @param host The server to connect to.
	   * @param port The port number to connect on.
	   * @param clientUI The interface type variable.
	   */
	  
	  static public ReportController reportController;
	  static public MainScreenController mainScreenController;
	  static public BookingController bookingController;
	  
	  public Client(String host, int port, ClientController clientUI) 
	    throws IOException 
	  {
	    super(host, port); //Call the superclass constructor
	    this.clientUI = clientUI;
	    System.out.println("Connecting...");
	    openConnection();
	    
	    //Initilazing The Contorllers
	    
	    bookingController = new BookingController();
	    mainScreenController = new MainScreenController();
	    reportController = new ReportController();
	    //to be continued if needed
	  }

	  
	  //Instance methods ************************************************
	    
	  /**
	   * This method handles all data that comes in from the server.
	   *
	   * @param msg The message from the server.
	   */
	  public void handleMessageFromServer(Object msg) 
	  {
		  //get the message a message object from the server (getcmd,getobj) while obj is the data from the server  
	      Message m = (Message)msg;

	      switch(m.getCmd()) {
	      
	      	case WorkerLoginResult:
	      		Boolean workerLoginResult =  (Boolean)m.getObj();
	      		mainScreenController.setWorkerLoginValid(workerLoginResult);
	      		
		default:
			break;
	      		
	      
	      }
		  
		  
			/*	
			 * 
			 * 
			 * 		 
			 * 
				//TODO: Add Switch case for all msgs from server.
			 */
	  }

	  
	  /**
	   * This method handles all data coming from the UI            
	   *
	   * @param message The message from the UI.    
	   */
	  public void handleMessageFromClientUI(String[] message)  
	  {

	    try
	    {
	    	System.out.println("sendtoserver");
	    	sendToServer(message);
	    	
	    }
	    catch(IOException e)
	    {
	      clientUI.display
	        ("Could not send message to server.  Terminating client.");
	      quit();
	    }
	  }

	  
	  /**
	   * This method terminates the client.
	   */
	  public void quit()
	  {
	    try
	    {
	      closeConnection();
	    }
	    catch(IOException e) {}
	    System.exit(0);
	  }

}
