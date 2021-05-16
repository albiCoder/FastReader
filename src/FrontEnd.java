import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class FrontEnd extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {

		// object of class ReadFile that deals with reading of text
		ReadFile fileToDisplay = new ReadFile();

		// words display
		Text text = new Text();
		text.setFont(Font.font("Abyssinica SIL", FontWeight.BOLD, FontPosture.REGULAR, 35));
		text.setText("FastReading");

		Text fileStatus = new Text("No File Loaded");
		fileStatus.setFill(Color.RED);// setting color of the text to blue
		fileStatus.setStroke(Color.RED); // setting the stroke for the text

		// File chooser
		Label label = new Label("File:");

		TextField tf = new TextField();
		tf.setOnAction(e -> {
			File f = new File(tf.getText());
			fileToDisplay.setFile(f);
			fileStatus.setText("File Loaded");
			fileStatus.setFill(Color.GREEN);
			fileStatus.setStroke(Color.GREEN);
		});

		Button btn = new Button("Browse");
		btn.setOnAction(e -> {
			FileChooser file = new FileChooser();
			file.setTitle("Open File");
			File f = file.showOpenDialog(primaryStage);
			fileToDisplay.setFile(f);
			tf.setText(f.getAbsolutePath());
			fileStatus.setText("File Loaded");
			fileStatus.setFill(Color.GREEN);
			fileStatus.setStroke(Color.GREEN);
		});

		Button startReadingBtn = new Button("Start");
		startReadingBtn.setOnAction(e -> {
			fileToDisplay.setContinueReading(true);
			new Thread(() -> {
				fileToDisplay.readToTheEnd(text);
			}).start();
		});
		Button pauseReadingBtn = new Button("Pause");
		pauseReadingBtn.setOnAction(e -> {
			fileToDisplay.setContinueReading(false);
		});

		Label delayValue = new Label("" + fileToDisplay.getDelay());
		Button increaseDelay = new Button("Reduce WPM");
		increaseDelay.setOnAction(e -> {
			fileToDisplay.setDelay(fileToDisplay.getDelay() + 5);
			delayValue.setText("" + fileToDisplay.getDelay());
		});
		Button decreaseDelay = new Button("Raise WPM");
		decreaseDelay.setOnAction(e -> {
			fileToDisplay.setDelay(fileToDisplay.getDelay() - 5);
			delayValue.setText("" + fileToDisplay.getDelay());
		});

		HBox leftToolbars = new HBox();
		leftToolbars.getChildren().addAll(label, tf, btn);
		leftToolbars.setSpacing(5);

		VBox rightToolbars = new VBox();
		rightToolbars.getChildren().addAll(delayValue, increaseDelay, decreaseDelay);
		rightToolbars.setSpacing(20);

		HBox controls = new HBox();
		controls.getChildren().addAll(startReadingBtn, pauseReadingBtn);
		controls.setSpacing(20);

		BorderPane root = new BorderPane();
		root.setTop(fileStatus);
		root.setLeft(leftToolbars);
		root.setRight(rightToolbars);
		root.setCenter(text);
		root.setBottom(controls);

		Scene scene = new Scene(root, 600, 200);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Fast reading!");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
