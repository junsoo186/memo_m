package ver4;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class MainFrame extends JFrame {
	

	private JLabel background;
	private JLabel iconTop;

	// ===버튼===
	private JLabel setting; // 설정
	private JLabel setting_press;
	private JLabel visitButton;
	private JLabel visitButton_Press;
	private JLabel findButton;
	private JLabel findButton_Press;
	

	private final int LOGIN_SIZE = 17;
	

	private Image image = null;

	public MainFrame() {
		intitData();
		setInitLayout();
		addEventListener();

	}


	private void intitData() {
		background = new JLabel(new ImageIcon("image/메모장.png"));
		setting = new JLabel(new ImageIcon("image/톱니 바퀴.png"));
		visitButton = new JLabel(new ImageIcon("image/visit.png"));
		visitButton_Press = new JLabel(new ImageIcon("image/visitpress.png"));
		findButton = new JLabel(new ImageIcon("image/find.png"));
		findButton_Press = new JLabel(new ImageIcon("image/findpress.png"));

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
		add(visitButton);
		visitButton.setSize(64, 26);
		visitButton.setLocation(10, 1);
		add(findButton);
		findButton.setSize(64, 26);
		findButton.setLocation(70, 1);
	}

	private void addEventListener() {
		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println(e.getX() + " " + e.getY());
				if (isSettingButton(e.getX(), e.getY())) {
					System.out.println("세팅눌림");
					setting.setIcon(new ImageIcon("image/톱니 바퀴 눌림.png"));
				}
				if (isVistButton(e.getX(), e.getY())) {
					visitButton.setIcon(new ImageIcon("image/visitpress.png"));

				}
				if (isFindButton(e.getX(), e.getY())) {
					findButton.setIcon(new ImageIcon("image/findpress.png"));
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (isSettingButton(e.getX(), e.getY())) {
					setting.setIcon(new ImageIcon("image/톱니 바퀴.png"));
				}
				if (isVistButton(e.getX(), e.getY())) {
					visitButton.setIcon(new ImageIcon("image/visit.png"));
					new LoginFrame();
					setVisible(false);
				}
				if (isFindButton(e.getX(), e.getY())) {
					findButton.setIcon(new ImageIcon("image/find.png"));
				
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

	public boolean isVistButton(int x, int y) {
		if (33 <= x && x <= 73 && 41 <= y && y <= 55) {
			return true;
		}
		return false;
	}

	public boolean isFindButton(int x, int y) {
		if (90 <= x && x <= 132 && 41 <= y && y <= 55) {
			return true;
		}
		return false;
	}

	// test

	public static void main(String[] args) {
	new MainFrame();
	}

}
