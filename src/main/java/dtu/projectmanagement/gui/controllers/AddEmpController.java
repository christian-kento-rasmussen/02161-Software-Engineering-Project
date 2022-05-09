package dtu.projectmanagement.gui.controllers;

import dtu.projectmanagement.app.ManagementApp;
import dtu.projectmanagement.app.OperationNotAllowedException;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author William Steffens (s185369)
 */
public class AddEmpController {

    ManagementApp managementApp;

    @FXML
    private TextField tfUsername;
    @FXML private Label lblError;
    @FXML private Button btnCancel;

    @FXML
    public void initialize() {
        managementApp = ManagementApp.getInstance();
    }



    @FXML
    public void onBtnCancel() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onBtnOK() {
        lblError.setTextFill(Color.RED);

        try {
            managementApp.addEmployee(tfUsername.getText());
        } catch (OperationNotAllowedException e) {
            lblError.setText(e.getMessage());
            return;
        }
        lblError.setTextFill(Color.GREENYELLOW);
        lblError.setText(tfUsername.getText().toLowerCase() + " successfully added");

        tfUsername.setText("");
        tfUsername.requestFocus();
    }
}
