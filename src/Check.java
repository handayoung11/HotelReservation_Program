import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Check extends BaseFrame{
	JLabel img=new JLabel();
	String ran="";
	String now=LocalDateTime.now().format(DateTimeFormatter.ofPattern("M월 d일 (E) a hh:mm"));
	JTextField txt=new JTextField(), num;
	JButton jB=new JButton();
	JPanel center=new JPanel(null);
	
	public Check(JTextField num) {
		super("휴대폰 인증", 370, 540, 2);
		this.num=num;
		setDesign();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				shake();
			}
		});
		setAction();
		setVisible(true);
	}
	
	void setAction() {
		jB.addActionListener(it -> {
			bound(font(txt.getText(), 15), center, getWidth()-140, 245, 100, 30);
			getNow(210);
			load(2);
			shake();
			getNow(275);
			String str="인증번호가 일치하지 않습니다.";
			num.setForeground(Color.red);
			if(txt.getText().equals(ran)) {
				str="인증번호가 일치합니다.";
				num.setForeground(Color.blue);
			}
			num.setText(txt.getText());
			bound(font("<HTML>[Web 발신] Hotels Combine<br>"+str+"</HTML>", 15), center, 40, getHeight()-220, 250, 50);
			load(3);
			paintAll(getGraphics());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			dispose();
		});
	}
	
	void shake() {
		var pt=5;
		for(int i=0; i<20; i++) {
			pt*=-1;
			setLocation(getX()+pt, getY());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			paintAll(getGraphics());
		}
	}
	
	void setDesign() {
		for(int i=0; i<6; i++) ran+=new Random().nextInt(10);
		add(center);
		txt.setBorder(new EmptyBorder(0, 0, 0, 0));
		txt.setOpaque(false);
		jB.setContentAreaFilled(false);
		jB.setBorder(new EmptyBorder(0, 0, 0, 0));
		bound(txt, center, 80, 400, 200, 25);
		getNow(120);
		bound(jB, center, getWidth()-80, getHeight()-140, 25, 25);
		bound(font("<HTML>[Web 발신] Hotels Combine<br>회원 인증번호는 "+ran+" 입니다.</HTML>", 15), center, 40, 25, 250, 300);
		load(1);
	}
	
	void getNow(int y) {
		JLabel jL=new JLabel(now, JLabel.CENTER);
		jL.setForeground(Color.gray);
		bound(jL, center, 0, y, 370, 40);
	}
	
	void load(int idx) {
		img.setIcon(img(PATH+"인증"+idx+".png", 350, 500));
		center.remove(img);
		bound(img, center, 0, 0, 400, 500);
	}
	
	boolean getCheck() {
		return false;
	}
}
