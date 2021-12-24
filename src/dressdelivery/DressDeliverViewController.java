package dressdelivery;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import customermeasure.DataBaseConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
//DressDeliver Updated

public class DressDeliverViewController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="comboOrderid"
	private ComboBox<String> comboOrderid; // Value injected by FXMLLoader

	@FXML // fx:id="txtDress"
	private TextField txtDress; // Value injected by FXMLLoader

	@FXML // fx:id="txtAmount"
	private TextField txtAmount; // Value injected by FXMLLoader

	PreparedStatement pst;

	void showMsg(String msg) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Delivery Information.");
		alert.setContentText(msg);
		alert.show();

	}
	
	
	@FXML
	void doDelivery(ActionEvent event) {
		try {
			String oid = comboOrderid.getEditor().getText();

			pst = con.prepareStatement("update orders set status=0 where oid=?");
			pst.setString(1, oid);
			int count = pst.executeUpdate();
			if (count == 0)
				showMsg("Invalid OrderID");
			else
				showMsg("Order ID:  " + oid + "   DELIVERED ");
			
			pst = con.prepareStatement("insert into dressdeliver value(?,?,?)");
			pst.setString(1, comboOrderid.getEditor().getText());
			pst.setString(2, txtDress.getText());
			pst.setString(3, txtAmount.getText());
			pst.executeUpdate();
			doFillCombo();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void doFetch(ActionEvent event) {
		try {
			pst = con.prepareStatement("select * from orders where status=1 AND oid=?");
			pst.setInt(1, Integer.parseInt(comboOrderid.getEditor().getText()));
			ResultSet table = pst.executeQuery();
			boolean j = false;
			while (table.next()) {
				j = true;
				String dresses = table.getString("dress");
				String amounttt = table.getString("amount");
				txtDress.setText(dresses);
				txtAmount.setText(amounttt);
			}
			if (j == false) {
				showMsg("Order ID Not Found.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void doFillCombo() {
			comboOrderid.getItems().clear();
			txtDress.setText("");
			txtAmount.setText("");
		try {
			pst = con.prepareStatement("select distinct oid  from orders where status=1");
			
			if(pst==null) {
				showMsg("No Active Orders for now.");
				return;
			}
			ResultSet table = pst.executeQuery();
			while (table.next()) {
				String oi = table.getString("oid");
				comboOrderid.getItems().add(oi);
				System.out.println(oi);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Connection con;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		con = DataBaseConnector.getConnection();
		doFillCombo();

	}
}