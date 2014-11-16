package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Dit is de klasse die een JXplorer representeerd en zaken kan afdrukken, build/creeeren en innitialiseren voor de files
 * 
 * @author Mark Dingemanse
 * @version 0,1 Concept
 * 
 */
public class JXplorer extends JFrame {
	
	
	private static final long serialVersionUID = 1L;	
	// maakt een veld aan dat een JXplorefile bezit
	private JXploreFile currentFile;
	// maakt een veld ArrayList van JXploreView componeneten
	private ArrayList<JXploreView> components;
	// maakt een veld aan met een stack van JXploreFile objecten 
	private Stack<JXploreFile> forward;
	// maakt een veld aan met een stack van JXploreFile objecten 
	private Stack<JXploreFile> backward;
	


	/**
	 * met deze methode wordt het programma geinitialiseerd en wordt een JXplorer aangemaakt en de testmethode
	 * wotdt uitgevoerd
	 */
	public static void main(String[] args) {
		JXplorer app = new JXplorer();
		app.buildGUI();

	}

	/**
	 * met deze contructor wordt een JXplorer geinitialiseerd en wordt er een testfile aangemaakt
	 * en wordt deze als currentfile gezet. Verder worden de overige velden geinitialiseerd
	 */
	public JXplorer() {
		// maakt een currentFile aan de hand van de systemRoot
		currentFile = new JXploreFile();
		components = new ArrayList<JXploreView>();
		forward = new Stack<JXploreFile>();
		backward = new Stack<JXploreFile>();
	}
	
	/**
	 * met deze contructor wordt een JXplorer geinitialiseerd
	 * @param file steld een jxplorefile voor die als currentfile wordt gezet
	 */
	public JXplorer(JXploreFile file) {
		currentFile = file;
	}
	
	private void buildGUI() {		
		// zorgt ervoor dat kruisje werkt
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// set look en feel van het frame
		JFrame.setDefaultLookAndFeelDecorated(true);
		//maakt nieuw addresview aan en geeft hem de JXplorer mee
		JXAddressView addresView = new JXAddressView(this);
		// voegt de addresView toe aan de Component lijst
		addComponents(addresView);
		// maakt een JXListView object aan en geeft hem de JXplorer mee
		JXListView list = new JXListView(this);		
		// voegt de listView toe aan de Component lijst
		addComponents(list);		
		// maakt een JXStatusview aan en geeft hem de JXplorer mee
		JXStatusView statusView = new JXStatusView(this);
		// voegt de statusView toe aan de Component lijst
		addComponents(statusView);
		// maakt een JXToolBar object aan en geeft hem de JXplorer mee
		JXToolBar bar = new JXToolBar(this);
		// voegt de JXToolBar toe aan de Component lijst
		addComponents(bar);		
		// maakt een nieuw JTreeView object aan en voegt deze toe aan de tree variabele
		JXTreeView tree = new JXTreeView(this);
		// voegt de JTreeView toe aan de Component lijst		
		addComponents(tree);		
		// maakt een Splitpane aan en split deze horizontaal
	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
	    // voegt de tree toe aan de linkerkant van de Splitpane
	    splitPane.setLeftComponent(tree);
	    // voegt de lijst toe aan de rechterkant van de Splitpane
	    splitPane.setRightComponent(list);
	    // maakt een JPanel aan dat het noordelijke hoofdpannel wordt
	    JPanel topPanel = new JPanel(new BorderLayout());
	    // Voegt Toolbar toe noordelijk in de noordelijke JPanel
	    topPanel.add(bar, BorderLayout.NORTH);
	    // Voegt de addresView zuidelijk toe aan de noordelijke bar
	    topPanel.add(addresView, BorderLayout.SOUTH);   
	    //Maakt een Jpanel aan dat dient als de opperPanel
	    JPanel finalPanel = new JPanel(new BorderLayout());
	    // voegt de Splitpane toe aan de opperpanel gecentreerd
	    finalPanel.add(splitPane, BorderLayout.CENTER);
	    // voegt de addresview toe aan de opperpanel aan de boverkant
	    finalPanel.add(topPanel, BorderLayout.NORTH);
	    //voegt de underbar toe aan de opperpanel aan de onderkant
	    finalPanel.add(statusView, BorderLayout.SOUTH);	    
	    // voegt het opperpanel toe aan het JFrame
	    this.setContentPane(finalPanel);
  
	    // zorgt ervoor dat de GUI Systen defaults aanneemt
	 	try {
	 		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	 	} catch (ClassNotFoundException e) {
	 		e.printStackTrace();
	 	} catch (InstantiationException e) {
	 		e.printStackTrace();
	 	} catch (IllegalAccessException e) {
	 		e.printStackTrace();
	 	} catch (UnsupportedLookAndFeelException e) {
	 		e.printStackTrace();
	 	}
	 	SwingUtilities.updateComponentTreeUI(this);
	    
	 	// zet een automatische groote voor het frame
	    this.pack();
	    // maakt het frame zichtbaar
	    this.setVisible(true);				    
	}

