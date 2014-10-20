package controller;

import javax.swing.JTextArea;

public class GuiController implements GKAController {

	private JTextArea reportTextArea;
	
	
	public GuiController() {}
	
	public GuiController(JTextArea reportTextArea) {
		this.reportTextArea = reportTextArea;
	}

	public void report(String msg) {
		reportTextArea.append(msg + "\n");
	}
	
	public void setReportTextArea(JTextArea ta) {
		reportTextArea = ta;
	}

}
