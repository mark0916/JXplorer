package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;


/**
 * Dit is de klasse die een JXplorefile representeerd die verschillende aspecten
 * van de files beheert
 * 
 * 
 * ik heb ervoor gekozen om de volgende klassen te importeren om de GUI op te
 * bouwen
 * 
 * @author Mark Dingemanse
 * @version 0,1 Concept
 */
public class JXAddressView extends JXploreView implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel addresLabel;
	private JTextField addresTextField;
	private JButton goButton;

	// de construcor zet eerst de JXplorer vast en maakt daarna de GUI aan.
	public JXAddressView(JXplorer data) {		
		this.setData(data);
		buildGUI();
	}

	// deze methode is verantwoordelijk voor het aanmaken van de GUI
	@Override
	protected void buildGUI() {

		// maakt een JLabel aan en stopt deze in addresLabel
		addresLabel = new JLabel("Address");
		// wanneer de currentfile een directory is zet de pathnaam en wanneer het een file is zet de perant in het textveld
		if (this.getData().getCurrentFile().getFile().isDirectory() == true){
				addresTextField = new JTextField(this.getData().getCurrentFile().getPath(), 40);
			}
		else{
				addresTextField = new JTextField(this.getData().getCurrentFile().getFile().getParent(), 40);			
		}
		

		ImageIcon icon = new ImageIcon("src/nl/hz/ict/ding0033/jxplorer/resources/65.png"); 
		// maakt een nieuw JButton aan en zet deze in de goButton
		goButton = new JButton("go",icon);

		// voeg een actionllissener toe aan de button en geeft het huidige object mee
		goButton.addActionListener(this);
		// zet ook een lissener op het textveld zodat je op enter kan klikken
		addresTextField.addActionListener(this);
		// zet de layout als BorderLayout
		this.setLayout(new BorderLayout());
		
		
		
		// zet de size voor het JPannel
		this.setPreferredSize(new Dimension(640, 30));
		//voeg alle onderdelen toe aan het pannel
		this.add(addresLabel, BorderLayout.LINE_START);
		this.add(addresTextField, BorderLayout.CENTER);
		this.add(goButton, BorderLayout.LINE_END);

	}
	
	@Override
	protected void updateGUI(){

		if(this.getData().getCurrentFile().getFile().isDirectory() != true ){
			addresTextField.setText(this.getData().getCurrentFile().getFile().getParent());
		}
		else{
			addresTextField.setText(this.getData().getCurrentFile().getFile().getAbsolutePath());
		}
	}
	
	// faciliteerd de go knop en geeft foutmelding met invallid path
	public void actionPerformed(ActionEvent actie) {
		if(!addresTextField.getText().equals(this.getData().getCurrentFile().getPath())){		
		this.getData().clearForward();
		// get het textveld
		this.getData().setCurrentFile(addresTextField.getText());
			// check of we een geen directory zien of niet
			if (this.getData().getCurrentFile().getFile().isDirectory() == false) {
				//  bestaad het?
				if(this.getData().getCurrentFile().getFile().exists() == true){
					this.getData().setCurrentFile(this.getData().getCurrentFile().getFile().getParent());
					this.getData().showMessageDialog(fileMessage());


				}
				// als het niet bestaad
				else{	
					this.getData().showMessageDialog("invalid pathname");
					return;
				}
				
				
			}
			// als het een directory is
			else{
				this.getData().setCurrentFile(this.getData().getCurrentFile());
			}
			
		}
	}

	// haalt het filemessage op
	private String fileMessage() {
		return this.getData().generateFileMessage(this.getData().getCurrentFile());
	}

}
