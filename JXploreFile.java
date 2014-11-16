package nl.hz.ict.ding0033.jxplorer;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 * Dit is de klasse die een JXplorefile representeerd die verschillende aspecten
 * van de files beheert. Daarnaast implementeerd deze klasse de TreeNode die helpt met 
 * het realiseren van de JTree 
 * 
 * ik heb ervoor gekozen om de volgende klassen te importeren:
 * 
 * @author Mark Dingemanse
 * @version 0,1 Concept
 */
public class JXploreFile implements TreeNode {

	// maakt een veld aan van het type file dat een file bezit
	private File file;
	// Maakt een veld aan dat een array van JXploreFile objecten bevat en een cache representeerd
	private JXploreFile[] foldersCache;

	/**
	 * met deze constructor wordt een JXplorefile object aangemaakt met deze
	 * constructor wordt ook een root folder gegenereerd. Dit steld een default
	 * start voor
	 */
	public JXploreFile() {
		this.file = FileSystemView.getFileSystemView().getRoots()[0];
	}

	/**
	 * 
	 * met deze constructor wordt een JXplorefile object aangemaakt
	 * 
	 * @param name steld een string voor met de naam van de te initialiseren file
	 */
	public JXploreFile(String name) {
		file = new File(name);
	}

	/**
	 * met deze constructor wordt een JXplorefile object aangemaakt
	 * 
	 * @param file steld een file voor die wordt meegegeven voor een nieuw JXplorefile
	 */
	public JXploreFile(File file) {
		this.file = file;
	}
	
	// getter voor het file verld
	public File getFile(){
		return file;
	}

	private void initChilderen() {
		foldersCache = getAllItems(false);
	}

	// een getter voor het name field van file
	public String getName() {
		return FileSystemView.getFileSystemView().getSystemDisplayName(file);
	}

	// een getter voor het path field van file
	public String getPath() {
		return file.getAbsolutePath();
	}

	// een getter voor een de date die bij een file hoort
	public Date getDate() {
		return new Date(file.lastModified());
	}

	// een check om te kijken of de file een Folder is of niet
	public boolean isFolder() {
		return !file.isFile();
	}

	// Met deze methode wordt de cache volledig gecleared
	public void clearCache() {
		if (foldersCache != null) {
			for (JXploreFile file : foldersCache) {
				file.clearCache();
				file = null;
			}
			foldersCache = null;
		}
	}

	// met deze getter word de extension van de file vastgesteld op een OS
	// onafhankelijke manier
	public String getExtension(String filename) {
		String afterLastSlash = filename.substring(filename.lastIndexOf('/') + 1);
		int afterLastBackslash = afterLastSlash.lastIndexOf('\\') + 1;
		int dotIndex = afterLastSlash.indexOf('.', afterLastBackslash);
		return (dotIndex == -1) ? "" : afterLastSlash.substring(dotIndex + 1);
	}

	// met deze getter wordt de filesize in mb gereturned
	public Long getFileSize() {
		Long filekb = file.length();
		filekb = filekb / 1024;
		return filekb;
	}

	// met deze methode wordt een icon gereturned
	public Icon getIcon() {
		return FileSystemView.getFileSystemView().getSystemIcon(this.file);
	}

	/*
	 * Deze methode heb ik gemaakt, omdat de methoden getSubFiles() en
	 * getSubFolders() beiden een array met JXploreFile-objecten moesten
	 * aanmaken. Dit is een voorbeeld van Cohesion of Methods en juist
	 * hergebruik van code.
	 */
	private JXploreFile[] makeFiles(File[] input) {
		JXploreFile[] output = new JXploreFile[input.length];
		for (int index = 0; index < input.length; index++) {
			output[index] = new JXploreFile(input[index]);
		}
		return output;
	}

	// met deze methode word een array geturned met subfiles
	public JXploreFile[] getSubFiles() {
		return getAllItems(true);
	}
	
	// deze methode returned de folderscache dat een JXploreFile array bevat
	public JXploreFile[] getSubFolders() {
		if (foldersCache == null && !file.isFile()) {
			initChilderen();
		}
		return foldersCache;
	}

