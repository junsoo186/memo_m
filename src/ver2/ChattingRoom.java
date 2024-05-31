package ver2;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChattingRoom extends JFrame {
	Client mContext;
	
	String roomName;
	JLabel background;
	JLabel setting;
	
	JTextArea mainMessageBox;
	JTextField textWrite;
	
	
	public ChattingRoom(String roomName , Client mContext ) {
		
		this.roomName = roomName;
		this.mContext=mContext ;
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

	
	
	setSize(600, 740);
	setContentPane(background);
	// todo 수정예정
	setIconImage(Toolkit.getDefaultToolkit().getImage("image/메모장파비콘.png"));
	
	
	textWrite.setText("아직 모른다");
	

	
	
	
	
	
	}
	
	private void setInitLayout() {
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		
		JScrollPane scrollPane = new JScrollPane(mainMessageBox);
		
		
		scrollPane.setSize(600,620);
		scrollPane.setLocation(0,30);
		add(scrollPane);
		
		
		
		
		textWrite.setSize(600,20);
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
	}
		private void sendMessage() {
			if((!textWrite.getText().equals(null))) {
				String msg = textWrite.getText();
				System.out.println(msg);
		mContext.clickSendMessageBtn(msg);;
			textWrite.setText("");
			textWrite.requestFocus();
			}
		}


		public JTextArea getMainMessageBox() {
			return mainMessageBox;
		}


	

}
