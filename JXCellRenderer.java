package nl.hz.ict.ding0033.jxplorer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Dit is de klasse die de Listcelrenderer beheert.
 * 
 * @author Mark Dingemanse
 * @version 0,1 Concept
 */
class JXCellRenderer extends JLabel implements ListCellRenderer<Object> {

	private static final long serialVersionUID = 1L;
	// zet de color voor het JLabel wanneer selected in mijn geval wanneer hover
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	// de constructor voor de cellrenderer en zet een aantal default waarden
	public JXCellRenderer() {
		setOpaque(true);
		setIconTextGap(12);
	}

	// zet voor elk object een naam en icon in een JLabel daarnaast wordt er gekeken of de entry geselecteerd is of niet
	// tot slot word het huidige object gereturned.
	public Component getListCellRendererComponent(JList<?> list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		JXploreFile entry = (JXploreFile) value;
		setText(entry.getName());
		setIcon(entry.getIcon());

		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}
}