	// deze methode handelt de situaties af rondom het getten van de files. Wanneer er true is dat wordt er een JXplroFile array
	// teruggeven met alle files er nog tussen. Wanneer het false zou zijn dan worden de files eruitgefilterd en blijven de
	// directories over. Wanneer er null meegegeven zou worden wordt er een leeg JXploreFile array meegegeven
	private JXploreFile[] getAllItems(boolean filesNeeded) {
		if (filesNeeded == false) {
			// S T A P 1 : Initialiseer de array met alle files en folders
			JXploreFile[] childFilesAndFolders = getSubFiles();
			// S T A P 2 : Creeer een ArrayList waar zometeen alleen de
			// directories
			// in komen
			ArrayList<JXploreFile> directoryList = new ArrayList<JXploreFile>();
			// S T A P 3 : Itereer over alle files, en...
			for (JXploreFile iterationFile : childFilesAndFolders) {
				if (iterationFile.isFolder()) {
					// Als een file een directory is, voeg hem toe aan de
					// arraylist
					directoryList.add(iterationFile);
				}
			}
			// S T A P 4 : Maak van de ArrayList een array, die geretourneerd
			// worden.
			return directoryList.toArray(new JXploreFile[directoryList.size()]);
		}
		if (filesNeeded == true) {
			return makeFiles(FileSystemView.getFileSystemView().getFiles(file,
					false));
		} else {
			return new JXploreFile[0];
		}
	}
	
	// met deze methode worden de subfolders geturned wanneer ze voldoen aan de
	// eis van dat
	// het een directory is

	/*
	 * ========================================================================
	 * De volgende methoden zijn methoden die nodig zijn voor het implementeren
	 * van de TreeNode interface.
	 * ========================================================================
	 */

	/*
	 * Dit is de moeilijkste. Enumeration is een interface waarvan de werking
	 * overeenkomt met een Iterator (zie de lessen Programmeren 1). We moeten
	 * dus een object retourneren die een implementatie is van deze interface
	 * (zie
	 * http://docs.oracle.com/javase/7/docs/api/java/util/Enumeration.html). Een
	 * elegante (en met de minste code) oplossing is om een anonymous inner
	 * class te gebruiken.
	 * 
	 * @see javax.swing.tree.TreeNode#children()
	 */
	@Override
	public Enumeration<?> children() {
		/*
		 * Een enumeration is een klasse die een beetje lijkt op een Iterator.
		 * Ik definieer hier een anonymous inner class.
		 */
		return new Enumeration<JXploreFile>() {

			// bepaalt de locatie van de enumeration in de array van folders.
			int index = 0;

			@Override
			public boolean hasMoreElements() {
				return index < getSubFolders().length;
			}

			@Override
			public JXploreFile nextElement() {
				return getSubFolders()[index++];
			}

		};
	}

	/*
	 * Children zijn bij ons sub files. Alleen een folder/directory “staat
	 * children toe”.
	 * 
	 * @see javax.swing.tree.TreeNode#getAllowsChildren()
	 */
	@Override
	public boolean getAllowsChildren() {
		return !this.isFile();
	}

	public boolean isFile() {
		return file.isFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildAt(int)
	 */
	@Override
	public TreeNode getChildAt(int childIndex) {
		return getSubFolders()[childIndex];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getChildCount()
	 */
	@Override
	public int getChildCount() {
		return getSubFolders().length;
	}

	/*
	 * Gebruik ook hier de array met subfolders. Loop door de array, totdat je
	 * het juiste child hebt gevonden en retourneer de index van dat child in de
	 * array.
	 * 
	 * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
	 */
	@Override
	public int getIndex(TreeNode node) {
		JXploreFile[] folders = getSubFolders();
		for (int n = 0; n < folders.length; n++)
			if (folders[n].equals(node))
				return n;
		// Niet gevonden
		return -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#getParent()
	 */
	@Override
	public TreeNode getParent() {
		if (file.getParentFile() == null)
			return null;
		return new JXploreFile(file.getParentFile());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.tree.TreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		if (getAllItems(false).length == 0) {
			return true;
		}
		return false;
	}

	/*
	 * ========================================================================
	 * De volgende methode is een helper methode die eigenlijk bedoeld is voor
	 * een goede werking van de TreeView. Je kunt mbv een TreePath namelijk
	 * ervoor zorgen dat een specifiek element in de tree kan worden
	 * geselecteerd.
	 * ========================================================================
	 */
	/**
	 * Retourneert een TreePath object die het pad voorstelt vanaf dit object
	 * tot aan de root van de Tree.
	 * 
	 * @return
	 */
	public TreePath getTreePath() {
		if (file.getParentFile() == null)
			return new TreePath(this);
		else
			return (new JXploreFile(file.getParentFile())).getTreePath()
					.pathByAddingChild(this);
	}

	/*
	 * ========================================================================
	 * De volgende methoden overriden het standaardgedrag van Object.
	 * ========================================================================
	 */

	@Override
	public String toString() {
		return "JXploreFile: " + file.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JXploreFile) {
			JXploreFile jxf = (JXploreFile) obj;
			return file.equals(jxf.file);
		}
		// als niet instanceof JxploreFile...
		return false;
	}

}
