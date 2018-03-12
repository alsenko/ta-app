import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Selection
        extends Application {
	private String classListFile;
	private String taListFile;
	private Text actionStatus;
	private Text actionStatus2;
	private Text actionStatus3;
	private Text actionStatus4;	
	private Stage savedStage;
	private ArrayList<Class> classList;
	private ArrayList<TA> taList;
	private ArrayList<Class> schedule;
	private static final String titleTxt = "UMD TA Schedule Generator";
	private static final String defaultFileName = "TaSchedule.xlsx";

	public static void main(String [] args) {

		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
	
		primaryStage.setTitle(titleTxt);	

		// Window label
		Label label = new Label("Select Files");
		label.setTextFill(Color.DARKBLUE);
		label.setFont(Font.font("Calibri", FontWeight.BOLD, 14));
		HBox labelHb = new HBox();
		labelHb.setAlignment(Pos.TOP_CENTER);
		labelHb.getChildren().add(label);

		// Buttons
		Button btn1 = new Button("Select the list of TAs");
		btn1.setOnAction(new TaListUpload());
		HBox buttonHb1 = new HBox(10);
		buttonHb1.setAlignment(Pos.TOP_CENTER);
		buttonHb1.getChildren().addAll(btn1);

		Button btn2 = new Button("Select the list of classes");
		btn2.setOnAction(new ClassListUpload());
		HBox buttonHb2 = new HBox(10);
		buttonHb2.setAlignment(Pos.CENTER);
		buttonHb2.getChildren().addAll(btn2);

		
		Button btn3 = new Button("Run");
		btn3.setOnAction(new CreateSchedule());
		HBox buttonHb3 = new HBox(10);
		buttonHb3.setAlignment(Pos.BOTTOM_CENTER);
		buttonHb3.getChildren().addAll(btn3);	
		
		Button btn4 = new Button("Save Results");
		btn4.setOnAction(new SaveButtonListener());
		HBox buttonHb4 = new HBox(10);
		buttonHb4.setAlignment(Pos.BOTTOM_CENTER);
		buttonHb4.getChildren().addAll(btn4);		
		// Status message text
		actionStatus = new Text();
		actionStatus.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
		actionStatus.setFill(Color.FIREBRICK);

		actionStatus2 = new Text();
		actionStatus2.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
		actionStatus2.setFill(Color.FIREBRICK);
		
		actionStatus3 = new Text();
		actionStatus3.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
		actionStatus3.setFill(Color.FIREBRICK);		

		actionStatus4 = new Text();
		actionStatus4.setFont(Font.font("Calibri", FontWeight.NORMAL, 14));
		actionStatus4.setFill(Color.FIREBRICK);				
		// Vbox
		VBox vbox = new VBox(30);
		vbox.setPadding(new Insets(25, 25, 25, 25));;
		vbox.getChildren().addAll(labelHb, buttonHb1,actionStatus, buttonHb2,actionStatus2,buttonHb3,actionStatus3,buttonHb4,actionStatus4);

		
		// Scene
		Scene scene = new Scene(vbox, 500, 500); // w x h
		primaryStage.setScene(scene);
		primaryStage.show();

		savedStage = primaryStage;
		
	}

	private class TaListUpload implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showTaList();
		}
	}
	
	private class ClassListUpload implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showClassList();
		}
	}	
	
	private class CreateSchedule implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			createSchedule();
		}
	}
	private void showTaList() {
	
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {

			actionStatus.setText("File selected for TA List : " + selectedFile.getName());
			taListFile = selectedFile.getAbsolutePath();
			
		}
		else {
			actionStatus.setText("File selection cancelled.");
		}
	}
	private void showClassList() {
		
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			
			actionStatus2.setText("File selected for Class List: " + selectedFile.getName());
			classListFile = selectedFile.getAbsolutePath(); 
		}
		else {
			actionStatus2.setText("File selection cancelled.");
		}
	}
	private void createSchedule() {
		
		if (classListFile != null && taListFile != null) {
			if (taList != null)
			taList.clear();
			if (classList != null)
			classList.clear();
			taList = ExcelReader.createTAList(taListFile);
			classList = ExcelReader.createClassList(classListFile); 
			HashMap<Integer,HashMap<Integer,ArrayList<TA>>> testMap = ScheduleMaker.createMap(taList);
			schedule = ScheduleMaker.createSchedule(testMap,classList);
			//show button to save
			actionStatus3.setText("Success");
		}
		else {
			//one of the files is invalid
			actionStatus3.setText("One of the files was invalid");
		}
	}		
	private class SaveButtonListener implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			showSaveFileChooser();
		}
	}

	private void showSaveFileChooser() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		fileChooser.setInitialFileName(defaultFileName);
		File savedFile = fileChooser.showSaveDialog(savedStage);

		if (savedFile != null) {

			try {
				saveFileRoutine(savedFile);
			}
			catch(IOException e) {
			
				e.printStackTrace();
				actionStatus4.setText("An ERROR occurred while saving the file!" +
						savedFile.toString());
				return;
			}
			
			actionStatus4.setText("File saved: " + savedFile.toString());
		}
		else {
			actionStatus4.setText("File save cancelled.");
		}
	}
	private void saveFileRoutine(File file)
			throws IOException{
		// Creates a new file and writes the txtArea contents into it
		ExcelWriter.printSchedule(schedule,taList, file.getAbsolutePath());
	}
	// TestMain.printCurrentList(schedule);
	//  ExcelWriter.printSchedule(schedule);
	
}