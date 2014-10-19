package controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class FileHandler {

	public static String currentFilePath = "";

	public static void saveGraph() {
		if (currentFilePath.isEmpty()) { 	// Wurde bisher kein Pfad und Dateiname zum Speichern gewählt,
			saveGraphAs();					// wird der Speicherndialog aufgerufen.
		} else {
			//TODO	Speicherung von Graphen durchführen
			// writeGraphToFile(currentFilePath);
		}
	}

	public static void saveGraphAs() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter(
				"Graphendateien (*.gka)", "gka"));
		int ret = chooser.showSaveDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION) {
			if (writeGraphToFile(chooser.getSelectedFile().getAbsolutePath())) {
				currentFilePath = chooser.getSelectedFile().getAbsolutePath();
			}
		}
	}
	
	private static boolean writeGraphToFile(String absolutePath) {
		Writer out;
		try {
			
			//TODO dekommentieren, falls OutputStreamWriter Fehler wirft
//			File file = new File(absolutePath);
//			
//			// Datei erzeugen, falls nicht vorhanden
//			if (!file.exists()) {
//				try {
//					file.createNewFile();
//				} catch (IOException e) {
//					// schreibfehler?
//					e.printStackTrace();
//				}
//			}
			
			out = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(absolutePath), "UTF-8" ) );
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
			//TODO	report error message
			return false;
		}
		try {
			//TODO	Graphobjekt durchscannen und Text erstellen
			out.write("aString");
			//TODO	report success
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			//TODO	report write error
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
				//TODO	"Graphendatei konnte nicht geschlossen werden."
				return false;
			}
		}
		
	}

	public static void openGraph() {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"Graphendateien (*.gka)", "gka");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File graphFile = new File(chooser.getSelectedFile().getAbsolutePath());
			if (graphFile.exists()) {
				readGraphFromFile(graphFile);
			};
		}
	}
	
	private static boolean readGraphFromFile(File graphFile) {
		return false;
	}
	
	private static boolean readGraphFromFile(String absolutePath) {
		throw new NotImplementedException();
	}

}
