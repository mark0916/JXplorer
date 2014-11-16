package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;

import javax.swing.JLabel;

/**
 * de klasse voor het aanmaken en aanpassen van de statusView
 * 
 * @author mark
 * 
 */
public class JXStatusView extends JXploreView {

	private static final long serialVersionUID = 1L;
	// Maakt een veld aan dat een JLabel opslaat waarin de status komt te staan
	private JLabel label;

	// de construcor zet eerst de JXplorer vast en maakt daarna de GUI aan.
	public JXStatusView(JXplorer data) {
		this.setData(data);
		buildGUI();
	}

	@Override
	protected void buildGUI() {
		// zet de layout van de statusview op een BorderLayout
		this.setLayout(new BorderLayout());
		// maakt de label aan op basis van een message
		label = new JLabel(this.getMessage());
		// voegt een JLabel toe aan de underbar die informatie weergeeft over de huidige currentFile
		this.add(label);
	}

	@Override
	protected void updateGUI() {
		// veranderd de message in het label
		label.setText(this.getMessage());
	}

	// deze methode genereerd een message die te zien is in een JLabel
	private String getMessage() {
		return this.getData().getCurrentFile().getPath() + " ~~~ You are currently looking in this directory";
	}

}
