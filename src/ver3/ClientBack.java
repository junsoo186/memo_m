package ver3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

public class ClientBack {
	// 화면부 연결
	public JTextArea mainMessageBox;
	private JList<String> userList;
	private JList<String> roomList;

	// 접속자 명단(userList), 방 명단(roomList)을 업데이트 하기 위한 문자열 벡터
	private Vector<String> userIdList = new Vector<>();
	private Vector<String> roomNameList = new Vector<>();

	String myRoomName; // 임시 변수
	LoginFrame login;

	// 실행자
	private BufferedReader reader;
	public BufferedWriter writer;
	private Socket socket;
	ChattingRoom chattingRoom;

	// 정보값
	private String ip;
	private int port;
	private String id;

	// 바로 실행되는 프레임
	ClientBack mContext;
	WaitingRoom waitingRoom;

	// 토크나이저 사용 변수
	private String protocol;
	private String from;
	private String message;
	
	public ClientBack(String id,String ip,int port) {
		
		// ChattingRoom
		mainMessageBox = new JTextArea(20, 10);
		
		(waitingRoom =new WaitingRoom(this)).setTitle("메모장 [" + id + "]");
		
		userList = waitingRoom.getUserList();
		roomList = waitingRoom.getRoomList();
		mainMessageBox = waitingRoom.getChattingRoom().getMainMessageBox();
		
	}

	// 연결 메서드
	public void conectNetwork() {
		try {
			socket = new Socket("192.168.0.127", 10000);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}
	} // end of network

	public void connectIO() {
		try {
			// 입출력 장치
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// 입력 스레드
			readThread();
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 입출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}

	}

	// 서버 읽는 용 msg 메세지 저장후checkProtocol()호출
	private void readThread() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						String msg = reader.readLine();

						checkProtocol(msg);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "클라이언트 입력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
						break;
					}
				}
			}
		}).start();
	}

	private void writer(String str) {
		try {
			writer.write(str + "\n");
			writer.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "클라이언트 출력 장치 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}
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
			outRoom();

		} else if (protocol.equals("EnterRoom")) {
			enterRoom();

		} else if (protocol.equals("NewUser")) {
			newUser();

		} else if (protocol.equals("ConnectedUser")) {
			connectedUser();
		} else if (protocol.equals("FailMakeRoom")) {
			JOptionPane.showMessageDialog(null, "같은 이름의 방이 존재합니다 !", "[알림]", JOptionPane.ERROR_MESSAGE);
		} else if (protocol.equals("UserOut")) {
			userIdList.remove(from);
			userList.setListData(userIdList);
		}
	}

	public void chatting() {
	
		if (id.equals(from)) {

			mainMessageBox.append( id +"\n"+"┗> " + message + "\n");
		} else if (from.equals("입장")) {
			mainMessageBox.append("▶" + from + "◀" + message + "\n");
		} else if (from.equals("퇴장")) {
			mainMessageBox.append("▷" + from + "◁" + message + "\n");
		} else {
			mainMessageBox.append("[" + from + "] \n" + message + "\n");
		}
	}

	public void makeRoom() {
		myRoomName = from;

	}

	public void madeRoom() {
		roomNameList.add(from);
		if (!(roomNameList.size() == 0)) {
			roomList.setListData(roomNameList);
		}
	}

	public void newRoom() {
		roomNameList.add(from);
		roomList.setListData(roomNameList);
	}

	public void outRoom() {
		myRoomName = null;
		mainMessageBox.setText("");
	}

	public void enterRoom() {
		myRoomName = from;
	}

	public void newUser() {
		if (!from.equals(this.id)) {
			userIdList.add(from);
			userList.setListData(userIdList);
		}
	}

	public void connectedUser() {
		userIdList.add(from);
		userList.setListData(userIdList);
	}

	public void clickSendMessageBtn(String messageText) {
		writer("Chatting/" + myRoomName + "/" + messageText);
	}

	public void clickSendSecretMessageBtn(String msg) {
		String user = (String) waitingRoom.getUserList().getSelectedValue();
		writer("SecretMessage/" + user + "/" + msg);
	}

	public void clickMakeRoomBtn(String roomName) {
		waitingRoom.getChattingRoom().setTitle("메모장"+"["+ id + "] /"+roomName);
		waitingRoom.getChattingRoom().setVisible(true);;
		writer("MakeRoom/" + roomName);
	}

	public void clickOutRoomBtn(String roomName) {
		writer("OutRoom/" + roomName);
	}

	public void clickEnterRoomBtn(String roomName) {
		waitingRoom.getChattingRoom().setTitle("메모장"+"["+ id + "] /"+roomName);
		waitingRoom.getChattingRoom().setVisible(true);;
		writer("EnterRoom/" + roomName);
	}


	public void setUserList(JList<String> userList) {
		this.userList = userList;
	}

	public void setUserIdList(Vector<String> userIdList) {
		this.userIdList = userIdList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setMainMessageBox(JTextArea mainMessageBox) {
		this.mainMessageBox = mainMessageBox;
	}

	public JTextArea getMainMessageBox() {
		return mainMessageBox;
	}

	public String getId() {
		return id;
	}

	// getter and setter

}
