import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class RePW extends BaseFrame{
	JButton jB=new JButton("수정");
	JLabel chk=font("", 20);
	PatternPanel pa[]=new PatternPanel[2];
	
	public RePW() {
		super("비밀번호 재설정", 600, 400, 2);
		setDesign();
		setVisible(true);
	}
	
	void setDesign() {
		setLayout(new BorderLayout(0, 10));
		((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 20));
		JPanel south=new JPanel(new BorderLayout()), center=new JPanel(new GridLayout(1, 0, 20, 0)), pattern[]=new JPanel[2];
		south.add(chk);
		south.add(jB, "East");
		size(chk, 420, 30);
		add(south, "South");
		add(center, "Center");
		String lbl[]= {"패턴 입력", "패턴 재확인"};
		for(int i=0; i<2; i++) {
			center.add(pattern[i]=new JPanel(new BorderLayout()));
			pattern[i].add(font(lbl[i], 20), "North");
			pattern[i].add(pa[i]=new PatternPanel());
			pa[i].setBorder(new LineBorder(Color.blue, 3));
		}
		
		jB.addActionListener(it -> {
			chk.setText("새 패턴과 패턴 재확인이 일치하지 않습니다.");
			chk.setForeground(Color.red);
			if(pa[0].getPattern().equals(pa[1].getPattern())) {
				execute("update member set m_pw="+pa[0].getPattern()+" where m_no="+NO);
				chk.setText("새패턴과 패턴 재확인이 일치합니다.");
				chk.setForeground(Color.blue);
			}
		});
		size(jB, 120, 30);
	}
}
