package ver5_imt;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ver2.WaitingRoom;

public class LoginFrame extends JFrame {

	private String id;
	String ip ="192.168.0.127";
	int port = 10000;
	// 새로운 유저 입력장치
	private JTextField inputId;
	ClientBack client;
	// 라벨 모음
	private JLabel loginBt;
	private JLabel loginPressBt;
	private JLabel background;
	
	public LoginFrame() {
		initData();
		setInitLayout();
		addEventListener();
	}
	
	
	private void initData() {
		background = new JLabel(new ImageIcon("image/login.gif"));
		loginBt= new JLabel(new ImageIcon("image/log.png"));
		loginPressBt= new JLabel(new ImageIcon("image/logpress.jpg"));

		setSize(397, 312);
		setLocation(0, 0);
		setContentPane(background);
		// 파비콘  ㅁ======
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
		setTitle("메모장 - 로그인 ");
		// 입력자 생성
		inputId = new JTextField();
	}  // end of init
	
	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		// 로그인 버튼 설정 값
		add(loginBt);
		loginBt.setSize(103, 41);
		loginBt.setLocation(136, 160);
		
		// 입력장치 설정 값
		add(inputId);
		inputId.setSize(154, 24);
		inputId.setLocation(125, 75);
		inputId.setBorder(null);
	} // end of layout
	
	
	
	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isLoginButton(e.getX(), e.getY())) {
					loginBt.setIcon(new ImageIcon("image/logpress.jpg"));

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isLoginButton(e.getX(), e.getY())) {
					loginBt.setIcon(new ImageIcon("image/log.png"));

					id = inputId.getText();
					
					if (id.isEmpty()) {
						System.out.println("아이디 입력 필요");
					} else if (id.equals(" ")) {
						System.out.println("아이디 입력 필요");
					} else if (id.equals("  ")) {
						System.out.println("아이디 입력 필요");
					} else {
						clickLoginBtn();
						setVisible(false);
						
					}
				}
			}
		});
		
		inputId.addKeyListener(new KeyListener() {
			
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBt.setIcon(new ImageIcon("image/logpress.jpg"));
					System.out.println("엔터");
				}

			}
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginBt.setIcon(new ImageIcon("image/log.png"));

					id = inputId.getText();
					
					if (id.isEmpty()) {
						System.out.println("아이디 입력 필요");
					} else if (id.equals(" ")) {
						System.out.println("아이디 입력 필요");
					} else if (id.equals("  ")) {
						System.out.println("아이디 입력 필요");
					} else {
						clickLoginBtn();
						setVisible(false);
						
					}
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			
		
		});

	} // eventListener over
	
	
	// 로그인 버튼의 범위 값
	public boolean isLoginButton(int x, int y) {
		if (144 <= x && x <= 246 && 192 <= y && y <= 230) {
			return true;
		}
		return false;
	}
	
	
	// 로그인 버튼 눌렀을때 실행
	public void clickLoginBtn() {
		(client = new ClientBack(this.id,this.ip,this.port)).setId(id);;
		
		client.conectNetwork();
		client.connectIO();
		try {
			client.writer.write(this.id.trim() + "\n");
			client.writer.flush();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "접속 에러 !", "알림", JOptionPane.ERROR_MESSAGE);
		}
	}
	

	

}// end of 
