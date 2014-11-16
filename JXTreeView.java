package nl.hz.ict.ding0033.jxplorer;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class JXTreeView extends JXploreView {

	private static final long serialVersionUID = 1L;
	private JTree tree;
	private JXTreeRenderer renderer;

	public JXTreeView(JXplorer data) {
		this.tree = new JTree();
		this.renderer = new JXTreeRenderer();
		this.setData(data);
		buildGUI();
	}

	@Override
	protected void buildGUI() {

		this.setData(this.getData());

		// voeg de renderer toe
		tree.setCellRenderer(renderer);
		// voeg een tree lissener toe aan de tree
		tree.addMouseListener(mouseListener);

		// voeg een muismotionlissener toe aan de JTree die duidelijkheid geeft wanneer je ergens over hovert
		this.tree.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int x = (int) e.getPoint().getX();
				int y = (int) e.getPoint().getY();
				TreePath path = tree.getPathForLocation(x, y);
				tree.setSelectionPath(tree.getClosestPathForLocation(e.getX(),e.getY()));
				if (path == null) {
					tree.setCursor(Cursor.getDefaultCursor());
				} else {
					tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				}
			}
		});

		// voeg de tree toe aan een scrolpane
		JScrollPane panel = new JScrollPane(tree);
		//zet de layout op BorderLayout
		this.setLayout(new BorderLayout());
		// voeg het panel toe aan het huidige object (JPanel)
		this.add(panel);
	}

	// deze methode zorgt ervoor dat de data geset wordt en een model aangemaakt op basis van de root van de zojuist gezette JXplorer
	protected void setData(JXplorer data) {
		super.setData(data);
		tree.setModel(new DefaultTreeModel(this.getData().getRoot()));
	}
	
	// maakt een mouselissener aan en voegt deze toe en bepaalt wat er moet
	// gebeuren aan de hand van situaties
	MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				JXploreFile selectedItem = (JXploreFile) tree.getLastSelectedPathComponent();
				if (selectedItem == null) {
					return;
				}
				getData().setCurrentFile(selectedItem);
			}
		}
	};

	@Override
	protected void updateGUI() {
		// deze methode blijft voorlopig leeg
	}
}
