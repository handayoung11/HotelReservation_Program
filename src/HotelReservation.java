import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class HotelReservation extends BaseFrame{
	int num;
	JButton jB[]=new JButton[2], pay=new JButton("결제 바로 가기"), re=new JButton("등록");
	JLabel img[]=new JLabel[3];
	JPanel north=new JPanel(new BorderLayout()), list=new JPanel(new GridLayout(0, 1));
	int idx=1;
	JTextArea review=new JTextArea();
	JCheckBox chk=new JCheckBox("익명");
	Star star=new Star();
	String name;
	
	public HotelReservation(int num) {
		super("호텔예약", 700, 800, 2);
		this.num=num;
		setDesign();
		setAction();
	}
	
	void setAction() {
		ID="rlsmd3";
		for(int i=0; i<2; i++)
			jB[i].addActionListener(it -> {
				if(it.getSource()==jB[1]) idx++;
				if(it.getSource()==jB[0]) idx--;
				if(idx>5) idx=1;
				if(idx<1) idx=5;
				load();
			});
		re.addActionListener(it -> {
			if(review.getText().equals("")) {
				err_msg("공백이 존재합니다.");
				return;
			}
			try {
				ResultSet rs=stmt.executeQuery("select * from list where l_id='"+ID+"' and l_name='"+name+"'");
				if(!rs.next()) {
					err_msg("호텔 이용자만 리뷰를 작성할 수 있습니다.");
					return;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(star.getValue()==0) {
				err_msg("만족도를 선택해주세요.");
				return;
			}
			try {
				stmt.execute("insert into review values(0, "+num+", '"+ID+"', '"+review.getText()+"', "+star.getValue()+", date(now()))");
				msg("리뷰가 등록되었습니다.");
				setList();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		pay.addActionListener(it -> {
			Pay pay=new Pay();
			pay.name=name;
			pay.setDesign();
			pay.setPanel(pay.pay);
			pay.setAction();
			pay.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if(pay.chk==0) setVisible(true);
				}
			});
			setVisible(false);
			pay.setVisible(true);
		});
	}
	
	void setDesign() {
		JPanel center=new JPanel(null), imgP=new JPanel(new FlowLayout(0, 10, 5)), south=new JPanel(new GridLayout(1, 0)), img2=new JPanel(new FlowLayout(0, 10, 5)), rig=new JPanel(new FlowLayout(2));
		String lbl[]= {"◀", "▶"};
		for(int i=0; i<2; i++) {
			jB[i]=new JButton(lbl[i]);
			jB[i].setBackground(Color.DARK_GRAY);
			jB[i].setForeground(Color.white);
			jB[i].setMargin(new Insets(0, 0, 0, 0));
			bound(jB[i], center, i==0?10:640, 80, 40, 40);
		}
		bound(imgP, center, 0, 0, 700, 200);
		for(int i=0; i<3; i++) {
			imgP.add(img[i]=new JLabel());
			img[i].setBorder(new LineBorder(Color.black));
			size(img[i], 180, 180);
		}
		size(img[0], 290, 180);
		for(int i=1; i<6; i++) img2.add(new JLabel(img(PATH+"방구조/"+i+".jpg", 40, 40)));
		south.add(img2);
		south.add(rig);
		rig.add(pay);
		size(center, 700, 200);
		north.add(center);
		north.add(south, "South");
		add(north, "North");
		load();
		setCenter();
	}

	void setCenter() {
		JPanel center=new JPanel(new BorderLayout()), south=new JPanel(new GridLayout(1, 0)), lef=new JPanel(new FlowLayout(0)), rig=new JPanel(new FlowLayout(2)), cen=new JPanel(new BorderLayout()), west=new JPanel(new BorderLayout());
		cen.add(review);
		for(int i=0; i<5; i++) {
			star.jL[i].setText("♡");
			star.jL[i].setForeground(Color.red);
		}
		lef.add(chk);
		rig.add(star);
		re.setBackground(Color.cyan);
		re.setForeground(Color.white);
		west.add(new JLabel(img(PATH+"곰6.png", 70, 70)));
		west.add(re, "South");
		review.setBorder(new LineBorder(Color.BLACK));
		center.add(cen, "North");
		cen.add(west, "West");
		cen.add(south, "South");
		south.add(lef);
		south.add(rig);
		lef.setBackground(Color.white);
		star.setBackground(Color.white);
		rig.setBackground(Color.white);
		west.setBackground(Color.white);
		chk.setBackground(Color.white);
		center.add(new JScrollPane(list));
		add(center);
	}

	void setList() {
		list.removeAll();
		try {
			ResultSet rs=stmt.executeQuery("select * from review where rh_no='"+num+"'");
			while(rs.next()) {
				JPanel temp=new JPanel(new BorderLayout());
				JLabel jL=font("♥".repeat(rs.getInt(5)), 25);
				JPanel cen=new JPanel(new GridLayout(0, 1));
				jL.setForeground(Color.RED);
				size(jL, 200, 25);
				temp.add(jL, "West");
				temp.add(cen);
				cen.add(font(rs.getString(4), 20));
				JLabel gr=new JLabel(rs.getString(3)+" | "+new SimpleDateFormat("yyyy-MM-dd").format(rs.getDate(6)));
				gr.setForeground(Color.gray);
				cen.add(gr);
				cen.setBackground(Color.white);
				temp.setBackground(Color.white);
				list.add(temp);
			}
//			list.setBackground(Color.white);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		repaint();
		revalidate();
	}
	
	void load() {
		int cnt=0;
		for(int i=idx; i<idx+3; i++, cnt++) {
			int idx2=i;
			if(idx2>5) idx2-=5;
			if(idx2<1) idx2+=5;
			img[cnt].setIcon(img(PATH+"/방구조/"+idx2+".jpg", cnt==0?290:180, 180));
		}
		repaint();
		revalidate();
	}
}
