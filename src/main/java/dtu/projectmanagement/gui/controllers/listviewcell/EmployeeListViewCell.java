package dtu.projectmanagement.gui.controllers.listviewcell;

import dtu.projectmanagement.domain.Employee;
import dtu.projectmanagement.gui.ManagementAppGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class EmployeeListViewCell extends ListCell<Employee> {

    //@FXML private FontAwesomeIconView
    @FXML private Label lblCellUsername;
    @FXML private VBox cell;

    public EmployeeListViewCell() {
        super();
        setCursor(Cursor.HAND);
    }

    @Override
    protected void updateItem(Employee emp, boolean empty) {
        super.updateItem(emp, empty);

        if (empty || emp == null) {
            setText(null);
            setGraphic(null);
        } else {
            FXMLLoader fxmlLoader = new FXMLLoader(ManagementAppGUI.class.getResource("components/empListCell-comp.fxml"));
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            lblCellUsername.setText(emp.getUsername());
            setCursor(Cursor.HAND);

            setText(null);
            setGraphic(cell);
        }
    }
}