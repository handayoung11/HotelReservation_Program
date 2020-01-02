import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;

public class Admin extends BaseFrame{

	public Admin() {
		super("관리자", 250, 250, 2);
		setLayout(new GridLayout(0, 1));
		JButton jB[]=new JButton[3];
		String lab[]= {"예약현황", "호텔등록", "로그아웃"};
		for(int i=0; i<3; i++) {
			add(jB[i]=new JButton(lab[i]));
			jB[i].addActionListener(it -> {
				if(it.getSource()==jB[2]) dispose();
				if(it.getSource()==jB[0]) new Chart();
				if(it.getSource()==jB[1]) {
					new AddHotel().addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							setVisible(true);
						}
					});
					setVisible(false);
				}
			});
		}
		setVisible(true);
	}
}
