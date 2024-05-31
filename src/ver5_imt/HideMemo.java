package ver5_imt;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ver1.LoginFrame;

public class HideMemo extends JFrame {
	ChattingRoom chattingRoom;
	private JLabel background;

	private JLabel setting; // 설정
	private JLabel setting_press;
	
	boolean flag = false;

	private final int LOGIN_SIZE = 17;

	public HideMemo() {
		intitData();
		setInitLayout();
		addEventListener();

	}

	private void intitData() {
		background = new JLabel(new ImageIcon("image/메모장.png"));
		setting = new JLabel(new ImageIcon("image/톱니 바퀴.png"));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(600, 740);
		setContentPane(background);
		setTitle("메모장");
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
	}

	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isSettingButton(e.getX(), e.getY())) {
					setting.setIcon(new ImageIcon("image/톱니 바퀴 눌림.png"));
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isSettingButton(e.getX(), e.getY())) {
					setting.setIcon(new ImageIcon("image/톱니 바퀴.png"));
					flag = true;
					System.exit(0);
					
				}

			}

		});
	}
	
	public boolean isSettingButton(int x, int y) {
		if (558 <= x && x <= 558 + LOGIN_SIZE && 41 <= y && y <= 41 + LOGIN_SIZE) {
			return true;
		}
		return false;
	}

}
