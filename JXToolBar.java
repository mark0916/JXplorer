package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * de klasse voor het aanmaken en aanpassen van de toolbar
 * 
 * @author mark
 * 
 */
public class JXToolBar extends JXploreView implements ActionListener {

	private static final long serialVersionUID = 1L;
	// maakt een veld aan dat de back button opslaat
	private JButton back;
	// maakt een veld aan dat de next button opslaat
	private JButton next;
	// maakt een veld aan dat de refresh butten opslaat
	private JButton refresh;
	
	// de construcor zet eerst de JXplorer vast en maakt daarna de GUI aan.
	public JXToolBar(JXplorer data){
		this.setData(data);
		buildGUI();
	}

	@Override
	protected void buildGUI() {
		// zet de Layout a;s BorderLayout
		this.setLayout(new BorderLayout());
		// Maakt een JToolbar aan 
		JToolBar bar = new JToolBar();
		// zorgt ervoor dat de JToolbar niet verplaatst kan worden
		bar.setFloatable(false);
		
		// maakt het next icontje aan
		ImageIcon iconnext = new ImageIcon("src/nl/hz/ict/ding0033/jxplorer/resources/57.png");
		// maakt het back icontje aan
		ImageIcon iconback = new ImageIcon("src/nl/hz/ict/ding0033/jxplorer/resources/56.png");
		// maakt het refresh icontje aan 
		ImageIcon iconrefresh = new ImageIcon("src/nl/hz/ict/ding0033/jxplorer/resources/42a.png");
		
		// maakt een vorige JButton met het desbetreffende icontje
		JButton vorige = new JButton(iconback);		
		// maakt een volgende JButton met het desbetreffende icontje
		JButton volgende = new JButton(iconnext);
		// maakt een ververs JButton met het desbetreffende icontje
		JButton ververs = new JButton(iconrefresh); 
	
		// add een actionlissener op alle buttons in de toolbar
		vorige.addActionListener(this);
		volgende.addActionListener(this);
		ververs.addActionListener(this);
		
		// voeg alle buttons toe aan de velden
		back = vorige;
		next = volgende;
		refresh = ververs;
		// voeg alle buttons toe aan de bar
		bar.add(back);
		bar.add(next);
		bar.add(refresh);
		// voeg de Toolbar toe aan het JPannel
		this.add(bar);
	}

	@Override
	protected void updateGUI() {
		// voor nu nog niet nodig
	}

	// deze methode stuurt de actie door naar de JXplore klasse die de actie afhandeld. 
	// Hij krijgt alle buttons en actie mee om uit te kunnen zoeken wat er gedaan moet worden.
	@Override
	public void actionPerformed(ActionEvent actie) {
		this.getData().processButtonAction(actie, refresh, next, back);
		
	}


	

	
	
}
