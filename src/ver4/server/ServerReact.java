package ver4.server;

public interface ServerReact {
	void clickConnectServerBtn(String ip, int port, String id);

	void clickSendMessageBtn(String messageText);
	
	void clickSecretMessageBtn(String msg);
	
	void clickMakeRoomBtn(String roomName);
	
	void clickOutRoomBtn(String roomName);
	
	void clickEnterRoomBtn(String roomName);
}
