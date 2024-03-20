package client;

import java.io.IOException;
import java.time.LocalDate;

import controller.*;
import java.util.ArrayList;
import java.util.Map;

import ocsf.client.AbstractClient;
import logic.BookingDetail;
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
	  
	  static public WorkerController workerController;
	  static public MainScreenController mainScreenController;
	  static public BookingController bookingController;
	  static public ReportController reportController;
	  
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
	    workerController = new WorkerController();
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
	      		break;
	      		
	      	case VisitorLoginResult:
	      		Boolean visitorLoginResult =  (Boolean)m.getObj();
	      		mainScreenController.setVisitorLoginValid(visitorLoginResult);
	      		break;
	      
	      	case VisitorMyBooking:
	      		ArrayList<BookingDetail> myBookingList = (ArrayList<BookingDetail>)m.getObj();
	      		bookingController.setMyBookingTableList(myBookingList);
	      		break;
	      		
	      	case CheckIfGroupGuide:
	      		Boolean groupguideLoginResult =  (Boolean)m.getObj();
	      		mainScreenController.setGroupGuideValid(groupguideLoginResult);
	      		break;
	      		
	      	case CancelBooking:
	      		Boolean cancelBookingResult =  (Boolean)m.getObj();
	      		bookingController.setCancelBooking(cancelBookingResult);
	      		break;

	      	case AverageParkStayTimeCheck:
	      		Boolean averageParkStayTimeResult =  (Boolean)m.getObj();
	      		workerController.setAverageParkStayTime(averageParkStayTimeResult);
	      		break;
	      		
	      	case OnlineBookingCapacityCheck:
	      		Boolean OnlineBookingCapacityResult =  (Boolean)m.getObj();
	      		workerController.setOnlineBookingCapacity(OnlineBookingCapacityResult);
	      		break;
	      		
	      	case ParkCapacityCheck:
	      		Boolean ParkCapacityResult =  (Boolean)m.getObj();
	      		workerController.setParkCapacity(ParkCapacityResult);
	      		break;
	      		
	      	case CheckParkCapacity:
	      		boolean bookingAvailable = (Boolean)m.getObj();
	      		bookingController.setCheckIfBookingAvailable(bookingAvailable);
	      		break;
	      	
	      	case checkIfExist:
	      		boolean checkexistig = (Boolean)m.getObj();
	      		bookingController.setCheckIfExistBooking(checkexistig);
	      		break;
	      		
	      	case visitorStatisticData:
	      		Map<LocalDate, int[]> visitorStatisticData = (Map<LocalDate, int[]>)m.getObj();
	      		reportController.setvisitorStatisticData(visitorStatisticData);
	      		break;
	      		
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
