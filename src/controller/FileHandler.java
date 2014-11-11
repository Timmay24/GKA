package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.file.FileSystemException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileHandler {
	
	public static final String FILE_SUFFIX = ".gka";

	public static File getOutFile(String filePath) throws IOException {
		File file = null;
		JFileChooser chooser = new JFileChooser();

		if (filePath == null || filePath.isEmpty()) {
			chooser.setFileFilter(new FileNameExtensionFilter(
					"Graphendateien (*.gka)", "gka"));
			int ret = chooser.showSaveDialog(null);

			if (ret == JFileChooser.APPROVE_OPTION) {
				filePath = chooser.getSelectedFile().getAbsolutePath();
				if (!filePath.toLowerCase().endsWith(FILE_SUFFIX)) {
					filePath += FILE_SUFFIX;
				}
//				file = new File(filePath);
//				return file;
			}
		}
		file = new File(filePath);
		return file;
	}
	
	public static File getInFile() throws IOException {
		String 			filePath = null;
		File 			file = null;
		JFileChooser 	chooser = new JFileChooser();
		
		chooser.setFileFilter(new FileNameExtensionFilter(
				"Graphendateien (*.gka)", "gka"));
		
		int ret = chooser.showOpenDialog(null);
		
		if (ret == JFileChooser.APPROVE_OPTION) {
			filePath = chooser.getSelectedFile().getAbsolutePath();
			file = new File(filePath);
			return file;
		} else {
			return null;
		}
	}
	
	/**
	 * Liest das uebergebene Dateiobjekt in eine Liste ein.
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFile(File file) throws IOException {
		CharsetDecoder 		decoder = Charset.forName("ISO-8859-1").newDecoder();
		InputStreamReader 	inStreamReader = new InputStreamReader(new FileInputStream(checkFile(file)), decoder);
        BufferedReader 		reader = new BufferedReader(inStreamReader);
        String 				line = null;
        
        List<String> resultList = new ArrayList<>();
        
		while ((line = reader.readLine()) != null) {
			resultList.add(line);
		}
		reader.close();
        return resultList;
	}

	/**
	 * @return Gibt das Objekt der im Chooser gewaehlten Datei zurueck.
	 * @throws FileSystemException
	 */
	public static File openGraph() throws FileSystemException {
		JFileChooser chooser = new JFileChooser();									// FileChooser init.
		FileNameExtensionFilter filter = new FileNameExtensionFilter(				// Dateisuffix Filter auf .gka setzen
				"Graphendateien (*.gka)", "gka");									// "
		chooser.setFileFilter(filter);												// "
		int returnVal = chooser.showOpenDialog(null);								// Chooser oeffnen
		if (returnVal == JFileChooser.APPROVE_OPTION) {								// 
			File graphFile = new File(chooser.getSelectedFile().getAbsolutePath());	// Absoluten Pfad der gewählten Datei holen
			checkFile(graphFile);													// Zugriff auf Datei prüfen
			return graphFile;														// Erzeugtes Dateiobjekt zurückgeben.
		} else {																	// 
			return null;															// Bei Fehlschlägen wird null zurückgegeben
		}
	}
	
	/**
	 * @param coll		Eingabe-Coll., die in die Datei outFile geschrieben werden soll
	 * @param outFile	Zu beschreibendes File-Objekt
	 * @return			Operation erfolgreich?
	 * @throws IOException
	 */
	public static boolean writeToFile(Collection<String> coll, File outFile) throws IOException {
		
		// exisitiert die Datei?
		if (!outFile.exists()) { 
			// Falls nicht --> erstellen
			outFile.createNewFile();
		}
		checkFile(outFile); // Zugriff prüfen
		
		// Stream init.
		OutputStreamWriter outStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile), "ISO-8859-1");
		
		// Alle Zeilen der Collection in die Datei schreiben (mit Umbruechen).
		for (String line : coll) {
			outStreamWriter.write(line + System.getProperty("line.separator"));
		}
		
		outStreamWriter.close();
		
		return true;
	}
	
	
	
	/**
	 * Prueft, ob korrekt auf file zugegriffen werden kann.
	 * 
	 * @param file Dateiobjekt
	 * @return Gibt bei validem Zugriff das gepruefte Objekt zurueck.
	 * @throws FileSystemException
	 */
	public static File checkFile(File file) throws FileSystemException {
		if (!(file.exists() && file.isFile() && file.canRead())) {
			throw new FileSystemException("Dateizugriffsfehler");
		}
		return file;
	}
}