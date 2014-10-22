package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystemException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {

	public static String currentFilePath = "";

	public static File saveGraph() throws IOException {
		File returnFile = null;
		
		if (currentFilePath.isEmpty()) { 			// Wurde bisher kein Pfad und Dateiname zum Speichern gewählt,
			return saveGraphAs();					// wird der Speicherndialog aufgerufen.
		} else {
			returnFile = new File(currentFilePath); // andernfalls nimm den hinterlegten Pfad der aktuellen Datei.
			returnFile.createNewFile();
		}
		return returnFile;
	}

	public static File saveGraphAs() throws IOException {
		String filePath = null;
		File returnFile = null;
		JFileChooser chooser = new JFileChooser();
		
		chooser.setFileFilter(new FileNameExtensionFilter(
				"Graphendateien (*.gka)", "gka"));
		
		int ret = chooser.showSaveDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getAbsolutePath();
			returnFile = new File(filePath);
			if (returnFile.createNewFile()) {	// Wenn Datei erfolgreich erstellt werden kann,
				currentFilePath = filePath;		// absoluten Pfad speichern.
			}
		}
		return returnFile;
	}
	
	public static File openGraph() throws FileSystemException {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Graphendateien (*.gka)", "gka");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File graphFile = new File(chooser.getSelectedFile().getAbsolutePath());
			checkFile(graphFile);
			currentFilePath = graphFile.getAbsolutePath();
			return graphFile;
		} else {
			return null;
		}
	}
	
	public static File checkFile(File file) throws FileSystemException {
		if (!(file.exists() && file.isFile() && file.canRead())) {
			throw new FileSystemException("Dateizugriffsfehler");
		}
		return file;
	}
}