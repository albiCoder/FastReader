import java.util.Scanner;
import java.io.*;
import javafx.scene.text.Text;

public class ReadFile {
	private File file;
	private Scanner fileReading;
	private int delay = 100;
	private boolean continueReading = true;

	static Scanner input = new Scanner(System.in);

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
		loadFile();
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		if (delay > 0) {
			this.delay = delay;			
		}
	}

	public boolean isContinueReading() {
		return continueReading;
	}

	public void setContinueReading(boolean continueReading) {
		this.continueReading = continueReading;
	}

	public void loadFile() {
		try {
			Scanner fileReading = new Scanner(file);
			this.fileReading = fileReading;
		} catch (FileNotFoundException e) {
			System.out.println("File does not exists");
			e.printStackTrace();
		}

	}

	public String getWord() {
		try {
			if (fileReading.hasNext()) {
				Thread.sleep(delay);
				return fileReading.next();
			}

		} catch (Exception e) {
			System.out.println("fileReading: " + fileReading);
			System.out.println(e);
			e.printStackTrace();
			return "Problem while reading file";
		}
		return "-----EOF-----";
	}

	public void readToTheEnd(Text t) {
		while (fileReading.hasNext() && continueReading) {
			t.setText(getWord());
		}
	}
}
