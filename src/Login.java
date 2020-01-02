import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Login extends BaseFrame {
	JTextField txt=new JTextField("");
	JButton jB=new JButton("로그인");
	JLabel jL[]=new JLabel[2];
	
	public Login() {
		super("Login", 400, 200, JFrame.EXIT_ON_CLOSE);
		setDesign();
		txt.setOpaque(false);
		txt.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
		setAction();
	}
	
	
	
	void setAction() {
		jB.addActionListener(it -> {
			if(txt.getText().equals("")) {
				err_msg("아이디를 입력해주세요.");
				return;
			}
			if(txt.getText().equals("admin")) {
				msg("관리자님 로그인 되었습니다.");
				new Admin().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						new Login().setVisible(true);
					}
				});
				dispose();
				return;
			}
			try {
				ResultSet rs=stmt.executeQuery("select * from member where m_id='"+txt.getText()+"'");
				rs.next();
				ID=rs.getString(2);
				NO=rs.getInt(1);
				String pw=rs.getString(4), name=rs.getString(3);
				Pattern pattern=new Pattern();
				pattern.pattern.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseReleased(MouseEvent e) {
						if(pattern.pattern.preNum.size()==0) return;
						if(!pattern.pattern.getPattern().equals(pw)) err_msg("패턴이 일치하지 않습니다.");
						else {
							msg(name+"님 로그인 되었습니다.");
							MAIN=new Main();
							MAIN.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									new Login().setVisible(true);
								}
							});
							dispose();
						}
						pattern.dispose();
					}
				});
				
			} catch (SQLException e1) {
				err_msg("존재하지 않는 아이디입니다.", 1);
			}
		});
		jL[0].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(jL[0].getText().equals("아이디 저장")) {
					jL[0].setText("√ 아이디 저장");
					PRE.put("id", txt.getText());
				}else {
					PRE.remove("id");
					jL[0].setText("아이디 저장");
				}
			}
		});
		jL[1].addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				dispose();
				new Sign_Up().addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosed(WindowEvent e) {
						new Login().setVisible(true);
					}
				});
			}
		});
	}

	void setDesign() {
		JPanel center=new JPanel(new GridLayout(0, 1, 0, 10)), south=new JPanel(new GridLayout(1, 0));
		((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		txt.setText(PRE.get("id", "아이디"));
		add(center);
		add(south, "South");
		center.add(txt);
		center.add(jB);
		jB.setBackground(Color.gray);
		jB.setForeground(Color.white);
		south.add(jL[0]=font("아이디 저장", 20));
		if(!PRE.get("id", "a!a!A").equals("a!a!A")) jL[0].setText("√ 아이디 저장");
		south.add(jL[1]=font("<HTML><U><span style=color:blue>회원가입</span></U></HTML>", 20, JLabel.RIGHT));
		
	}

}
