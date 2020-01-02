import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;

public class Sign_Up extends BaseFrame{
	JButton jB[]=new JButton[2], bear[]=new JButton[2];
	JTextField txt[]=new JTextField[9];
	String pw="";
	
	public Sign_Up() {
		super("회원가입", 450, 500, 2);
		setDesign();
		setAction();
		setVisible(true);
	}

	void setAction() {
		txt[0].getDocument().addDocumentListener(new DocumentAdapter() {

			@Override
			void update(DocumentEvent e) {
				try {
					ResultSet rs=stmt.executeQuery("select * from member where m_id='"+txt[0].getText()+"'");
					if(rs.next()) SwingUtilities.invokeLater(()-> txt[0].setText(""));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		for(int i=0; i<2; i++) {
			bear[i].addActionListener(it -> {
				if(it.getSource()==bear[0]) {
					Pattern pattern=new Pattern();
					pattern.pattern.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							if(pattern.pattern.preNum.size()<1) return;
							pw=pattern.pattern.getPattern();
							pattern.dispose();
						};
					});
				}
				if(it.getSource()==bear[1]) new Check(txt[8]);
			});
			jB[i].addActionListener(it -> {
				if(it.getSource()==jB[0]) {
					for(int j=0; j<txt.length; j++)
						if(txt[j].getText().equals("")) {
							err_msg("공백이 존재합니다.");
							return;
						}
					if(pw=="") {
						err_msg("공백이 존재합니다.");
						return;
					}
					if(!txt[8].getForeground().equals(Color.blue)) {
						err_msg("인증번호를 확인해주세요.", 1);
						return;
					}
					String birth=txt[2].getText()+"-"+txt[3].getText()+"-"+txt[4].getText();
					try {
						date.parse(birth);
					} catch (ParseException e1) {
						err_msg("날짜를 확인해주세요.");
						return;
					}
					try {
						stmt.execute("insert into member values(0, '"+txt[0].getText()+"', '"+txt[1].getText()+"', '"+pw+"', '"+birth+"', '"+txt[5].getText()+"-"+txt[6].getText()+"-"+txt[7].getText()+"')");
						System.out.println("insert into member values(0, '"+txt[0].getText()+"', '"+txt[1].getText()+"', '"+pw+"', '"+birth+"', '"+txt[5].getText()+"-"+txt[6].getText()+"-"+txt[7].getText()+"')");
						msg("가입이 완료되었습니다.");
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					dispose();
					new Login();
				}
			});
		}
	}

	void setDesign() {
		JPanel center=new JPanel(new GridLayout(0, 1)), south=new JPanel(new GridLayout(1, 0, 10, 0));
		add(center);
		add(south, "South");
		String lbl[]= {"아이디", "비밀번호", "이름", "생년월일", "휴대폰 번호", "인증번호"};
		for(var i=0; i<2; i++) {
			bear[i]=new JButton(img(PATH+"곰1.png", 40, 40));
			bear[i].setMargin(new Insets(0, 0, 0, 0));
			bear[i].setBackground(Color.LIGHT_GRAY);
		}
		for(int i=0; i<txt.length; i++) {
			txt[i]=new JTextField();
			size(txt[i], 300, 40);
		}
		for(int i=0; i<lbl.length; i++) {
			JPanel temp=new JPanel(new FlowLayout(0));
			center.add(temp);
			JLabel jL=font(lbl[i], 15);
			temp.add(jL);
			size(jL, 100, 25);
			if(i==0) temp.add(txt[i]);
			if(i==1) temp.add(bear[0]);
			if(i==2) temp.add(txt[1]);
			if(i==3) {
				String[] lab= {"년", "월", "일"};
				for(int j=0; j<3; j++) {
					temp.add(txt[j+2]);
					size(txt[j+2], j==0?80:70, 40);
					size(txt[j+5], 80, 40);
					temp.add(font(lab[j], 15));
				}
			}
			if(i==4) {
				for(int j=0; j<2; j++) {
					temp.add(txt[j+5]);

					temp.add(new JLabel("-"));
				}
				temp.add(txt[7]);
			}
			if(i==5) {
				size(txt[8], 250, 40);
				temp.add(txt[8]);
				temp.add(bear[1]);
			}
		}
		String lab[]= {"가입", "취소"};
		for(int i=0; i<2; i++) {
			south.add(jB[i]=new JButton(lab[i]));
			jB[i].setBackground(Color.GRAY);
			size(jB[i], 20, 35);
		}
		((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
	}
}
