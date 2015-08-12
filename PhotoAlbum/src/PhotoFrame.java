import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.Serializable;
import javax.swing.*;

@SuppressWarnings("serial")
public class PhotoFrame extends JFrame implements ActionListener, Serializable {
	
	private JLabel imagePane = null;
	private Storage photos = new Storage();
	private int photosIndex = 1;
	private final JFileChooser fc = new JFileChooser();
	ImageIcon image = new ImageIcon();
	
	private JMenu file = new JMenu("File");
	private JMenuItem exit = new JMenuItem("Exit",KeyEvent.VK_X);
	private JMenu view = new JMenu("View");
	private JMenuItem browse = new JMenuItem("Browse",KeyEvent.VK_B);
	private JMenuItem maintain = new JMenuItem("Maintain",KeyEvent.VK_M);
	
	private JTextArea description = null;
	private JLabel descriptionLabel = new JLabel("Description: ");
	
	private JLabel dateLabel = new JLabel("Date: ");
	private JTextField date = new JTextField();
	private JButton delete = new JButton("Delete");
	private JButton save = new JButton("Save Changes");
	private JButton add = new JButton("Add Photo");
	
	private JButton prev = new JButton("Prev");
    private JButton next = new JButton("Next");
    private JTextField currentPhoto = new JTextField();
    private JLabel lastPhoto = new JLabel();
    

	public PhotoFrame() {
		// --- Set the title of the window.
		super("Photo Viewer");
		try {
			openPhotosFile();
			System.out.println("Opening Photos File!");
			photosIndex++;
		}
		catch (Exception e) {
			System.out.println(e);
		}
		
		// --- Set up the menu bar.
		JMenuBar menuBar = new JMenuBar();
		setUpMenuBar(menuBar);
		setJMenuBar(menuBar);
		
		// --- Set up the layout for each section of the window.
		Container frame = getContentPane();
		frame.setLayout(new BoxLayout(frame, 3));
		
		// --- Add image to the window.
		imagePane = new JLabel(image);
		JScrollPane scrollImage = new JScrollPane(imagePane);
	
		frame.add(scrollImage);
		
		// --- Adds the description information.
		JPanel descPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		setUpDescriptionPanel(descPanel);
		frame.add(descPanel);
		
		// --- Adds the date information.
		JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		setUpDatePanel(datePanel);
		frame.add(datePanel);
		
		// --- Adds the increment panel.
		JPanel veryBottom = new JPanel(new BorderLayout());
		JPanel incrementPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		setUpIncrementPanel(incrementPanel);
		veryBottom.add(incrementPanel, BorderLayout.SOUTH);
		frame.add(veryBottom);
		
		// --- Update all of the display.
        updateDisplay();
		
		// --- Set to close the program when the window is closed.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
	}

	private void updateDisplay() {	
		currentPhoto.setText(String.valueOf(photosIndex));
		lastPhoto.setText(" of " + String.valueOf(photos.getSize()));
		
		prev.setEnabled(photosIndex > 1);
		next.setEnabled(photosIndex < photos.getSize());
		
		if (photos.getSize() > 0) {
			image = photos.getPhoto(photosIndex-1);
			imagePane.setIcon(image);
			description.setText(photos.getDesc(photosIndex-1));
			date.setText(photos.getDate(photosIndex-1));
		}
		else {
			image = new ImageIcon(getClass().getResource("NoPhotos.jpg"));
			imagePane.setIcon(image);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == prev) {
			photosIndex--;
			updateDisplay();
		}
		else if (evt.getSource() == next) {
			photosIndex++;
			updateDisplay();
		}
		else if (evt.getSource() == exit) {
        	System.exit(0);
        }
        else if (evt.getSource() == browse) {
        	setButtonStatus(false);
        	updateDisplay();
        }
        else if (evt.getSource() == maintain) {
        	setButtonStatus(true);
        	updateDisplay();
        }
        else if (evt.getSource() == delete) {
        	photos.deletePhoto(photosIndex - 1);
        	if (photosIndex > photos.getSize())
        		photosIndex = photos.getSize();
        	updateDisplay();
        	saveToFile();
        }
        else if (evt.getSource() == save) {
        	System.out.println("SAVED");
        	photos.setDesc(description.getText(), photosIndex - 1);
        	photos.setDate(date.getText(), photosIndex - 1);
        	saveToFile();
        }
        else if (evt.getSource() == add) {
        	getFileToAdd();
        	updateDisplay();
        }
        else {
            System.out.println("Unexpected event: " + evt);
        }
	}
	
	private void getFileToAdd() {
		int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            PhotoObject newPhoto = new PhotoObject(file.getPath());
            photos.addPhoto(newPhoto);
            System.out.println("Opening: " + file.getName());
            photosIndex++;
        } 
        else {
        	System.out.println("File Open dialog canceled");
        }
	}
	
	private void addToDB() {
		
	}
	
	private void saveToFile() {
		photos.saveToFile();
	}
	
	private void openPhotosFile() {
		photos.openPhotosFile();
	}

	private void setButtonStatus(boolean edit) {
		if (edit) {
			description.setEditable(true);
			date.setEditable(true);
			delete.setVisible(true);
			save.setVisible(true);
			add.setVisible(true);
		}
		else {
			description.setEditable(false);
			date.setEditable(false);
			delete.setVisible(false);
			save.setVisible(false);
			add.setVisible(false);
		}
	}
	
	private void setUpDescriptionPanel(JPanel descPanel) {
        description = new JTextArea(3, 20);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        
        descPanel.add(descriptionLabel);
        descPanel.add(description);
	}
	
	private void setUpDatePanel(JPanel datePanel) {
        date = new JTextField();
        date.setColumns(5);
        
        datePanel.add(dateLabel);
        datePanel.add(date);
        datePanel.add(delete);
        datePanel.add(save);
        datePanel.add(add);
        
        date.setEditable(false);
		delete.setVisible(false);
		delete.addActionListener(this);
		save.setVisible(false);
		save.addActionListener(this);
		add.setVisible(false);
		add.addActionListener(this);
	}
	
	private void setUpIncrementPanel(JPanel incrementPanel) {
        currentPhoto.setColumns(3);
        
        prev = new JButton("Prev");
        prev.addActionListener(this);
        
        next = new JButton("Next");
        next.addActionListener(this);
        
        incrementPanel.add(currentPhoto);
        incrementPanel.add(lastPhoto);
        incrementPanel.add(prev);
        incrementPanel.add(next);
	}
		
	private void setUpMenuBar(JMenuBar menuBar) {
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        menuBar.add(file);
        
        exit = new JMenuItem("Exit",KeyEvent.VK_X);
        exit.addActionListener(this);
        file.add(exit);
        
        view = new JMenu("View");
        view.setMnemonic(KeyEvent.VK_V);
        menuBar.add(view);
        
        browse = new JMenuItem("Browse",KeyEvent.VK_B);
        browse.addActionListener(this);
        view.add(browse);
        
        maintain = new JMenuItem("Maintain",KeyEvent.VK_M);
        maintain.addActionListener(this);
        view.add(maintain);
	}

	
	// Main entry point
    public static void main(String[] args) {
        JFrame frame = new PhotoFrame();

        frame.setSize(600, 500);
        // You also might see programs that call show()
        // to make the frame visible on the screen. show() and
        // setVisible(true) are equivalent.
        //frame.setVisible(true);
        
    }
    
}
