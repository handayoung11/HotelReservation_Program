import java.awt.BorderLayout;

public class Pattern extends BaseFrame{
	PatternPanel pattern=new PatternPanel();
	
	public Pattern() {
		super("", 400, 400, 2);
		add(pattern);
		setVisible(true);
	}
}
