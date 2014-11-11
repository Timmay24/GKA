package controller.interfaces;

public interface MessageSender {
	
	/**
	 * Zum Anmelden von Objekten, die Nachrichten erhalten sollen.
	 * @param messageListener Anzumeldendes Objekt.
	 */
	public void addMessageListener(MessageListener messageListener);

}
