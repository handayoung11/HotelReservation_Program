import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CalForm extends BaseFrame{
	JTextField day;
	JLabel day2;
	LocalDate date=LocalDate.now();
	JLabel jL[]=new JLabel[3];
	ArrayList<JLabel> arr=new ArrayList<>();
	JPanel cal=new JPanel(new GridLayout(0, 7));
	boolean click=false;
	int start=0;
	
	public CalForm(JTextField day, JLabel day2) {
		super("달력", 300, 300, 2);
		this.day=day;
		this.day2=day2;
		setDesign();
		setAction();
		setVisible(true);
	}
	
	void setAction() {
		for(int i=0; i<3; i++)
			jL[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if(e.getSource()==jL[0]) date=date.plusMonths(-1);
					if(e.getSource()==jL[2]) date=date.plusMonths(1);
					drawCal();
				}
			});
	}
	
	void setDesign() {
		String lab[]= {"◀", "", "▶"};
		JPanel north=new JPanel();
		for(int i=0; i<3; i++) north.add(jL[i]=font(lab[i], 20));
		drawCal();
		add(north, "North");
		add(cal);
	}
	
	void drawCal() {
		String lbl[]= {"일", "월", "화", "수", "목", "금", "토"};
		
		cal.removeAll();
		jL[1].setText(date.getYear()+" 년 "+date.getMonthValue()+" 월");
		for(int i=0; i<7; i++) {
			JLabel jL;
			cal.add(jL=new JLabel(lbl[i], JLabel.CENTER));
			if(i==0) jL.setForeground(Color.red);
			if(i==6) jL.setForeground(Color.blue);
		}
		int offset=date.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue(), dayCnt=1, last=date.with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		if(offset==7) offset=0;
		for(int i=0; i<42; i++) {
			if(i<offset || dayCnt>last) {
				cal.add(new JLabel());
				continue;
			}
			JLabel jL=new JLabel(dayCnt+"", JLabel.CENTER);
			cal.add(jL);
			dayCnt++;
			jL.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					LocalDate selected=date.withDayOfMonth(toint(((JLabel)(e.getSource())).getText()));
					if(selected.isBefore(LocalDate.now().plusDays(1))) {
						err_msg("이전 날자는 선택할 수 없습니다.");
						click=false;
						return;
					}
					for(int i=0; i<arr.size(); i++) arr.get(i).setBackground(cal.getBackground());
					start=toint(((JLabel)e.getSource()).getText())-1;
					((JLabel)e.getSource()).setBackground(Color.YELLOW);
					click=true;
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					click=false;
					int cnt=-1;
					String str="";
					LocalDate start=null;
					for(int i=0; i<arr.size(); i++) {
						if(arr.get(i).getBackground().equals(Color.yellow)) {
							if(cnt==-1) {
								start=date.withDayOfMonth(toint(arr.get(i).getText()));
								str=start.format(DateTimeFormatter.ofPattern("yyyy.M.dd"));
							}
							cnt++;
						}
					}
					str+="~"+start.withDayOfMonth(start.getDayOfMonth()+cnt).format(DateTimeFormatter.ofPattern("yyyy.M.dd"));;
					day.setText(str);
					String[] split=day.getText().split("~"), date=split[0].split("\\."), date2=split[1].split("\\.");
					int dif=toint(date2[2])-toint(date[2]);
					day2.setText("["+dif+"박"+(dif+1)+"일]");
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if(!click) return;
					LocalDate selected=date.withDayOfMonth(toint(((JLabel)(e.getSource())).getText()));
					if(selected.isBefore(LocalDate.now().plusDays(1))) {
						err_msg("이전 날자는 선택할 수 없습니다.");
						return;
					}
					JLabel lbl=((JLabel)e.getSource());
					lbl.setBackground(Color.YELLOW);
					int se=toint(lbl.getText())-1;
					int max=(int)Math.max(start,se);
					for(int j=max==se?start:se; j<max; j++) {
						arr.get(j).setBackground(Color.yellow);
					}
				}
			});
			jL.setOpaque(true);
			arr.add(jL);
		}
	}
}
