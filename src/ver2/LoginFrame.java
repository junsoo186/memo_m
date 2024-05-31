package ver2;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import lombok.Data;

public class LoginFrame extends JFrame {
	ClientFrame Cframe;
	Client mContext;
	
	private JLabel login_Background;
	private JLabel frame ;
	private JLabel loginButton;
	private JLabel loginButtonPress;
	private JTextField idname;
	String name;
	private ServerReact react;

	public LoginFrame(Client mContext,ClientFrame Cframe ) {
		
		this.Cframe = Cframe;
		this.mContext = mContext;
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		login_Background = new JLabel(new ImageIcon("image/login.gif"));
		loginButton = new JLabel(new ImageIcon("image/log.png"));
		loginButtonPress = new JLabel(new ImageIcon("image/logpress.jpg"));

		setSize(397, 312);
		setLocation(0, 0);
		setContentPane(login_Background);
		setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
		setTitle("메모장 - 로그인 ");
		idname = new JTextField();

	}

	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		add(loginButton);
		loginButton.setSize(103, 41);
		loginButton.setLocation(136, 160);
		add(getIdname());
		getIdname().setSize(154, 24);
		getIdname().setLocation(125, 75);
		getIdname().setBorder(null);
	}

	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isLoginButton(e.getX(), e.getY())) {
					loginButton.setIcon(new ImageIcon("image/logpress.jpg"));

				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isLoginButton(e.getX(), e.getY())) {
					loginButton.setIcon(new ImageIcon("image/log.png"));

					name = idname.getText();
					
					if (name.isEmpty()) {
						System.out.println("아이디 입력 필요");
					} else if (name.equals(" ")) {
						System.out.println("아이디 입력 필요");
					} else if (name.equals("  ")) {
						System.out.println("아이디 입력 필요");
					} else {
						mContext.clickConnectServerBtn( "192.168.0.127", 10000, name);
						WaitingRoom waitingRoom = new WaitingRoom(mContext);
						waitingRoom.setTitle("메모장 -" + name);
						mContext.setupList(waitingRoom);
						setVisible(false);
						Cframe.setVisible(false);
					}
				}
			}
		});

	} // eventListener over

	public boolean isLoginButton(int x, int y) {
		if (144 <= x && x <= 246 && 192 <= y && y <= 230) {
			return true;
		}
		return false;
	}

	JTextField getIdname() {
		return idname;
	}

	public void setIdname(JTextField idname) {
		this.idname = idname;
	}

}
