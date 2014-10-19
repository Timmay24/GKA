package controller;

import javax.swing.JTextArea;

public class GuiController {

	private JTextArea reportTextArea;
	
	
	public GuiController() {
	}
	
	public GuiController(JTextArea reportTextArea) {
		this.reportTextArea = reportTextArea;
	}

	public void report(String msg) {
		reportTextArea.setText(reportTextArea.getText() + "\n" + msg);
	}
	
	public void setReportTextArea(JTextArea ta) {
		reportTextArea = ta;
	}

}
