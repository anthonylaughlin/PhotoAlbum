import java.sql.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Storage implements Serializable {

	private ArrayList<PhotoObject> photos = null;
	private int numOfPhotos = 0;
	ImageIcon image;
	
	public Storage() {
		photos = new ArrayList<PhotoObject>();
	}
	
	public void addPhoto(PhotoObject picture) {
		photos.add(picture);
		numOfPhotos++;
	}
	
	public void deletePhoto(int index) {
		photos.remove(index);
		numOfPhotos--;
	}
	
	public int getSize() {
		return numOfPhotos;
	}
	
	public ImageIcon getPhoto(int index) {
		image = new ImageIcon(photos.get(index).getFileName());
		return image;
	}
	
	public String getDesc(int index) {
		return photos.get(index).getDescription();
	}
	
	public void setDesc(String desc, int index) {
		photos.get(index).setDescription(desc);
	}
	
	public String getDate(int index) {
		return photos.get(index).getDate();
	}
	
	public void setDate(String date, int index) {
		photos.get(index).setDate(date);
	}
	
	public void saveToFile() {
		try {
            ObjectOutputStream fw = new ObjectOutputStream(new FileOutputStream("photos.txt"));
            fw.writeObject(photos);
            fw.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void openPhotosFile() {
		try {
			ObjectInputStream fr = new ObjectInputStream(new FileInputStream("photos.txt"));
            photos = (ArrayList<PhotoObject>) fr.readObject();
            fr.close();
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
