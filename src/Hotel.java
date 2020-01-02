import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class Hotel extends BaseFrame{
	String trip;
	int grade;
	static LocalDate start;
	static LocalDate end;
	
	public Hotel(String trip, int val) {
		super("호텔", 700, 700, 2);
		grade=val;
		this.trip=trip;
		setDesign();
		setAction();
		setVisible(true);
	}

	private void setDesign() {
		try {
			String split[]=trip.split(" ");
			String sql="";
			for(int i=0; i<split.length; i++) sql+=(sql==""?"": "and ")+"h_address like '%"+split[i]+"%'";
			ResultSet rs=stmt.executeQuery("select * from hotel  where h_grade='"+grade+"' and ("+sql+")");
			if(drawList(rs)==0) drawList(stmt.executeQuery("select * from hotel  where h_grade='"+grade+"' and ("+sql.replace("and", "or")+")"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}
	
	int drawList(ResultSet rs) throws SQLException{
		((JPanel)getContentPane()).removeAll();
		JPanel center=new JPanel(new GridLayout(0, 1, 0, 10));
		add(new JScrollPane(center));
		EmptyBorder em=new EmptyBorder(10, 10, 10, 10);
		center.setBorder(em);
		int cnt=0;
		while(rs.next()) {
			JPanel temp=new JPanel(new BorderLayout()), cen=new JPanel(new GridLayout(0, 1)), east=new JPanel(new GridLayout(0, 1)), fl=new JPanel(new FlowLayout(0)), img=new JPanel(new FlowLayout(0, 5, 0));
			String str[]=rs.getString(3).split(" ");
			JLabel jL=font((str.length>=2?str[1]+",":"")+str[0].substring(0, 2), 15), pay=font("￦ "+new DecimalFormat("#,###").format(rs.getInt(5)), 30), heart=font("♡", 25, JLabel.CENTER);
			JButton jB=new JButton("바로 예약");
			jB.setForeground(Color.white);
			jB.setBackground(Color.blue);
			heart.setForeground(Color.red);
			pay.setForeground(Color.blue);
			size(jL, 130, 25);
			jL.setForeground(Color.GRAY);
			fl.add(jL);
			JLabel star=new JLabel("★".repeat(rs.getInt(4)));
			star.setFont(new Font("HY견고딕", Font.BOLD, 26));
			star.setForeground(Color.ORANGE);
			fl.add(star);
			size(star, 150, 35);
			temp.setBorder(em);
			temp.setBackground(Color.WHITE);
			temp.add(new JLabel(img(PATH+"Hotel/"+rs.getString(2)+".jpg", 120, 120)), "West");
			temp.add(cen);
			temp.add(east, "East");
			cen.add(font(rs.getString(2), 25));
			cen.add(fl);
			east.add(pay);
			east.add(heart);
			east.add(jB);
			ResultSet rs2=con.createStatement().executeQuery("select * from wishlist where w_id='"+ID+"' and wh_no="+rs.getInt(1));
			if(rs2.next())  heart.setText("♥");
			for(int i=1; i<=5; i++) img.add(new JLabel(img(PATH+"방구조/"+i+".jpg", 40, 40)));
			cen.add(img);
			cen.add(new JLabel());
			size(temp, 50, 150);
			int num=rs.getInt(1);
			String name=rs.getString(2);
			heart.addMouseListener(new MouseAdapter() {
				public void mousePressed(java.awt.event.MouseEvent e) {
					JLabel se=(JLabel)e.getSource();
					if(se.getText().equals("♡")) {
						se.setText("♥");
						execute("insert into wishlist values(0, '"+ID+"', "+num+")");
					}else {
						se.setText("♡");
						execute("delete from wishlist where wh_no="+num+" and w_id='"+ID+"'");
					}
				};
			});
			jB.addActionListener(it -> {
				try {
					//2019-09-30, 2019-09-05
					ResultSet rs3=stmt.executeQuery("select * from list where l_checkout>='"+start+"' and l_checkin<='"+end+"' and l_name='"+name+"'");
					if(rs3.next()) {
						err_msg("예약내역이 존재합니다.");
						return;
					}
					setVisible(false);
					HotelReservation res=new HotelReservation(num);
					JPanel north=new JPanel(new BorderLayout());
					res.north.add(north, "North");
					north.add(font(name, 20));
					size(jL, 250, 30);
					jL.setText(jL.getText()+", 대한민국");
					north.add(fl, "South");
					res.setList();
					res.name=name;
					res.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							setVisible(true);
						}
					});
					res.setVisible(true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});
			cen.setOpaque(false);
			img.setOpaque(false);
			east.setOpaque(false);
			fl.setOpaque(false);
			center.add(temp);
			cnt++;
		}
		return cnt;
	}

	private void setAction() {
	}
}
