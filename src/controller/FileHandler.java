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
				file = new File(filePath);
				return file;
			}
		}
		return null;
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
	
	public static List<String> readFile(File file) throws IOException {
		CharsetDecoder 		decoder = Charset.forName("ISO-8859-1").newDecoder();
		InputStreamReader 	inStreamReader = new InputStreamReader(new FileInputStream(checkFile(file)), decoder);
        BufferedReader 		reader = new BufferedReader(inStreamReader);
        String 				line = null;
        
        List<String> resultList = new ArrayList<>();
        
		while ((line = reader.readLine()) != null) {
			/**
			 *  Pro Zeile werden Whitespaces (Leerzeichen, Tabs) entfernt und sie wird
			 *  nochmal in eine Unterliste nach Semikolons gesplittet, damit fehlende Umbrueche
			 *  keine Schwierigkeiten bereiten.
			 *  addAll fuegt anschlieﬂend das Unterliste der Ergebnisliste hinzu.
			 */
			resultList.addAll(Arrays.asList(line.replace(" ", "").replace("\t", "").split(";")));
		}
		reader.close();
        return resultList;
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
			return graphFile;
		} else {
			return null;
		}
	}
	
	public static boolean writeToFile(Collection<String> coll, File outFile) throws IOException {
		OutputStreamWriter outStreamWriter = null;
		if (!outFile.exists()) { 
			outFile.createNewFile();
		}
		checkFile(outFile);
		outStreamWriter = new OutputStreamWriter(new FileOutputStream(outFile), "ISO-8859-1");
		
		// outStreamWriter.write(""); // ???
		//TODO: eventuell append() statt write() nehmen, falls es probleme gibt.
		for (String line : coll) {
			outStreamWriter.write(line + System.getProperty("line.separator"));
		}
		
		outStreamWriter.close();
		
		return true;
	}
	
	
	
	public static File checkFile(File file) throws FileSystemException {
		if (!(file.exists() && file.isFile() && file.canRead())) {
			throw new FileSystemException("Dateizugriffsfehler");
		}
		return file;
	}
}