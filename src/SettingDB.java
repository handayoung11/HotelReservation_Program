import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SettingDB {
	
	static Connection con;
	static Statement stmt;
	
	public SettingDB() {
		try {
			con=DriverManager.getConnection("jdbc:mysql://localhost/?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234");
			stmt=con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cDB();
		cTable("member", "m_no int primary key not null auto_increment, m_id varchar(10), m_name varchar(10), m_pw int, m_birth date, m_phone varchar(20)");
		cTable("hotel", "h_no int primary key not null auto_increment, h_name varchar(30), h_address varchar(30), h_grade int, h_tax int");
		cTable("review", "r_no int primary key not null auto_increment, rh_no int, rh_id varchar(30), r_review varchar(30), rh_grade int, rh_date date");
		cTable("wishlist", "w_no int primary key not null auto_increment, w_id varchar(30), wh_no int");
		cTable("list", "l_no int primary key not null auto_increment, l_id varchar(20), l_name varchar(30), l_checkin varchar(10), l_checkout varchar(10), l_adult int, l_child int, l_total int");
	}
	
	void cTable(String table, String column) {
		execute("create table "+table+"("+column+")");
		execute("load data local infile './제2과제 지급자료/DataFiles/"+table+".txt' into table "+table+" ignore 1 lines");
	}
	
	void cDB() {
		execute("drop database if exists Hotel");
		execute("create database Hotel default character set utf8");
		execute("drop user if exists user@localhost");
		execute("create user user@localhost identified by '1234'");
		execute("grant select, update, delete, insert on hotel.* to user@localhost");
		execute("set global local_infile=1");
		execute("use hotel");
	}
	
	static void execute(String sql) {
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new SettingDB();
	}
}
