import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Star extends JPanel{
	JLabel jL[]=new JLabel[5];
	boolean clicked=false;
	String str="¡Ú";
	
	Star(){
		setLayout(new FlowLayout(0, 0, 0));
		for(int i=0; i<5; i++) {
			jL[i]=BaseFrame.font("¡Ù", 26);
			jL[i].setFont(new Font("HY°ß°íµñ", Font.BOLD, 26));
			jL[i].setForeground(Color.orange);
			jL[i].setVerticalAlignment(JLabel.TOP);
			add(jL[i]);
			jL[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(((JLabel)e.getSource()).getText().equals("¢½")) str="¢¾";
					((JLabel)e.getSource()).setText(str);
					clicked=true;
				};
				@Override
				public void mouseReleased(MouseEvent e) {
					clicked=false;
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					if(clicked) ((JLabel)e.getSource()).setText(str);
				}
			});
		}
	}
	
	int getValue() {
		var cnt=0;
		for(int i=0; i<5; i++) if(jL[i].getText().equals(str)) cnt++;
		return cnt;
	}
}
