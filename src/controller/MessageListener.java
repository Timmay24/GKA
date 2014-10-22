package controller;

public interface MessageListener {

	/**
	 * Anlaufstelle für eingehende Nachrichten, die an MessageListener verschickt werden.
	 * @param message Eingehende Nachricht
	 */
	public void receiveMessage(String message);
	
}
