import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Chart extends BaseFrame{

	public Chart() {
		super("예약현황", 1000, 550, 2);
		setDesign();
		setVisible(true);
	}

	void setDesign() {
		JPanel south=new JPanel();
		add(font("일짜별 호텔 예약현황", 15), "North");
		((JPanel)getContentPane()).setBackground(Color.cyan);
		((JPanel)getContentPane()).setBorder(new EmptyBorder(20, 5, 20, 10));
		drawChart();
	}

	void drawChart() {
		int max=0;
		JPanel chart=new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				try {
					int h=400;
					ResultSet rs=stmt.executeQuery("select count(*), l_checkin from list group by l_checkin order by l_checkin desc limit 10");
					Graphics2D g2=(Graphics2D)g;
					g2.setStroke(new BasicStroke(4));
					g.drawRect(50, 0, 900, 400);
					int max=0, cnt=0;
					while(rs.next()) max=(int)Math.max(rs.getInt(1), max);
					double yunit=(double)h/max;
					rs.afterLast();
					g.setFont(new Font("", Font.BOLD, 15));
					while(rs.previous()) {
						g.setColor(Color.YELLOW);
						g.fillRect(80+cnt*getWidth()/11, h-rs.getInt(1)*(int)yunit, 30, rs.getInt(1)*(int)yunit);
						g.setColor(Color.BLACK);
						g.drawString(rs.getString(2), 60+cnt*getWidth()/11, getHeight()-20);
						cnt++;
					}
					cnt=0;
					h=getHeight()-80;
					for(int i=max; i>=0; i--, cnt++) g.drawString(i+"", 0, 20+cnt*h/max);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		};
		chart.setOpaque(false);
		add(chart);
	}
}