	// de getter voor de currentfile
	public JXploreFile getCurrentFile() {
		return currentFile;
	}

	// de setter voor de currentfile
	public void setCurrentFile(JXploreFile currentFile) {		
		addBackward(getCurrentFile());
		this.currentFile = currentFile;
		updateGUI();
	}
	
	// voegt eerst de huidige currentFile in de backward stack 
	// daarna wordt de currentfile vervangen en wordt de gui geupdated
	public void setCurrentFile(String naam) {
		addBackward(getCurrentFile());
		this.currentFile = new JXploreFile(naam);
		updateGUI();
	}
	
	private void setCurrentFileFromBack(String naam){
		this.currentFile = new JXploreFile(naam);
		updateGUI();
	}
	
	// roept de clearcache methode aan in JXplorer
	public void clearCache(){
		currentFile.clearCache();
		
	}
			
	// de algemene methode voor het aanmaken van een message de algemene methode verwacht een JXploreFile en return de 
	// benodigde file informatie om een boxje aan te maken in
	public String generateFileMessage(JXploreFile file) {	
		String FileMessage = "name:              "
				+ file.getName() + "\n" + "file path:         "
				+ file.getPath() + "\n" + "file extension: "
				+ file.getExtension(file.getName()) + "\n"
				+ "file size in KB:  " + file.getFileSize() + "\n"
				+ "file datum:      " + file.getDate() + "\n";
		return FileMessage;
	}
	
	// maakt een popupje aan met de filemessage die hij meekrijgt
	public void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	// returned de root van het systeem in een JXploreFile
	public JXploreFile getRoot(){
		return new JXploreFile();
	}
	
	// voegt een component toe aan de JXploreView arrayList
	private void addComponents(JXploreView comp){
		components.add(comp);
	}
	
	// roept de updateGui() methode aan voor alle componenten
	private void updateGUI(){
		for (JXploreView comp : components){
			comp.updateGUI();
			
		}
				
	}
	
	// de engine voor het verwerken van acties in je toolbar 
	public void processButtonAction(ActionEvent actie, JButton refresh, JButton next, JButton back) {	

		// wanneer de user op de refresh knop klikt refreshed de cache
		if(actie.getSource().equals(refresh)){			
			clearCache();
		} 
		
		// wanneer de user op de back knop klikt gaat het scherm 1 stap terug
		if(actie.getSource().equals(back)){
			if( backward.isEmpty() == false){
				addForward(getCurrentFile());
				setCurrentFileFromBack(this.backward.pop().getPath());
				return;
			}
		}
		// wanneer de user op de next knop klikt gaat het scherm 1 stap verder
		if(actie.getSource().equals(next)){
			if(this.forward.isEmpty() == false){
				addBackward(getCurrentFile());
				setCurrentFile(this.forward.pop().getPath());				
			}		
		}
	}
	
	// voegt een JXplorefile toe aan de forward stack
	private void addForward(JXploreFile file){
		forward.add(file);
	}
	
	// voegt een JXplorefile toe aan de backward stack
	private void addBackward(JXploreFile file){
		backward.add(file);
	}
	
	//cleart de forward stack
	public void clearForward(){
		this.forward.clear();
	}
	
}





