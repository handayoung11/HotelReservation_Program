import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class BaseFrame extends JFrame{
	
	static Connection con;
	static Statement stmt;
	static String ID="", PATH="./제2과제 지급자료/DataFiles/Image/";
	static int NO=0;
	static final Preferences PRE=Preferences.userNodeForPackage(BaseFrame.class);
	static final SimpleDateFormat date=new SimpleDateFormat("yyyy-MM-dd");
	static int PAY=0, adult=0, child=0;
	static Main MAIN;
	
	static {
		try {
			date.setLenient(false);
			con=DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC", "user", "1234");
			stmt=con.createStatement();
			execute("use hotel");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void bound(Component c, Container box, int x, int y, int w, int h) {
		c.setBounds(x, y, w, h);
		box.add(c);
	}
	
	void size(JComponent c, int w, int h) {
		c.setPreferredSize(new Dimension(w, h));
	}
	
	BaseFrame(String title, int w, int h, int operation) {
		super(title);
		setSize(w, h);
		setDefaultCloseOperation(operation);
		setLayout(new BorderLayout());
		setLocationRelativeTo(null);
	}
	
	static JLabel font(String lbl, int size) {
		return font(lbl, size, JLabel.LEFT);
	}
	
	static void err_msg(String msg, int a) {
		UIManager.put("OptionPane.okButtonText", "확인");
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.ERROR_MESSAGE);
		UIManager.put("OptionPane.okButtonText", "OK");
	}
	
	static void err_msg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.ERROR_MESSAGE);
	}
	
	static void msg(String msg) {
		JOptionPane.showMessageDialog(null, msg, "메시지", JOptionPane.INFORMATION_MESSAGE);
	}
	
	static JLabel font(String lbl, int size, int alig) {
		JLabel jL=new JLabel(lbl, alig);
		jL.setFont(new Font("", Font.BOLD, size));
		return jL;
	}
	
	static void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ImageIcon img(String path, int w, int h) {
		try {
			return new ImageIcon(ImageIO.read(new File(path)).getScaledInstance(w, h, Image.SCALE_SMOOTH));
		} catch (IOException e) {
			System.out.println(path);
			return null;
		}
	}
	
	int toint(String str) {
		return Integer.parseInt(str);
	}
	
	public static void main(String[] args) {
		new Login().setVisible(true);
	}
}
