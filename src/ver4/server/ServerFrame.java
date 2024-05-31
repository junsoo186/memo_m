package ver4.server;

import java.awt.Color;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerFrame extends JFrame {

	private Server mContext;

	private ScrollPane scrollPane;
	// 배경
	private JLabel background;

	// 포트
	private JLabel portLabel;

	// 텍스트
	private JPanel mainPanel;
	private JTextArea mainBoard;
	private JTextField inputPort;

	// 버튼
	private JLabel connectBtn;
	private JLabel eyesBtn;
	
	boolean flag = false;

	public ServerFrame(Server mContext) {
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
		
	}

	private void initData() {
		background = new JLabel(new ImageIcon("image/server/서버메모장.png"));
		setConnectBtn(new JLabel(new ImageIcon("image/server/off.png")));
		setEyesBtn(new JLabel(new ImageIcon("image/server/red.png")));

		mainPanel = new JPanel();
		mainBoard = new JTextArea();
		scrollPane = new ScrollPane();
		inputPort = new JTextField(9);

		inputPort.setText("3000만큼사랑해");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(600, 740);
		setContentPane(background);
		setTitle("메모장 - 서버");
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/서버메모장파비콘.png"));

	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		add(connectBtn);
		connectBtn.setSize(39, 39);
		connectBtn.setLocation(272, 180);
		add(eyesBtn);
		eyesBtn.setSize(15, 15);
		eyesBtn.setLocation(550, 680);

		// 포트
		inputPort.setSize(110, 20);
		inputPort.setLocation(250, 125);
		inputPort.setBorder(null);
		add(inputPort);

		// 메인 패널
		mainPanel.setBorder(null);
		mainPanel.setSize(280, 280);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setLocation(155, 300);
		scrollPane.setSize(280, 290);
		mainBoard.setEditable(false);
		mainPanel.add(scrollPane);
		scrollPane.add(mainBoard);
		background.add(mainPanel);
	}

	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isConnectBtn(e.getX(), e.getY())) {
					connectBtn.setIcon(new ImageIcon("image/server/on.png"));
					eyesBtn.setIcon(new ImageIcon("image/server/Green.png"));
				mContext.startServer();
				}
			}

		});

	}
	public JTextArea getMainBoard() {
		return mainBoard;
	}

	public boolean isConnectBtn(int x, int y) {
		if (286 <= x && x <= 311 && 214 <= y && y <= 243) {
			return true;
		}
		return false;
	}


	public void setConnectBtn(JLabel connectBtn) {
		this.connectBtn = connectBtn;
	}

	public void setEyesBtn(JLabel eyesBtn) {
		this.eyesBtn = eyesBtn;
	}


}
