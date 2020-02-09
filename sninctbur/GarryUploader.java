package sninctbur;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GarryUploader extends JFrame implements GarryUploaderShared {
	static File addon,img,gmpublish;
	static JPanel main;
	static GarryUploaderSettings settings;
	
	
	void showSettings() throws IOException {
		setVisible(false);
		new GarryUploaderSettings();
		dispose();
	}
	
	public boolean showGmMissing() throws IOException {
		if(main == null || gmpublish == null) return false;
		String[] options = {"Settings","Continue"};
		String messageText;
		
		if(gmpublish.getPath().equals("C:\\\\Program Files (x86)\\\\Steam\\\\SteamApps\\\\common\\\\GarrysMod"))
			messageText = "gmpublish.exe could not be found in its default location.";
		else
			messageText = "gmpublish.exe could not be found in its currently designated location.";
		
		int option = JOptionPane.showOptionDialog(main,messageText
				+ "\nPlease designate the current location of your Garry's Mod installation in the Settings panel.",
				"Garry Uploader",JOptionPane.WARNING_MESSAGE,0,null,options,options[0]);
		
		if(option == 0) {
			showSettings();
			return true;
		}
		else if(option == -1) {
			dispose();
			return true;
		}
		else if(option != 1)
			dispose();
		
		return false;
	}
	
	
	public GarryUploader() throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,0,0);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.WEST;
		//c.weighty = 0.2;
		Properties properties = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("internal.properties");
		
        
		setSize(500,600);
		setLocation((int)screenSize.getWidth() / 2 - 250,(int)screenSize.getHeight() / 2 - 300);
		setTitle("Garry Uploader");
		setLayout(gridbag);
		
		main = new JPanel();
		add(main);
		
		
		if(input != null)
			properties.load(input);
		else {
//			PrintWriter propsWrite = new PrintWriter("internal.properties","UTF-8");
//			propsWrite.println("GmPath=C:\\\\Program Files (x86)\\\\Steam\\\\SteamApps\\\\common\\\\GarrysMod\\\\bin\\\\gmpublish.exe");
//			propsWrite.close();
			remakeProperties();
		}
		
		try {
			gmpublish = new File(properties.getProperty("GmPath") + "\\bin\\gmpublish.exe");
		}catch(Exception e){
			if(properties.getProperty("GmPath") == null) {
				remakeProperties();
			}
			
			gmpublish = new File(properties.getProperty("GmPath") + "\\bin\\gmpublish.exe");
		}
		
