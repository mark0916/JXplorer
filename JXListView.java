package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class JXListView extends JXploreView {

	private static final long serialVersionUID = 1L;

	// maakt een veld aan waar het listmodel in gestopt wordt
	private DefaultListModel<JXploreFile> model;
	// maakt een veld aan waar de list met JXploreFile objecten in gestop wordt
	private JList<JXploreFile> list;
	// maakt een veld aan waar de renderer in gestop wordt
	private JXCellRenderer renderer;

	// De constructor zet een de JXplorer in het data veld van JXploreView (de
	// superklasse)
	// tot slot wordt de buildGUI methode aangeropen om de GUI te initialiseren
	public JXListView(JXplorer data) {
		this.setData(data);
		buildGUI();
	}

	@Override
	protected void buildGUI() {
		// maakt een nieuw model aan en stopt deze in het modelveld
		model = new DefaultListModel<JXploreFile>();
		// maakt een listrenderer aan
		renderer = new JXCellRenderer();
		// elke subfile wordt toegevoegd aan de lijst
		addElementsToModel();
		JList<JXploreFile> jlist = new JList<JXploreFile>(model);
		// voegt de renderer toe aan de list
		jlist.setCellRenderer(renderer);
		list = jlist;
		// voegt een mouselistner toe aan de list
		list.addMouseListener(mouseListener);

		// voegt een mousemoutionlissener toe aan de lijst
		this.list.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				list.setSelectedIndex(list.locationToIndex(new Point(e.getX(), e.getY())));
			}
		});
		
		// maakt een scrolpane aan op basis van de lijst
		JScrollPane scrollPane1 = new JScrollPane(list);
		// zet de layout als BorderLayout;
		this.setLayout(new BorderLayout());
		// voegt de scrolpanel toe aan het huidige object (JPanel)
		this.add(scrollPane1);
	}

	@Override
	protected void updateGUI() {
		// cleart eerst het huidige model
		model.clear();
		// cleart de lijst
		list.clearSelection();

		// elke subfile wordt toegevoegd aan de lijst
		addElementsToModel();

		// maakt een scrolpane aan op basis van de lijst
		JScrollPane scrollPane1 = new JScrollPane(list);

		// cleart de JPanel
		this.removeAll();
		// zet de layout als BorderLayout;
		this.setLayout(new BorderLayout());
		// voeg de eerder aangemaakte scrolpane toe aan het huidige object (eem
		// JPanel JXploreView extends JPanel)
		this.add(scrollPane1);
	}
	
	// deze methode haalt alle subFiles op van de currentFile en voegt deze toe aan het model
	private void addElementsToModel() {
		for (JXploreFile file : this.getData().getCurrentFile().getSubFiles()) {
			if (file != null) {
				model.addElement(file);
			}
		}
	}

	// maakt een mouselissener aan
	MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			// als je dubbelklikt op een element uit de lijst
			if (e.getClickCount() == 2) {
				// zet het geselecteerde item als currentfile
				JXploreFile selectedItem = (JXploreFile) list.getSelectedValue();
				// als het een directory is
				if (selectedItem.getFile().isDirectory()) {
					try {
						// verander de locatie en hermaak het scherm
						getData().setCurrentFile(selectedItem.getPath());
					}
					// wanneer de file niet geopend kan worden
					catch (NullPointerException seNullPointerException) {
						getData().showMessageDialog(
								"cant open this file. Please try a different one!");
					}
					// als de geselecteerde file geen directory is
				} else {
					getData().showMessageDialog(generateFileMessage());
				}
			}
		};
	};

	// genereer een file message aan de hand van de geselecteerde vallue
	private String generateFileMessage() {
		JXploreFile selectedItems = (JXploreFile) list.getSelectedValue();
		return this.getData().generateFileMessage(selectedItems);
	}
}
