package nl.hz.ict.ding0033.jxplorer;

import javax.swing.JPanel;

/**
 * de supperklasse voor alle gui onderdelen die het frame moet gaan krijgen onder zich heeft
 * @author mark
 * 
 */
public abstract class JXploreView extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// maakt een veld aan dat een JXplorer object in zich krijgt dat gaat helpen bij de View klassen.
	// ook hoeft het object nu dan maar 1 keer opgeslagen te worden.
	private JXplorer data;

	// de constructor voor de JXploreview klasse
	public JXploreView() {		
	}

	// Een getter voor het data veld dat alleen deze klasse en alle subklassen mag gaan gebruiken
	protected JXplorer getData() {
		return data;
	}
	
	// een Setter voor het data veld dat alleen deze klasse en alle subklassen mag gaan gebruiken
	protected void setData(JXplorer data) {
		this.data = data;
	}
	
	/**
	 * De methode die voor de eerste ronde de GUI moet gaan opbouwen
	 */
	protected abstract void buildGUI();
	
	/**
	 * De methode die de GUI update op basis van een verrandering in bijv de currentFile. Wordt
	 * dus aangeropen wanneer alles al een keer is geinitialiseerd
	 */
	protected abstract void updateGUI();
	

}