package sninctbur;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public interface GarryUploaderShared {
	default void remakeProperties() throws IOException {
		Properties properties = new Properties();
		new File("internal.properties").createNewFile();
		FileOutputStream out = new FileOutputStream("internal.properties");
		properties.put("GmPath","C:\\\\Program Files (x86)\\\\Steam\\\\SteamApps\\\\common\\\\GarrysMod");
		properties.put("AddonSaved","");
		properties.put("ImgSaved","");
		properties.store(out,"Garry Uploader user-defined settings");
		out.flush();
	}
}
