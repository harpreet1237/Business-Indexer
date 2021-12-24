/**
 * Sample Skeleton for 'LoginnVieww.fxml' Controller Class
 */
//Login Updated
package login;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
//Login Updated 
public class LoginnViewwController {
	
	String username="harpreet1237",pwd="1234";
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtUsername"
    private TextField txtUsername; // Value injected by FXMLLoader

    @FXML
    private PasswordField pass1;
    
   

   
    @FXML
    void doDone(ActionEvent event)
    {
    	 String str=txtUsername.getText();
    		String str1=pass1.getText();
    		System.out.println(str+ "  "+str1);
    	
    	if(str.matches(username) && str1.matches(pwd))
    	{
    	try { 
    		Parent root=FXMLLoader.load(getClass().getClassLoader().getResource("dashboard/DashBoarddViewww.fxml")); 
    		Scene scene = new Scene(root);
    		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		Stage stage=new Stage();
    	    stage.setScene(scene);
    	    stage.show();
           }    
           catch(Exception e) 
           {
    	     e.printStackTrace();
            }
    	}else {
    		
    		showMsg("Invalid UserName or Password.");
    	}
            }
    
    void showMsg(String msg)
    {
    	Alert alert=new Alert(AlertType.ERROR);
    	alert.setTitle("Log In");
    	alert.setContentText(msg);
    	alert.show();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        
    }
}