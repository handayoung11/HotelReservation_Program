import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AddHotel extends BaseFrame{
	JButton jB[]=new JButton[3];
	JLabel img=new JLabel();
	JTextField txt[]=new JTextField[3];
	Star star=new Star();
	JFileChooser choose=new JFileChooser();
	
	public AddHotel() {
		super("호텔 등록", 350, 500, 2);
		setLayout(new BorderLayout(0, 10));
		setDesign();
		setAction();
		setVisible(true);
	}
	
	void setDesign() {
		((JPanel)getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel center=new JPanel(new GridLayout(0, 1, 0, 10)), south=new JPanel(new GridLayout(1, 0, 10, 0));
		add(img, "North");
		size(img, 300, 150);
		img.setBorder(new LineBorder(Color.black, 2));
		center.add(jB[0]=new JButton("업로드"));
		String lbl[]= {"호텔명", "주소", "1박 요금", "등급"};
		for(int i=0; i<lbl.length; i++) {
			JPanel temp=new JPanel(new FlowLayout(0));
			temp.add(new JLabel(lbl[i]));
			if(i<3) temp.add(txt[i]=new JTextField());
			else temp.add(star);
			center.add(temp);
		}
		size(star, 200, 40);
		add(center);
		add(south, "South");
		south.add(jB[1]=new JButton("등록"));
		south.add(jB[2]=new JButton("취소"));
		for(int i=0; i<3; i++) {
			jB[i].setBackground(Color.LIGHT_GRAY);
			size(txt[i], i==1?280:250, 25);
		}
	}
	
	void setAction() {
		for(int i=0; i<3; i++) {
			jB[i].addActionListener(it -> {
				if(it.getSource()==jB[2]) dispose();
				if(it.getSource()==jB[0]) {
					choose.setFileFilter(new FileNameExtensionFilter("JPG Image", "jpg"));
					if(choose.showOpenDialog(null)!=choose.APPROVE_OPTION) return;
					img.setIcon(img(choose.getSelectedFile().getAbsolutePath(), 320, 150));
				}
				if(it.getSource()==jB[1]) {
					for(int j=0; j<3; j++)
						if(txt[j].getText().equals("")) {
							err_msg("공백이 존재합니다.");
							return;
						}
					if(img.getIcon()==null) {
						err_msg("사진을 선택해주세요.");
						return;
					}
					try {
						ResultSet rs=stmt.executeQuery("select * from hotel where h_name='"+txt[0].getText()+"'");
						if(rs.next()) {
							err_msg("이미 존재하는 호텔명입니다.");
							return;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(!txt[2].getText().matches("\\d*")) {
						err_msg("요금은 숫자로 입력해주세요.");
						return;
					}
					if(star.getValue()==0) {
						err_msg("등급을 선택해주세요.");
						return;
					}
					try {
						stmt.execute("insert into hotel values(0, '"+txt[0].getText()+"', '"+txt[1].getText()+"', "+star.getValue()+", "+txt[2].getText()+")");
						File file=new File(PATH+"Hotel/"+txt[0].getText()+".jpg");
						ImageIO.write(ImageIO.read(choose.getSelectedFile()), "jpg", file);
						msg("호텔이 등록되었습니다.");
					} catch (Exception e) {
						e.printStackTrace();
					}
					dispose();
				}
			});
		}
	}
}
