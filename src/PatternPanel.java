import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PatternPanel extends JPanel{
	ArrayList<Integer> preNum=new ArrayList<>();
	int h, w, num=1, black=Color.BLACK.getRGB(), xPoints[]=new int[9], yPoints[]=new int[9], size=30;
	BufferedImage image;
	boolean re=false;

	public PatternPanel() {
		setLayout(new GridLayout(0, 3));
		for(int i=0; i<9; i++) {
			JLabel jL=new JLabel();
			jL.setBorder(new LineBorder(Color.gray));
			add(jL);
		}
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(preNum.size()>1) re=true;
			}
		});
		
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				if(re) return;
				num=(e.getY()/h)*3+e.getX()/w;
				boolean chk=true;
				int r=size/2;
				for(int i=0; i<preNum.size(); i++) if(preNum.get(i)==num) chk=false;
				if(e.getX()>=xPoints[num]-r && e.getX()<=xPoints[num]+r && e.getY()>=yPoints[num]-r && e.getY()<=yPoints[num]+r && chk) {
					preNum.add(num);
					repaint();
				}
			}
		});
	}
	

	String getPattern() {
		String pa="";
		for(int i=0; i<preNum.size(); i++) pa+=(preNum.get(i)+1);
		return pa;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		h=getHeight()/3;
		w=getWidth()/3;
		g.setColor(Color.black);
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++) {
				g.fillOval(w/2+i*w-size/2, h/2+j*h-size/2, size, size);
				xPoints[i+j*3]=w/2+i*w;
				yPoints[i+j*3]=h/2+j*h;
			}
		Graphics2D g2=(Graphics2D)g;
		g2.setStroke(new BasicStroke(7));
		g.setColor(Color.blue);
		if(num!=-1) {
			int xx[]=new int[preNum.size()], yy[]=new int[xx.length];
			for(int i=0; i<preNum.size(); i++) {
				xx[i]=xPoints[preNum.get(i)];
				yy[i]=yPoints[preNum.get(i)];
			}
			g.drawPolyline(xx, yy, preNum.size());
		}
	}
}
