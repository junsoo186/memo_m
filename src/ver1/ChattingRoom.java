package ver1;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ChattingRoom extends JFrame {
	
	String roomName;
	JLabel background;
	JLabel setting;
	
	public ChattingRoom() {
		initData();
	}

	
	private void initData() {
	background = new JLabel(new ImageIcon("image/메모장.png"));
	setting = new JLabel(new ImageIcon("image/톱니 바퀴.png"));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	setSize(600, 740);
	setContentPane(background);
	setTitle("메모장");
	setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
	}
	
	private void setInitLayout() {

	}
	
	
}
