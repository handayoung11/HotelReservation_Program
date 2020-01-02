import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Ticket extends BaseFrame{
	Object info[];
	JLayeredPane layer=new JLayeredPane();
	
	public Ticket(Object info[]) {
		super("티켓", 750, 300, 2);
		this.info=info;
		setDesign();
		setVisible(true);
	}
	
	void setDesign() {
		JPanel jP=new JPanel(new BorderLayout()), jP2=new JPanel(new BorderLayout()), in=new JPanel(new GridLayout(0, 1));
		jP.add(font("YA NULJA TICKET", 30));
		JLabel jL=font("￦"+info[0], 30, JLabel.RIGHT);
		jL.setForeground(Color.blue);
		jP.add(jL, "South");
		jP.setOpaque(false);
		bound(jP2, layer, 510, 50, 220, 200);
		bound(jP, layer, 100, 0, 380, 180);
		bound(new JLabel(img(PATH+"티켓.jpg", 740, 260)), layer, 0, 0, 740, 260);
		jP2.add(new JLabel(img(PATH+"Hotel/"+info[1]+".jpg", 150, 100)), "North");
		jP2.add(in);
		in.add(font(info[1].toString(), 15, JLabel.CENTER));
		in.add(font("[ 성인 " +info[2]+"명 어린이 "+info[3]+"명 ]".toString(), 15, JLabel.CENTER));
		in.add(font(info[4].toString()+" ~ "+info[5], 15, JLabel.CENTER));
		jP2.setOpaque(false);
		add(layer);
	}
}
