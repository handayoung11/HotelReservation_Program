import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Pay extends BaseFrame{
	JPanel card[]=new JPanel[2], pay[]=new JPanel[2];
	JButton jB[]=new JButton[2];
	JRadioButton way[]=new JRadioButton[2];
	JTextField txt[]=new JTextField[6], pxt=new JTextField(15);
	String name;
	JComboBox bank=new JComboBox(new String[] {"����"});
	JPanel center=new JPanel(new GridLayout(0, 1));
	int chk=0;
	public Pay() {
		super("����", 500, 350, 2);
	}
	
	void setAction() {
		for(int i=0; i<2; i++)
			jB[i].addActionListener(it -> {
				if(it.getSource()==jB[1]) dispose();
				if(it.getSource()==jB[0]) {
					if(way[0].isSelected()) {
						if(pxt.getText().equals("")) {
							err_msg("������ �����մϴ�.");
							return;
						}
					}else {
						for(int j=0; j<6; j++) {
							if(txt[j].getText().equals("")) {
								err_msg("������ �����մϴ�.");
								return;
							}
						}
					}
					chk=1;
					execute("insert into list values(0, '"+ID+"', '"+name+"', '"+Hotel.start+"', '"+Hotel.end+"', "+adult+", "+child+", "+PAY+")");
					int yes=JOptionPane.showConfirmDialog(null, "������ �Ϸ�Ǿ����ϴ�.\n���೻���� Ȯ���Ͻðڽ��ϱ�?", "�޽���", JOptionPane.YES_NO_OPTION);
					if(yes==JOptionPane.YES_OPTION) {
						dispose();
						new ReservationList();
					}else {
						dispose();
						MAIN.setVisible(true);
					}
				}
			});
	}
	
	void setPanel(JPanel[] jP) {
		center.removeAll();
		String lab[]= {"�������", "��ǰ��", "�����ݾ�"};
		for(int i=0; i<3; i++) {
			JLabel jL;
			JPanel temp=new JPanel(new FlowLayout(0));
			temp.add(jL=new JLabel(lab[i], JLabel.RIGHT));
			size(jL, 100, 25);
			if(i==0) {
				temp.add(way[0]);
				temp.add(way[1]);
			}
			if(i==1) temp.add(new JLabel(name));
			if(i==2) temp.add(new JLabel(PAY+""));
			center.add(temp);
			temp.setOpaque(false);
		}
		center.add(jP[0]);
		center.add(jP[1]);
		center.setBackground(Color.cyan);
		add(center);
		repaint();
		revalidate();
	}
	
	void setDesign() {
		ButtonGroup gr=new ButtonGroup();
		way[0]=new JRadioButton("������ü");
		way[1]=new JRadioButton("ī��");
		way[0].setOpaque(false);
		way[1].setOpaque(false);
		way[0].setSelected(true);
		gr.add(way[0]);
		gr.add(way[1]);
		for(int i=0; i<2; i++)
			way[i].addActionListener(it -> {
				if(it.getSource()==way[0]) {
					setPanel(pay);
				}else setPanel(card);
			});
		JPanel south=new JPanel();
		String lbl[]= {"����", "���"};
		for(int i=0; i<2; i++) south.add(jB[i]=new JButton(lbl[i]));
		setCard();
		setPay();
		south.setBackground(Color.cyan);
		add(south, "South");
	}

	private void setPay() {
		String lab[]= {"�Ա�����", "�Ա��ڸ�"};
		for(int i=0; i<2; i++) {
			JLabel jL;
			pay[i]=new JPanel(new FlowLayout(0));
			pay[i].add(jL=new JLabel(lab[i], JLabel.RIGHT));
			size(jL, 100, 25);
			if(i==0) pay[i].add(bank); 
			if(i==1) pay[i].add(pxt);
			pay[i].setOpaque(false);
		}
	}

	private void setCard() {
		String lab[]= {"ī���ȣ", "��ȿ�Ⱓ"};
		for(int i=0; i<2; i++) {
			JLabel jL;
			card[i]=new JPanel(new FlowLayout(0));
			card[i].add(jL=new JLabel(lab[i], JLabel.RIGHT));
			size(jL, 100, 25);
			if(i==0) {
				for(int j=0; j<4; j++)
				card[i].add(txt[j]=new JTextField(6)); 
			}
			if(i==1) {
				for(int j=4; j<6; j++) card[i].add(txt[j]=new JTextField(6));
			}
			card[i].setOpaque(false);
		}
	}
}
