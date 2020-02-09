package sninctbur;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GarryUploaderSettings extends JFrame implements GarryUploaderShared{
	static JPanel main;
	
	public GarryUploaderSettings() throws IOException {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		GridBagLayout gridbag = new GridBagLayout();
		Properties properties = new Properties();
		InputStream input = getClass().getClassLoader().getResourceAsStream("internal.properties");
		properties.load(input);
		
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(-200,-50,0,0);
		c.anchor = GridBagConstraints.PAGE_START;
		c.fill = GridBagConstraints.WEST;
		
        
		setSize(800,600);
		setLocation((int)screenSize.getWidth() / 2 - 400,(int)screenSize.getHeight() / 2 - 300);
		setTitle("Settings");
		setLayout(gridbag);
		
		main = new JPanel();
		add(main);
		
		
		// do stuff here
		JLabel label1 = new JLabel("GarrysMod Folder Path");
		
		JTextField text1 = new JTextField(properties.getProperty("GmPath"));
		text1.setPreferredSize(new Dimension(250,20));
		
		JButton button1 = new JButton("Browse");
		
		JButton buttonApply = new JButton("Apply Changes");
		JButton buttonApplyLeave = new JButton("Apply and Exit");
		JButton buttonLeave = new JButton("Exit");
		JLabel info = new JLabel("Garry Uploader v1.0 by Sninctbur");
		
		
		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent input) {
				// TODO Auto-generated method stub
				Object source = input.getSource();
				try {
					if(source == button1){
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setAcceptAllFileFilterUsed(false);
						int result = chooser.showOpenDialog(main);
						
						if(result == JFileChooser.APPROVE_OPTION) {
							text1.setText(chooser.getSelectedFile().getPath());
						}else if(result == JFileChooser.ERROR_OPTION)
							JOptionPane.showMessageDialog(main,"Something went wrong.\nPlease bear with me and try again.","Error",JOptionPane.ERROR_MESSAGE);
						
					}else if(source == buttonApply || source == buttonApplyLeave) {
//						if(!new File(text1.getText()).exists()) {
//							JOptionPane.showMessageDialog(main,"The gmpublish.exe Path is not a valid file path.","Error",JOptionPane.ERROR_MESSAGE);
//						}

						
						FileOutputStream out = new FileOutputStream("internal.properties");
						properties.put("GmPath",text1.getText());
						properties.store(out,"Garry Uploader internal and user-defined settings");
						out.flush();
					}if(source == buttonLeave || source == buttonApplyLeave) {
						if(properties.getProperty("GmPath") == null) {
							remakeProperties();
						}
						if(!properties.getProperty("GmPath").equals(text1.getText())) {
							String[] options = {"Yes","No"};
							int option = JOptionPane.showOptionDialog(main,"You have unsaved changes. These changes will be lost if you leave this panel now."
									+ "\nContinue anyway?","Garry Uploader", JOptionPane.WARNING_MESSAGE,0,null,options,options[1]);
							
							if(option == 0) {
								new GarryUploader();
								dispose();
							}
						}
						else {
							new GarryUploader();
							dispose();
						}
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		button1.addActionListener(listener);
		buttonApply.addActionListener(listener);
		buttonApplyLeave.addActionListener(listener);
		buttonLeave.addActionListener(listener);
		
		
		c.gridx = 0;
		c.gridy = 0;
		
		add(label1,c);
		
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(-200,20,0,0);
		add(text1,c);
		
		c.gridx = 2;
		c.gridy = 0;
		//c.insets = new Insets(-190,0,0,0);
		add(button1,c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0,-10,0,0);
		c.anchor = GridBagConstraints.SOUTH;
		
		add(buttonApply,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,0,0);
		c.anchor = GridBagConstraints.SOUTH;
		
		add(buttonApplyLeave,c);
		
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,10,0,0);
		c.anchor = GridBagConstraints.PAGE_END;
		add(buttonLeave,c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0,0,-100,0);
		c.anchor = GridBagConstraints.PAGE_END;
		add(info,c);
		
		
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
//	public static void main(String[] args) throws IOException {
//		new GarryUploaderSettings();
//	}
}
