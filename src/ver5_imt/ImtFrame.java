package ver5_imt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImtFrame extends JFrame{
	private JLabel imt_back;
	
	public ImtFrame() {
		initData();
		setInitLayout();
	}
	
	

	
	private void initData() {
		imt_back = new JLabel(new ImageIcon("image/메모장.png"));
		
		setSize(300, 300);
		setContentPane(imt_back);
	}
	
	
	private void setInitLayout() {
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
		JPanel main = new JPanel();
		JScrollPane imtscroll = new JScrollPane(main);
		imtscroll.setSize(300,300);
		imtscroll.setLocation(0,0);
		add(imtscroll);
	}
	
	public static void main(String[] args) {
		new ImtFrame();
	}
}
