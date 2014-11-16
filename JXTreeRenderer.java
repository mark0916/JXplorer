package nl.hz.ict.ding0033.jxplorer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 * Dit is de klasse die de TreeCellRenderer beheert.
 * 
 * @author Mark Dingemanse
 * @version 0,1 Concept
 */
public class JXTreeRenderer extends JLabel implements TreeCellRenderer {

	private static final long serialVersionUID = 1L;
	// zet de color voor het JLabel wanneer selected in mijn geval wanneer hover
	private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);
	
	// de constructor voor de TreeCellrenderer en zet een aantal default waarden
	public JXTreeRenderer(){
	    setOpaque(true);
	    setIconTextGap(6);	
	}
	
	// zet voor elk object een naam en icon in een JLabel daarnaast wordt er gekeken of de entry geselecteerd is of niet
	// tot slot word het huidige object gereturned.
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,boolean selected, boolean expanded, boolean leaf, int row,boolean hasFocus) {
		TreeNode entry = (TreeNode) value;
		JXploreFile file = (JXploreFile) entry;
		
	    this.setIcon(file.getIcon());
		this.setText(file.getName());
	    
		 if (hasFocus) {
		      setBackground(HIGHLIGHT_COLOR);
		      setForeground(Color.white);
		    } else {
		      setBackground(Color.white);
		      setForeground(Color.black);
		    }
		 
		return this;
	}



	}


