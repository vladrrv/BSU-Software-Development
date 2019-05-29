package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class RegistrarWindowController extends WindowController {

    @FXML private Button buttonSwitchRegistration;
    @FXML private Button buttonPayment;
    @FXML private Button buttonEditProfessor;
    @FXML private Button buttonDeleteProfessor;
    @FXML private Button buttonEditStudent;
    @FXML private Button buttonDeleteStudent;
    @FXML private Button buttonEditCourse;
    @FXML private Button buttonDeleteCourse;
    @FXML private TextArea taRegInfo;
    @FXML private ListView<Student> listViewStudents;
    @FXML private ListView<Professor> listViewProfessors;
    @FXML private ListView<Course> lvCourses;

    private final int MIN_STUDS_PER_COURSE = 2;
    private final int MIN_COURSES_PER_STUD = 1;

    private void log(String s) {
        String old = taRegInfo.getText();
        taRegInfo.setText(old + s + '\n');
    }

    private void switchButtonLabel() {
        boolean isOpen = DatabaseManager.isRegistrationOpen();
        if (isOpen) {
            buttonSwitchRegistration.setText("Close");
            buttonPayment.setDisable(true);
        } else {
            buttonSwitchRegistration.setText("Open");
            buttonPayment.setDisable(false);
        }
    }

    private void updateUserLists() {
        ObservableList<Student> studentList;
        ObservableList<Professor> professorList;
        professorList = DatabaseManager.getProfessors();
        listViewProfessors.setItems(professorList);
        studentList = DatabaseManager.getStudents();
        listViewStudents.setItems(studentList);
    }

    private void updateCourseList() {
        lvCourses.setItems(DatabaseManager.getCourses());
    }

    @Override
    void init() {
        super.init();
        switchButtonLabel();
        updateUserLists();
        updateCourseList();
        listViewStudents.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buttonEditStudent.setDisable(newValue == null);
            buttonDeleteStudent.setDisable(newValue == null);
        });
        listViewProfessors.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buttonEditProfessor.setDisable(newValue == null);
            buttonDeleteProfessor.setDisable(newValue == null);
        });
        lvCourses.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            buttonEditCourse.setDisable(newValue == null);
            buttonDeleteCourse.setDisable(newValue == null);
        });
    }

    @FXML private void onSwitchRegistration() {
        boolean isClosed = DatabaseManager.switchRegistration();
        log(String.format("Registration %s at %s", isClosed? "closed": "opened", new Date()));
        switchButtonLabel();

        if (isClosed) {
            for (var offeringId : DatabaseManager.getUnfixedOfferingIds()) {
                long nStudents = DatabaseManager.getNStudentsForCourseOffering(offeringId);
                if (nStudents >= MIN_STUDS_PER_COURSE) {
                    DatabaseManager.fixCourseOffering(offeringId);
                }
            }
            for (var studentId : DatabaseManager.getStudentIds()) {
                long nCourses = DatabaseManager.getNFixedCoursesForStudent(studentId);
                long delta = MIN_COURSES_PER_STUD - nCourses;
                if (delta > 0) {
                    for (var offeringId : DatabaseManager.getStudentAltOfferingIds(studentId)) {
                        DatabaseManager.makePrimary(offeringId);
                    }
                }
            }
            for (var offeringId : DatabaseManager.getUnfixedOfferingIds()) {
                long nStudents = DatabaseManager.getNStudentsForCourseOffering(offeringId);
                if (nStudents >= MIN_STUDS_PER_COURSE) {
                    DatabaseManager.fixCourseOffering(offeringId);
                }
            }
            DatabaseManager.removeUnfixedSCO();
        } else {
            DatabaseManager.clearAll();
        }
    }

    @FXML private void onSend() {
        String bill = DatabaseManager.getPaymentString();
        try {
            PrintWriter out = new PrintWriter("bill.csv");
            out.println(bill);
            out.close();
            log(String.format("Bill sent at %s", new Date()));
        } catch (IOException ex) {
            showError("Could not write bill file");
        }
    }

    @FXML private void onAddProfessor() {
        AddUserController c =
                (AddUserController) nextStage("forms/AddProfessorForm.fxml", "Add Professor");
        c.init(getStage());
        if (c.isOK()) {
            DatabaseManager.addProfessor(c.getEmail(), c.getPassword(), c.getName(),
                    c.getInfo1(), c.getInfo2(), c.getPhotoURL());
            updateUserLists();
        }
    }

    @FXML private void onEditProfessor() {
        User professor = listViewProfessors.getSelectionModel().getSelectedItem();
        AddUserController c =
                (AddUserController) nextStage("forms/AddProfessorForm.fxml", "Edit Professor");
        c.init(getStage(), professor);
        if (c.isOK()) {
            DatabaseManager.editProfessor(professor.getLoginId(), c.getEmail(), c.getPassword(), c.getName(),
                    c.getInfo1(), c.getInfo2(), c.getPhotoURL());
            updateUserLists();
        }
    }

    @FXML private void onDeleteProfessor() {
        User professor = listViewProfessors.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(String.format("Are you sure to delete professor %s?", professor));
        alert.setContentText("");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                DatabaseManager.deleteUser(professor.getLoginId());
                updateUserLists();
            }
        });
    }

    @FXML private void onAddStudent() {
        AddUserController c =
                (AddUserController) nextStage("forms/AddStudentForm.fxml", "Add Student");
        c.init(getStage());
        if (c.isOK()) {
            DatabaseManager.addStudent(c.getEmail(), c.getPassword(), c.getName(),
                    Integer.parseInt(c.getInfo1()), c.getInfo2(), c.getPhotoURL());
            updateUserLists();
        }
    }

    @FXML private void onEditStudent() {
        User student = listViewStudents.getSelectionModel().getSelectedItem();
        AddUserController c =
                (AddUserController) nextStage("forms/AddStudentForm.fxml", "Edit Student");
        c.init(getStage(), student);
        if (c.isOK()) {
            DatabaseManager.editStudent(student.getLoginId(), c.getEmail(), c.getPassword(), c.getName(),
                    Integer.parseInt(c.getInfo1()), c.getInfo2(), c.getPhotoURL());
            updateUserLists();
        }
    }

    @FXML private void onDeleteStudent() {
        User student = listViewStudents.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(String.format("Are you sure to delete student %s?", student));
        alert.setContentText("");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                DatabaseManager.deleteUser(student.getLoginId());
                updateUserLists();
            }
        });
    }

    @FXML private void onAddCourse() {
        AddCourseController c =
                (AddCourseController) nextStage("forms/AddCourseForm.fxml", "Add Course");
        c.init(getStage());
        if (c.isOK()) {
            DatabaseManager.addCourse(c.getDescription(), c.getPrice());
            updateCourseList();
        }
    }

    @FXML private void onEditCourse() {
        Course course = lvCourses.getSelectionModel().getSelectedItem();
        AddCourseController c =
                (AddCourseController) nextStage("forms/AddCourseForm.fxml", "Edit Course");
        c.init(getStage(), course);
        if (c.isOK()) {
            DatabaseManager.editCourse(course.getCourseId(), c.getDescription(), c.getPrice());
            updateCourseList();
        }
    }

    @FXML private void onDeleteCourse() {
        Course course = lvCourses.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText(String.format("Are you sure to delete course '%s'?", course.getDescription()));
        alert.setContentText("");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                DatabaseManager.deleteCourse(course.getCourseId());
                updateCourseList();
            }
        });
    }
}

