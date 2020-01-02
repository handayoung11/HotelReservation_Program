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
	JButton jB=new JButton("����");
	JLabel chk=font("", 20);
	PatternPanel pa[]=new PatternPanel[2];
	
	public RePW() {
		super("��й�ȣ �缳��", 600, 400, 2);
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
		String lbl[]= {"���� �Է�", "���� ��Ȯ��"};
		for(int i=0; i<2; i++) {
			center.add(pattern[i]=new JPanel(new BorderLayout()));
			pattern[i].add(font(lbl[i], 20), "North");
			pattern[i].add(pa[i]=new PatternPanel());
			pa[i].setBorder(new LineBorder(Color.blue, 3));
		}
		
		jB.addActionListener(it -> {
			chk.setText("�� ���ϰ� ���� ��Ȯ���� ��ġ���� �ʽ��ϴ�.");
			chk.setForeground(Color.red);
			if(pa[0].getPattern().equals(pa[1].getPattern())) {
				execute("update member set m_pw="+pa[0].getPattern()+" where m_no="+NO);
				chk.setText("�����ϰ� ���� ��Ȯ���� ��ġ�մϴ�.");
				chk.setForeground(Color.blue);
			}
		});
		size(jB, 120, 30);
	}
}
