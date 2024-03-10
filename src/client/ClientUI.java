package client;

import javafx.application.Application;
import javafx.stage.Stage;
import screenController.LoginMainScreen;

public class ClientUI extends Application { 
	


	public static void main( String args[] ) throws Exception
	   { 
		    launch(args);  //launch application.
	   } // end main
	 
	@Override
	public void start(Stage primaryStage) throws Exception {

		LoginMainScreen mainScreen = new LoginMainScreen(); // create MainScreen and start it.		 
		mainScreen.start(primaryStage); 
						 
		 
	}
	
	
	
	
		
}
