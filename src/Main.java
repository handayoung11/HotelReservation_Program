import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;

public class Main extends BaseFrame{
	JTextField txt[]=new JTextField[2];
	JComboBox peo[]=new JComboBox[2];
	JButton jB=new JButton("검색");
	JLabel jL[]=new JLabel[3], cal=new JLabel(img(PATH+"달력.png", 40, 40)), day=font("[1박2일]", 20);
	Star star=new Star();

	public Main() {
		super("YANULJA", 1000, 550, 2);
		setDesign();
		setAction();
		setVisible(true);
	}
	
	void setDefault() {
		for(int i=0; i<5; i++) star.jL[i].setText("☆");
		txt[0].setText("");
		txt[1].setText("");
		peo[0].setSelectedIndex(0);
		peo[1].setSelectedIndex(0);
		day.setText("[1박2일]");
	}
	
	void setDesign() {
		JPanel north=new JPanel(new GridLayout(1, 0)), lblP=new JPanel(new GridLayout(1, 0)), center=new JPanel(new GridLayout(0, 1));
		String lbl[]= {"비밀번호 재설정", "예약내역", "로그아웃"};
		((JPanel)getContentPane()).setBorder(new EmptyBorder(20, 10, 10, 10));
		for(int i=0; i<3; i++) {
			lblP.add(jL[i]=font(lbl[i], 20, JLabel.RIGHT));
			jL[i].setForeground(Color.white);
		}
		lblP.setOpaque(false);
		north.setOpaque(false);
		((JPanel)getContentPane()).setBackground(Color.cyan);
		JLabel jL, logo;
		north.add(logo=new JLabel(img(PATH+"로고.png", 170, 40)));
		logo.setHorizontalAlignment(JLabel.LEFT);
		north.add(lblP);
		add(jL=new JLabel(img(PATH+"곰3.png", 300, 300)), "East");
		jL.setVerticalAlignment(JLabel.BOTTOM);
		add(north, "North");
		add(center);
		center.setBackground(Color.blue);
		String lab[]= {"여행지", "숙박 예정 일자", "성인", "어린이", "호텔등급"};
		JPanel[] temp=new JPanel[3], lP=new JPanel[3];
		for(int i=0; i<lab.length; i++) {
			if(i<3) {
				lP[i]=new JPanel(new FlowLayout(0));
				temp[i]=new JPanel(new FlowLayout(0));
				lP[i].setOpaque(false);
				temp[i].setOpaque(false);
			}
			JLabel label=font(lab[i], 20);
			label.setForeground(Color.white);
			if(i<2) lP[i].add(label);
			else {
				size(label, 100, 30);
				lP[2].add(label);
			}
		}
		star.setOpaque(false);
		temp[0].add(txt[0]=new JTextField(60));
		temp[1].add(cal);
		temp[1].add(txt[1]=new JTextField(20));
		day.setForeground(Color.white);
		temp[1].add(day);
		for(int i=0; i<2; i++) {
			temp[2].add(peo[i]=new JComboBox());
			size(peo[i], 100, 30);
			for(int j=0; j<=10; j++) peo[i].addItem(j);
		}
		temp[2].add(star);
		jB.setBackground(Color.gray);
		temp[2].add(jB);
		jB.setForeground(Color.white);
		size(jB, 120, 30);
		for(int i=0; i<3; i++) {
			center.add(new JLabel());
			center.add(lP[i]);
			center.add(temp[i]);
		}
		center.add(new JLabel());
		txt[1].setFocusable(false);
	}
	
	
	void setAction() {
		for(int i=0; i<3; i++)
			jL[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getSource()==jL[0]) new RePW();
					if(e.getSource()==jL[1]) {
						setVisible(false);
						new ReservationList();
					}
					if(e.getSource()==jL[2]) dispose();
				}
			});
		cal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				new CalForm(txt[1], day);
			}
		});
		jB.addActionListener(it -> {
			if(txt[1].getText().equals("")) {
				err_msg("숙박일자를 선택해주세요.");
				return;
			}
			if(peo[0].getSelectedIndex()+peo[1].getSelectedIndex()==0) {
				err_msg("인원을 선택해주세요.");
				return;
			}
			if(star.getValue()==0) {
				err_msg("호텔등급을 선택해주세요.");
				return;
			}
			if(peo[0].getSelectedIndex()==0) {
				err_msg("미성년자는 호텔예약이 불가합니다.\n성인을 1명이상 선택해주세요.");
				return;
			}
			if(txt[0].getText().equals("")) return;
			int days=toint(day.getText().split("박")[0].replace("[", ""));
			PAY=(int)((70000+peo[0].getSelectedIndex()*20000+peo[1].getSelectedIndex()*10000)*days*1.17);
			setVisible(false);
			Hotel hotel=new Hotel(txt[0].getText(), star.getValue());
			hotel.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					setVisible(true);
				}
			});
			adult=peo[0].getSelectedIndex();
			child=peo[1].getSelectedIndex();
			String day[]=txt[1].getText().split("~");
			try {
				DateTimeFormatter date=DateTimeFormatter.ofPattern("yyyy.M.dd");
				hotel.start=LocalDate.parse(day[0], date);
				hotel.end=LocalDate.parse(day[1], date);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			setDefault();
		});
	}
}
