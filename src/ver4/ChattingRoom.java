package ver4;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChattingRoom extends JFrame {
	ClientBack mContext;

	String roomName;
	JLabel background;
	JLabel setting;
	HideMemo hideMemo;

	private final int LOGIN_SIZE = 17;
	private JTextArea mainMessageBox;
	JTextField textWrite;

	public ChattingRoom(ClientBack mContext) {

		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
	
	}

	private void initData() {
		background = new JLabel(new ImageIcon("image/메모장.png"));
		setting = new JLabel(new ImageIcon("image/톱니 바퀴.png"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textWrite = new JTextField(20);
		mainMessageBox = new JTextArea();

		// setTitle("메모장");
		setSize(600, 740);
		setContentPane(background);
		// todo 수정예정
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));

		textWrite.setText("대화 입력");

	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		JScrollPane scrollPane = new JScrollPane(mainMessageBox);

		scrollPane.setSize(600, 620);
		scrollPane.setLocation(0, 30);
		add(scrollPane);

		add(setting);
		setting.setSize(LOGIN_SIZE, LOGIN_SIZE);
		setting.setLocation(550, 5);

		textWrite.setSize(600, 20);
		textWrite.setLocation(0, 650);
		textWrite.setBorder(null);
		add(textWrite);

	}

	private void addEventListener() {
		textWrite.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();

				}

			}
		});

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				if (isSettingButton(e.getX(), e.getY())) {
					setting.setIcon(new ImageIcon("image/톱니 바퀴 눌림.png"));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isSettingButton(e.getX(), e.getY())) {
					setting.setIcon(new ImageIcon("image/톱니 바퀴.png"));
					//background.setIcon(new ImageIcon(""));
					
				}

			}

		});

	}

	private void sendMessage() {
		if ((!textWrite.getText().equals(null))) {
			String msg = textWrite.getText();
			System.out.println(msg);
			mContext.clickSendMessageBtn(msg);
			;
			textWrite.setText("");
			textWrite.requestFocus();
		}
	}

	public boolean isSettingButton(int x, int y) {
		if (558 <= x && x <= 558 + LOGIN_SIZE && 41 <= y && y <= 41 + LOGIN_SIZE) {
			return true;
		}
		return false;
	}

	public JTextArea getMainMessageBox() {
		return mainMessageBox;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	
	

}
