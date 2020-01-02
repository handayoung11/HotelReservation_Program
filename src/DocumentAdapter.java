import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

abstract class DocumentAdapter implements DocumentListener {

	@Override
	public void insertUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		update(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		update(e);
	}
	
	abstract void update(DocumentEvent e);
}
