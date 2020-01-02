import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ReservationList extends BaseFrame{
	DefaultTableModel model=new DefaultTableModel(null, new String[] {"호텔명", "호텔등급", "체크인", "체크아웃", "성인인원", "유아인원", "총가격"}) {
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	JTable table=new JTable(model);
	
	public ReservationList() {
		super("예약내역", 700, 150, 2);
		setDesign();
		addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				MAIN.setVisible(true);
			};
		});
		setVisible(true);
	}
	
	void setDesign() {
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(new JScrollPane(table));
		try {
			Object row[]=new Object[7];
			ResultSet rs=stmt.executeQuery("select h_name, h_grade, l_checkin, l_checkout, l_adult, l_child, format(l_total, 0) from list l inner join hotel h on h.h_name=l.l_name where l_id='"+ID+"' and l_checkin>=date(now())");
			while(rs.next()) {
				for(int i=0; i<row.length; i++) row[i]=rs.getString(i+1);
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(0).setMinWidth(200);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Object[] info=new Object[6];
				int row=table.getSelectedRow();
				info[0]=table.getValueAt(row, 6);
				info[1]=table.getValueAt(row, 0);
				info[2]=table.getValueAt(row, 4);
				info[3]=table.getValueAt(row, 5);
				info[4]=table.getValueAt(row, 2);
				info[5]=table.getValueAt(row, 3);
				new Ticket(info);
			}
		});
	}
}
