package ver1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import lombok.Data;
public class Client implements Toolkit, ServerReact{
	
	// 클라이언트 프레임
	private ClientFrame clientFrame;
	private WaitingRoom waitingRoom;
	private LoginFrame loginFrame;
	
	private JTextArea mainMessageBox;
	private JList<String> userList;
	private JList<String> roomList;
	private JButton enterRoomBtn;
	private JButton makeRoomBtn;
	
	private JButton outRoomBtn;
	private JButton secretMsgBtn;
	private JButton sendMessageBtn;
	private String protocol;
	
	// 소켓 장치
	private Socket socket;
	
	// 입출력 장치
	private BufferedReader reader;
	private BufferedWriter writer;
	
	//연결 주소
	private String ip;
	private int port;
	
	
	//유저정보
	private String id;
	private String myRoomName;
	
	//토크나이저 사용 변수
	private String protocl;
	private String from;
	private String message;
	
	private Vector<String> userIdList =new Vector<>();
	private Vector<String> roomNameList =new Vector<>();
	private ImageIcon icon = new ImageIcon("images/erroricon.png");
	public Client() {
		clientFrame = new ClientFrame(this , this);
		new LoginFrame(this, clientFrame);
		
		
		
	}
	
	@Override
	public void clickConnectServerBtn(String ip, int port, String id) {
		this.ip = ip;
		this.port = port;
		this.id = id;
		connectNetwork();
		connectIO();
		
	}
	
	private void connectNetwork() {
		try {

			socket = new Socket(ip, port);

		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
		}
	}
	
	private void connectIO() {
		try {
			// 입출력 장치
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// 입력 스레드
			readThread();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
		}
	}
	private void readThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = reader.readLine();

						checkProtocol(msg);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "클라이언트 입력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
						break;
					}
				}
			}
		}).start();
	}
	private void checkProtocol(String msg) {
		StringTokenizer tokenizer = new StringTokenizer(msg, "/");

		protocol = tokenizer.nextToken();
		from = tokenizer.nextToken();

		if (protocol.equals("Chatting")) {
			message = tokenizer.nextToken();
			chatting();

		} else if (protocol.equals("MakeRoom")) {
			makeRoom();

		} else if (protocol.equals("MadeRoom")) {
			madeRoom();

		} else if (protocol.equals("NewRoom")) {
			newRoom();

		} else if (protocol.equals("OutRoom")) {
			outroom();

		} else if (protocol.equals("EnterRoom")) {
			enterroom();

		} else if (protocol.equals("NewUser")) {
			newUser();

		} else if (protocol.equals("ConnectedUser")) {
			connectedUser();
		} else if (protocol.equals("EmptyRoom")) {
			roomNameList.remove(from);
			roomList.setListData(roomNameList);
			makeRoomBtn.setEnabled(true);
			enterRoomBtn.setEnabled(true);
			outRoomBtn.setEnabled(false);
		} else if (protocol.equals("FailMakeRoom")) {
			JOptionPane.showMessageDialog(null, "같은 이름의 방이 존재합니다 !", "[알림]",
					JOptionPane.ERROR_MESSAGE, icon);
		} else if (protocol.equals("UserOut")) {
			userIdList.remove(from);
			userList.setListData(userIdList);
		}
	}

	private void writer(String str) {
		try {
			writer.write(str + "\n");
			writer.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE, icon);
		}
	}


	@Override
	public void clickSendMessageBtn(String messageText) {
		writer("Chatting/" + myRoomName + "/" + messageText);
	}

	@Override
	public void clickSecretMessageBtn(String msg) {
		String user = (String) waitingRoom.getUserList().getSelectedValue();
		writer("SecretMessage/" + user + "/" + msg);
		
	}

	@Override
	public void clickMakeRoomBtn(String roomName) {
		writer("MakeRoom/" + roomName);
		
	}

	@Override
	public void clickOutRoomBtn(String roomName) {
		writer("OutRoom/" + roomName);
		
	}

	@Override
	public void clickEnterRoomBtn(String roomName) {
		writer("EnterRoom/" + roomName);
		
	}

	@Override
	public void chatting() {
		if (id.equals(from)) {
			mainMessageBox.append("[나] \n" + message + "\n");
		} else if (from.equals("입장")) {
			mainMessageBox.append("▶" + from + "◀" + message + "\n");
		} else if (from.equals("퇴장")) {
			mainMessageBox.append("▷" + from + "◁" + message + "\n");
		} else {
			mainMessageBox.append("[" + from + "] \n" + message + "\n");
		}
		
	}

	@Override
	public void makeRoom() {
		myRoomName = from;
		makeRoomBtn.setEnabled(false);
		enterRoomBtn.setEnabled(false);
		outRoomBtn.setEnabled(true);
		
	}

	@Override
	public void madeRoom() {
		roomNameList.add(from);
		if (!(roomNameList.size() == 0)) {
			roomList.setListData(roomNameList);
		}
		
	}

	@Override
	public void newRoom() {
		roomNameList.add(from);
		roomList.setListData(roomNameList);
	}

	@Override
	public void outroom() {
		myRoomName = null;
		mainMessageBox.setText("");
		makeRoomBtn.setEnabled(true);
		enterRoomBtn.setEnabled(true);
		outRoomBtn.setEnabled(false);
	}

	@Override
	public void enterroom() {
		myRoomName = from;
		makeRoomBtn.setEnabled(false);
		enterRoomBtn.setEnabled(false);
		outRoomBtn.setEnabled(true);
	}

	@Override
	public void newUser() {
		if (!from.equals(this.id)) {
			userIdList.add(from);
			userList.setListData(userIdList);
		}
	}

	@Override
	public void connectedUser() {
		userIdList.add(from);
		userList.setListData(userIdList);
	}

	public static void main(String[] args) {
		new Client();
	}
	
}
