package employeeroom;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

import customermeasure.DataBaseConnector;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
//EmployeeRoom Updated
public class EmployeeRoomViewController {

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="comboName"
	private ComboBox<String> comboName; // Value injected by FXMLLoader

	@FXML // fx:id="txtCn"
	private TextField txtCn; // Value injected by FXMLLoader

	@FXML // fx:id="txtAddress"
	private TextField txtAddress; // Value injected by FXMLLoader

	@FXML // fx:id="txtAdhar"
	private TextField txtAdhar; // Value injected by FXMLLoader

	@FXML // fx:id="comboExp"
	private ComboBox<String> comboExp; // Value injected by FXMLLoader

	@FXML // fx:id="listSps"
	private ListView<String> listSps; // Value injected by FXMLLoader

	@FXML // fx:id="txtReffered"
	private TextField txtReffered; // Value injected by FXMLLoader

	@FXML // fx:id="txtSD"
	private TextField txtSD; // Value injected by FXMLLoader
	PreparedStatement pst;

	@FXML
	void doRecruit(ActionEvent event) {
		try {
			pst = con.prepareStatement("insert into employees value(?,?,?,?,?,?,current_date(),?)");
			pst.setString(1, comboName.getEditor().getText());
			pst.setString(2, txtCn.getText());
			pst.setString(3, txtAddress.getText());
			pst.setString(4, txtAdhar.getText());
			pst.setString(5, comboExp.getEditor().getText());
			pst.setString(6, txtSD.getText());
			pst.setString(7, txtReffered.getText());
			
			if(comboName.getEditor().getText()=="" || txtAdhar.getText()=="") {
				showMsg("Please Fill required Fields.");
				return;
			}
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showMsg("Saved successfully.");
	}

	void showMsg(String msg) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Employee Info");
		alert.setContentText(msg);
		alert.show();
	}
	
	void doReset()
	{
		txtCn.setText("");
		txtAddress.setText("");
		txtAdhar.setText("");
		txtSD.setText("");
		txtReffered.setText("");
		comboExp.getEditor().setText("");
		doFill();
	}
	@FXML
	void doRefresh(ActionEvent event) {
		doReset();
	}

	@FXML
	void doRemove(ActionEvent event) {
		try {
			pst = con.prepareStatement("delete from employees where name=?");
			pst.setString(1, comboName.getEditor().getText());
			int count = pst.executeUpdate();
			if (count == 0) {
				System.out.println("Invalid Id.");
				return;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showMsg("Deleted successfully");
		doReset();
	}

	

	@FXML
	void doSearch(ActionEvent event) {
		try {
			pst = con.prepareStatement("select * from employees where name=?");
			if(comboName.getEditor().getText()=="") {
				showMsg("Please select employee from list.");
				return;
			}
			pst.setString(1, comboName.getEditor().getText());
			ResultSet table = pst.executeQuery();
			while (table.next()) {
				String contact = table.getString("contact");
				String address = table.getString("address");
				String adhar = table.getString("adhar");
				String exp = table.getString("exp");
				String dresses = table.getString("dresses");
				Date date = table.getDate(7);
				String ref = table.getString("ref");

				txtCn.setText(contact);
				txtAddress.setText(address);
				txtAdhar.setText(adhar);
				comboExp.getEditor().setText(exp);
				txtSD.setText(dresses);
//				txtSD.setAccessibleText(dresses);
				txtReffered.setText(ref);
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@FXML
	void doUpdate(ActionEvent event) {
		try {
			pst = con.prepareStatement("update employees set contact=?,address=?,adhar=?,exp=?,dresses=?,ref=? where name=?");
			pst.setString(1, txtCn.getText());
			pst.setString(2, txtAddress.getText());
			pst.setString(3, txtAdhar.getText());
			pst.setString(4, comboExp.getEditor().getText());
			pst.setString(5, txtSD.getText());
			pst.setString(6, txtReffered.getText());
			pst.setString(7, comboName.getEditor().getText());
			int count = pst.executeUpdate();
			if (count == 0)
				System.out.println("Invalid Id");
			else
				System.out.println(count + " Records Updated");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		showMsg("Updated successfully");
	}

	
	void doFill() {
		comboName.getItems().clear();
		try {
			pst = con.prepareStatement("select distinct name from employees");
			ResultSet table = pst.executeQuery();
			if(pst==null)
			{
				showMsg("No Employee Record Found.");
			}
			while (table.next()) {
				String cn = table.getString("name");
				comboName.getItems().add(String.valueOf(cn));
				System.out.println(cn);

			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}

	@FXML
	void doShowList(MouseEvent event) {
		ObservableList<String> all = listSps.getSelectionModel().getSelectedItems();
		String allItems = "";
		for (String str : all) {
			allItems += str + ",";
		}
		txtSD.setText(allItems.substring(0, allItems.length() - 1));
	}

	Connection con;

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		con = DataBaseConnector.getConnection();
		String str[] = {"Salwar suit", "sharara", "pajami suit", "kurta pajama", "coat pent", "plazo", "lehenge",
				"gharara", "skirts", "leggings", "lehenga choli", "patiala suit", "jackets"};
		listSps.getItems().addAll(str);
		String str1[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20" };
		comboExp.getItems().addAll(Arrays.asList(str1));
		listSps.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		doFill();
	}
}