//		boolean publishFound = false;
//		while(publishFound == false) {
//			if(gmpublish.exists() && !gmpublish.isDirectory() && gmpublish.getName().equals("gmpublish.exe")) {
//				publishFound = true;
//				properties.setProperty("GmPath",gmpublish.getPath());
//			}else {
//				String[] options = {"OK","Close"};
//				String messageText;
//				
//				if(gmpublish.exists() && !gmpublish.getName().equals("gmpublish.exe"))
//					messageText = "The selected file is invalid.";
//				else if(gmpublish.getPath().equals("C:\\Program Files (x86)\\Steam\\SteamApps\\common\\GarrysMod\\bin\\gmpublish.exe"))
//					messageText = "gmpublish.exe could not be found in its default location.";
//				else
//					messageText = "gmpublish.exe could not be found in its former location.";
//				
//				int optionChosen = JOptionPane.showOptionDialog(main,messageText
//						+ "\nPlease set the location of gmpublish.exe in the Settings panel (under GarrysMod\\bin by default).",
//						"Error",JOptionPane.ERROR_MESSAGE,0,null,options,options[0]);
//				
//				if(optionChosen == 0) {
//					JFileChooser chooser = new JFileChooser("C:");
//					chooser.setFileFilter(new FileNameExtensionFilter(".exe files","exe"));
//					int returnVal = chooser.showOpenDialog(main);
//					if(returnVal == JFileChooser.APPROVE_OPTION) {
//						gmpublish = chooser.getSelectedFile();
//					}else if(returnVal == JFileChooser.CANCEL_OPTION) return;
//				}else return;
//			}
//		}
		
		if(!properties.getProperty("AddonSaved").equals(""))
			addon = new File(properties.getProperty("AddonSaved"));
		if(!properties.getProperty("ImgSaved").equals(""))
			img = new File(properties.getProperty("ImgSaved"));
		
		JButton addonButton = new JButton("Select Addon");
		if(addon != null)
			addonButton.setText("Selected Addon: " + addon.getName());
		JButton imageButton = new JButton("Select Image");
		if(img != null)
			imageButton.setText("Selected Image: " + img.getName());
		JButton uploadButton = new JButton("Upload Addon");
		JButton settingsButton = new JButton("Settings");
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent input) {
				try {
					Object source = input.getSource();
					if(source == addonButton) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileFilter(new FileNameExtensionFilter(".gma files","gma"));
						
						int returnVal = chooser.showOpenDialog(main);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							addon = chooser.getSelectedFile();
							addonButton.setText("Selected Addon: " + addon.getName());
						}else if(returnVal == JFileChooser.ERROR_OPTION)
							JOptionPane.showMessageDialog(main,"Something went wrong. Try again","Error",JOptionPane.ERROR_MESSAGE);
					}
					else if(source == imageButton) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileFilter(new FileNameExtensionFilter(".jpg files","jpg"));
						
						int returnVal = chooser.showOpenDialog(main);
						if(returnVal == JFileChooser.APPROVE_OPTION) {
							img = chooser.getSelectedFile();
							imageButton.setText("Selected Image: " + img.getName());
						}else if(returnVal == JFileChooser.ERROR_OPTION)
							JOptionPane.showMessageDialog(main,"Something went wrong. Try again","Error",JOptionPane.ERROR_MESSAGE);
					}
					else if(source == uploadButton) {
						gmpublish = new File(properties.getProperty("GmPath") + "\\bin\\gmpublish.exe");
						if(!gmpublish.exists())
							showGmMissing();
						else {
							if(addon != null && img != null && addon.exists() && img.exists()) {
								FileOutputStream out = new FileOutputStream("internal.properties");
								properties.put("AddonSaved",addon.getPath());
								properties.put("ImgSaved",img.getPath());
								properties.store(out,"Garry Uploader user-defined settings");
								
								// Current problem: If there are any spaces in the file paths for the addon or the image file, the command will not work
								ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c","\"" + properties.getProperty("GmPath") + "\\bin\\gmpublish.exe\" create "
										+ "-addon " + addon.getPath().replaceAll(" ","^ ")
										+ " -icon " + img.getPath().replaceAll(" ","^ "));
								uploadButton.setText("Uploading...");
								builder.redirectErrorStream(true);
							    Process p = builder.start();
						        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
						        String line;
						        while (true) {
						            line = r.readLine();
						            if (line == null) { 
						            	uploadButton.setText("Upload Complete");
						            	break;
						            }
						            //System.out.println(line);
						        }
							}else
								JOptionPane.showMessageDialog(main,"Garry's Mod addons require both a .gma addon file and a .jpg icon to be uploaded to the Workshop."
										+ "\nPlease verify that you have correctly specified both of these files and try again.","Garry Uploader",JOptionPane.ERROR_MESSAGE);
						}
					}
					else if(source == settingsButton)
						showSettings();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		settingsButton.addActionListener(listener);
		addonButton.addActionListener(listener);
		imageButton.addActionListener(listener);
		uploadButton.addActionListener(listener);
		
		//c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(-100,-200,0,0);
		add(settingsButton,c);
		
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(10,0,0,0);
		add(addonButton,c);

		c.gridx = 2;
		c.gridy = 1;
		add(imageButton,c);
		
		c.gridx = 2;
		c.gridy = 2;
		c.insets = new Insets(140,0,0,0);
		add(uploadButton,c);
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(!gmpublish.exists()) {
			if(!showGmMissing())
				setVisible(true);
		}else
				setVisible(true);

	}
	
	static void addComp(JComponent c,int col,int row,int w,int h,int place,int stretch) {
		GridBagConstraints gridC = new GridBagConstraints();
		gridC.gridx = col;
		gridC.gridy = row;
		gridC.gridwidth = w;
		gridC.gridheight = h;
		gridC.insets = new Insets(5,5,5,5);
		gridC.anchor = place;
		gridC.fill = stretch;
		main.add(c,gridC);
	}
	
	
//	public static void main(String[] args) throws IOException {
//		new GarryUploader();
//	}
}
