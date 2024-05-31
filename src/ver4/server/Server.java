package ver4.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;





public class Server {

	private Vector<ConnectedUser> connectedUsers = new Vector<>();
	private Vector<MyRoom> madeRooms = new Vector<>();
	private ServerFrame serverFrame;
	private JTextArea mainBoard;

	// 소켓 장치
	private ServerSocket serverSocket;
	private Socket socket;

	// 파일 저장을 위한 장치
	private FileWriter fileWriter;

	// 방이름 확인
	private boolean roomCheck;

	private String protocol;
	private String from;
	private String message;

	public Server() {
		serverFrame = new ServerFrame(this);
		roomCheck = true;
		mainBoard = serverFrame.getMainBoard();

	}

	public void startServer() {
		try {
			serverSocket = new ServerSocket(10000);
			serverViewAppendWriter("서버가 시작됩니다\n");
			connectClient();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "이미 사용중인 포트입니다.", "알림", JOptionPane.ERROR_MESSAGE);
			serverFrame.setConnectBtn(new JLabel(new ImageIcon("image/server/off.png")));
			serverFrame.setEyesBtn(new JLabel(new ImageIcon("image/server/red.png")));

		}
	}

	private void connectClient() {
		new Thread(() -> {
			while (true) {
				try {

					socket = serverSocket.accept();
					serverViewAppendWriter("사용자 대기중\n");
					System.out.println("잘돌아감");
					ConnectedUser user = new ConnectedUser(socket);
					user.start();
				} catch (Exception e) {
					serverViewAppendWriter("에러 발생\n");
					System.out.println("접속에러!!");
				}

			}

		}).start();

	}

	private void broadCast(String msg) {
		for (int i = 0; i < connectedUsers.size(); i++) {
			ConnectedUser user = connectedUsers.elementAt(i);
			 user.writer(msg);
		}
	}
	private void serverViewAppendWriter(String str) {
			mainBoard.append(str);
	}
	private class ConnectedUser extends Thread implements Toolkit {
		// 소켓 장치
		private Socket socket;

		// 입출력 장치
		private BufferedReader reader;
		private BufferedWriter writer;

		// 유저 정보
		private String id;
		private String myRoomName;

		public ConnectedUser(Socket socket) {
			this.socket = socket;
			connectIO();

		}

		private void connectIO() {

			try {
				// 입출력

				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				sendInfomation();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "서버 입출력 장치 에러!", "알림", JOptionPane.ERROR_MESSAGE);
			}

		}// end of connect

		private void sendInfomation() {
			try {
				id = reader.readLine();
				serverViewAppendWriter("ID:"+ id +" [접속]\n");
				newUser();
				connectedUser();
				madeRoom();

			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
			}

		}

		@Override
		public void run() {
			try {
				while (true) {
					String str = reader.readLine();
					checkProtocol(str);

				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "유저 접속 끊김 !", "알림", JOptionPane.ERROR_MESSAGE);
				serverViewAppendWriter("ID: " + id +" [종료]\n");
				for (int i = 0; i < madeRooms.size(); i++) {

					MyRoom myRoom = madeRooms.elementAt(i);
					if (myRoom.roomName.equals(this.myRoomName)) {
						myRoom.removeRoom(this);
					}

				}
				connectedUsers.remove(this);
				broadCast("UserOut/" + id);
				
			}
		}

		private void checkProtocol(String str) {
			StringTokenizer tokenizer = new StringTokenizer(str, "/");

			protocol = tokenizer.nextToken();
			from = tokenizer.nextToken();

			if (protocol.equals("Chatting")) {
				message = tokenizer.nextToken();
				chatting();

			} else if (protocol.equals("MakeRoom")) {
				makeRoom();

			} else if (protocol.equals("OutRoom")) {
				outroom();

			} else if (protocol.equals("EnterRoom")) {
				enterroom();
			}

		}

		private void writer(String str) {
			try {
				writer.write(str + "\n");
				writer.flush();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "서버 출력 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
			}

		}

		@Override
		public void chatting() {
			serverViewAppendWriter("[메세지] " + from + "_" + message + "\n");
			for (int i = 0; i < madeRooms.size(); i++) {
				MyRoom myRoom = madeRooms.elementAt(i);

				if (myRoom.roomName.equals(from)) {
					myRoom.roomBroadCast("Chatting/" + id + "/" + message);
				}
			}
		

		}

		@Override
		public void makeRoom() {
			for (int i = 0; i < madeRooms.size(); i++) {
				MyRoom room = madeRooms.elementAt(i);

				if (room.roomName.equals(from)) {
					writer("FailMakeRoom/" + from);
					roomCheck = false;
				} else {
					roomCheck = true;
				}
			}

			if (roomCheck) {
				myRoomName = from;
				MyRoom myRoom = new MyRoom(from, this);
				madeRooms.add(myRoom);
				serverViewAppendWriter("[방 생성]" + id + "_" + from + "\n");
				newRoom();
				writer("MakeRoom/" + from);
			}
		}

		@Override
		public void madeRoom() {
			for (int i = 0; i < madeRooms.size(); i++) {
				MyRoom myRoom = madeRooms.elementAt(i);
				writer("MadeRoom/" + myRoom.roomName);
			}
		}

		@Override
		public void newRoom() {
			broadCast("NewRoom/" + from);
			}

		@Override
		public void outroom() {
			for (int i = 0; i < madeRooms.size(); i++) {
				MyRoom myRoom = madeRooms.elementAt(i);

				if (myRoom.roomName.equals(from)) {
					myRoomName = null;
					myRoom.roomBroadCast("Chatting/퇴장/" + id + "님 퇴장");
					serverViewAppendWriter("[방 퇴장]" + id + "_" + from + "\n");
					myRoom.removeRoom(this);
					writer("OutRoom/" + from);
				}
			}
		}

		@Override
		public void enterroom() {
			for (int i = 0; i < madeRooms.size(); i++) {
				MyRoom myRoom = madeRooms.elementAt(i);

				if (myRoom.roomName.equals(from)) {
					myRoomName = from;
					myRoom.addUser(this);
					myRoom.roomBroadCast("Chatting/입장/" + id + "님 입장");
					serverViewAppendWriter("[입장]" + from + " 방_" + id + "\n");
					writer("EnterRoom/" + from);
				}
			}
		}

		@Override
		public void newUser() {
			// 자기자신을 벡터에 추가
			connectedUsers.add(this);
			broadCast("NewUser/" + id);
		}

		@Override
		public void connectedUser() {
			for (int i = 0; i < connectedUsers.size(); i++) {
				ConnectedUser user = connectedUsers.elementAt(i);
				writer("ConnectedUser/" + user.id);
			}
		}

	}

	private class MyRoom {
		private String roomName;
		private Vector<ConnectedUser> myRoom = new Vector<>();

		public MyRoom(String roomName, ConnectedUser connectedUser) {
			this.roomName = roomName;
			this.myRoom.add(connectedUser);
			connectedUser.myRoomName = roomName;

		}

		private void roomBroadCast(String msg) {
			for (int i = 0; i < myRoom.size(); i++) {
				ConnectedUser user = myRoom.elementAt(i);

				user.writer(msg);
			}
		}

		private void addUser(ConnectedUser connectedUser) {
			myRoom.add(connectedUser);
		}

		private void removeRoom(ConnectedUser user) {
			myRoom.remove(user);
			boolean empty = myRoom.isEmpty();
			if (empty) {
				for (int i = 0; i < madeRooms.size(); i++) {
					MyRoom myRoom = madeRooms.elementAt(i);

					if (myRoom.roomName.equals(roomName)) {
						madeRooms.remove(this);
						serverViewAppendWriter("[방 삭제]" + user.id + "_" + from + "\n");
						roomBroadCast("OutRoom/" + from);
						broadCast("EmptyRoom/" + from);
						break;
					}
				}
			}
		}

	}

	public static void main(String[] args) {
		new Server();
	}

}
