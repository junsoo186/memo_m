package ver3;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class RoomMaker extends JFrame {
	
	String userName;
	ClientBack mContext;
	WaitingRoom waitingRoom;
	ChattingRoom chattingRoom;
	JTextField roomName;
	JLabel background;
	JLabel makeBt;

	public RoomMaker(String userName,ClientBack mContext) {
		this.userName = userName;
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		background = new JLabel(new ImageIcon("image/대화방생성/새대화방.png"));
		makeBt = new JLabel(new ImageIcon("image/대화방생성/생성.png"));
		roomName = new JTextField();
		setTitle("대화방 생성");
		setSize(295, 160);
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
		setContentPane(background);
		roomName.setText("대화방 이름");
	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		add(makeBt);
		makeBt.setSize(85, 32);
		makeBt.setLocation(95, 70);
		
		add(getRoomName());
		getRoomName().setSize(154,24);
		getRoomName().setLocation(65,30);
		
		

	}

	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isNewButton(e.getX(), e.getY())) {
					makeBt.setIcon(new ImageIcon("image/대화방생성/생성press.png"));
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				if (isNewButton(e.getX(), e.getY())) {
					makeBt.setIcon(new ImageIcon("image/대화방생성/생성.png"));
					
					String name =roomName.getText();
					
					
					if (name.isEmpty()) {
						System.out.println("대화방 입력");
					} else if (name.equals(" ")) {
						System.out.println("대화방 입력");
					} else if (name.equals("  ")) {
						System.out.println("대화방 입력");
					} else {
						
						mContext.clickMakeRoomBtn(name);
						setVisible(false);
						
						
					}
					
					
					
				}
				
			}
			
			
			
		});
	}

	public boolean isNewButton(int x, int y) {
		if (104 <= x && x <= 186 && 101 <= y && y <= 130) {
			return true;
		}
		return false;
	}
	
	
	public JTextField getRoomName() {
		return roomName;
	}

	public ChattingRoom getChattingRoom() {
		return chattingRoom;
	}

}
