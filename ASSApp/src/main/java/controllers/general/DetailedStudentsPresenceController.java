package controllers.general;

import dtos.StudentDto;
import entities.PresenceOnLecture;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import requests.CardRequest;
import requests.PresenceOnLectureRequest;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailedStudentsPresenceController implements Initializable {

    @FXML
    private TableView<StudentDto> studentsTable;

    @FXML
    private TableColumn<StudentDto, String> cardIdColumn;

    @FXML
    private TableColumn<StudentDto, Integer> studentIdColumn;

    @FXML
    private TableColumn<StudentDto, String> nameColumn;

    @FXML
    private TableColumn<StudentDto, String> surnameColumn;

    @FXML
    private TableColumn<StudentDto, String> facultyColumn;

    @FXML
    private TableColumn<StudentDto, String> deanGroupColumn;

    @FXML
    private TableColumn<StudentDto, String> emailColumn;

    @FXML
    private TableColumn<StudentDto, String> wasLateColumn;


    private static ObservableList<StudentDto> students;

    private PresenceOnLectureRequest presenceOnLectureRequest;
    private CardRequest cardRequest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeRequests();
        initializeStudentsPresenceColumns();
    }

    private void initializeRequests() {
        presenceOnLectureRequest = new PresenceOnLectureRequest();
        cardRequest = new CardRequest();
    }

    public void initializeStudentsPresenceColumns() {

        cardIdColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("cardId")
        );
        studentIdColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, Integer>("Id")
        );
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("firstName")
        );
        surnameColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("lastName")
        );
        facultyColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("faculty")
        );
        deanGroupColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("deanGroupName")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<StudentDto, String>("eMail")
        );
        wasLateColumn.setCellValueFactory(cellData -> {
            boolean indicator = cellData.getValue().getIsLate();
            String indicatorAsString;
            indicatorAsString = indicator == Boolean.TRUE ? "TAK" : "NIE";
//            if (indicator == true) {
//                indicatorAsString = "Male";
//            } else {
//                indicatorAsString = "Female";
//            }
            return new ReadOnlyStringWrapper(indicatorAsString);
        });
    }

    public void initData(String date, String hour, String room) {
        students = FXCollections.observableArrayList();
        List<PresenceOnLecture> presenceOnLecture =
                presenceOnLectureRequest.findAllByPresenceDateAndHourTimeAndRoom(date, hour, room);
        for (PresenceOnLecture p : presenceOnLecture) {
            StudentDto studentDto = new StudentDto(p.getStudent());
            studentDto.setIsLate(p.getWasLate());
            studentDto.setCardId(cardRequest.findCardByStudent_Id(studentDto.getId()).getId());
            students.add(studentDto);
        }
        studentsTable.setItems(students);
    }
}
