import java.io.Serializable;

@SuppressWarnings("serial")
public class PhotoObject implements Serializable {
	
	private String FileName = null;
	private String Description = null;
	private String Date = null;
	
	public String getFileName() {
		return FileName;
	}
	
	public String getDescription() {
		return Description;
	}
	
	public void setDescription(String desc) {
		Description = desc;
	}
	
	public String getDate() {
		return Date;
	}
	
	public void setDate(String date) {
		Date = date;
	}
	
	public PhotoObject(String file) {
		FileName = file;
	}
	
}
