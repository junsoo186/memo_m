package ver2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import lombok.Data;

public class WaitingRoom extends JFrame  {

	private Client mContext;
	private JLabel backGround;
	private LoginFrame loginFrame;
	// 프레임 위 버튼
	private JLabel setting;
	private JLabel setting_press;
	private JLabel visitButton;
	private JLabel visitButton_Press;
	private JLabel findButton;
	private JLabel findButton_Press;
	private JLabel searchBt;
	JPanel roomListPanel;
	String id;
	// ============================

	// 프레임 안 버튼

	private JLabel makeRoomBt;
	private JLabel makeRoomBt_Press;
	private JLabel enterRoomBt;
	private JLabel enterRoomBt_Press;

	private JList<String> roomList;
	private JList<String> userList;
	private JTextField roomFinder;

	private final int LOGIN_SIZE = 17;

	private Vector<String> userIdVector = new Vector<>();
	private Vector<String> roomNameVector = new Vector<>();

	private ServerReact react;

	public WaitingRoom( Client mContext) {
		
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
		 
	}

	public JList<String> getUserList() {
		return userList;
	}

	private void initData() {
		backGround = new JLabel(new ImageIcon("image/대기/waitingroom.png"));
		setting = new JLabel(new ImageIcon("image/톱니 바퀴.png"));
		userList = new JList<>();
		mContext.setUserIdList(userIdVector);
		setRoomList(new JList<>());
		roomListPanel = new JPanel();
		visitButton = new JLabel(new ImageIcon("image/visit.png"));
		visitButton_Press = new JLabel(new ImageIcon("image/visitpress.png"));
		findButton = new JLabel(new ImageIcon("image/find.png"));
		findButton_Press = new JLabel(new ImageIcon("image/findpress.png"));
		makeRoomBt = new JLabel(new ImageIcon("image/대기/newRoom.png"));
		makeRoomBt_Press = new JLabel(new ImageIcon("image/대기/newRoomPress.png"));
		enterRoomBt = new JLabel(new ImageIcon("image/대기/roomIn.png"));
		enterRoomBt_Press = new JLabel(new ImageIcon("image/대기/roomInPress.jpg"));
		searchBt = new JLabel(new ImageIcon("image/대기/search.png"));
		roomFinder = new JTextField();
		setSize(600, 740);
		setContentPane(backGround);
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		add(setting);
		setting.setSize(LOGIN_SIZE, LOGIN_SIZE);
		setting.setLocation(550, 5);
		add(visitButton);
		visitButton.setSize(64, 26);
		visitButton.setLocation(10, 1);
		add(findButton);
		findButton.setSize(64, 26);
		findButton.setLocation(70, 1);
		add(makeRoomBt);
		makeRoomBt.setSize(110, 44);
		makeRoomBt.setLocation(18, 450);
		add(enterRoomBt);
		enterRoomBt.setSize(110, 44);
		enterRoomBt.setLocation(138, 450);
		add(roomFinder);
		roomFinder.setSize(154, 24);
		roomFinder.setLocation(370, 94);
		roomFinder.setBorder(null);
		add(searchBt);
		searchBt.setSize(25, 25);
		searchBt.setLocation(0, 0);
		roomListPanel.setBounds(230, 30, 120, 260);
		roomListPanel.setBackground(Color.WHITE);
		roomListPanel.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 3), "room List"));
		roomListPanel.add(roomList);
		add(roomListPanel);
		
		
	}

	
	
	
	
	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());

				if (isMakeRoomBt(e.getX(), e.getY())) {
					makeRoomBt.setIcon(new ImageIcon("image/대기/newRoomPress.png"));
				}
				if (isRoomInBt(e.getX(), e.getY())) {
					enterRoomBt.setIcon(new ImageIcon("image/대기/roomInPress.jpg"));
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isMakeRoomBt(e.getX(), e.getY())) {
					
					makeRoomBt.setIcon(new ImageIcon("image/대기/newRoom.png"));
					new RoomMaker(id,mContext);

					
				}
				if (isRoomInBt(e.getX(), e.getY())) {
					
					enterRoomBt.setIcon(new ImageIcon("image/대기/roomIn.png"));
					String roomName = getRoomList().getSelectedValue();
					mContext.clickEnterRoomBtn(roomName);
					getRoomList().setSelectedValue(null, false);

				}
			}
		});
	}
	
	public boolean isMakeRoomBt(int x, int y) {
		if (28 <= x && x <= 133 && 482 <= y && y <= 523) {
			return true;
		}
		return false;
	}

	public boolean isRoomInBt(int x, int y) {
		if (147 <= x && x <= 254 && 483 <= y && y <= 523) {
			return true;
		}
		return false;
	}
	public boolean isSearchBt(int x, int y) {
		if (147 <= x && x <= 254 && 483 <= y && y <= 523) {
			return true;
		}
		return false;
	}

	public JList<String> getRoomList() {
		return roomList;
	}

	public void setRoomList(JList<String> roomList) {
		this.roomList = roomList;
	}
	

}
