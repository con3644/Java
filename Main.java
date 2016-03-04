/*Brandon Arnold
 *Max Conroy
 *2/22/2016
 *Program 3 CET 350
 */
 

import java.io.*;
import java.awt.*;
import java.lang.*;
import java.awt.event.*;
import java.util.StringTokenizer;	

public class Main extends Frame implements ActionListener, WindowListener
{
    public void winClose(WindowEvent e){}
	public void winOpen(WindowEvent e){}
	public void winActive(WindowEvent e){}
	public void winDeactive(WindowEvent e){}
	public void winIcon(WindowEvent e){}
	public void winDeicon(WindowEvent e){}
	private static final long serialVersionUID = 1L;
	List list = new List(13);
	File curDir;
	FileWriter diskfile = null;
	PrintWriter outfile = null;
	BufferedReader infile;
	FileReader inputfile;
	
	//window objects
	Button OKButton = new Button("     OK     ");
	Button TargetButton = new Button("Target :");
	Label TargetLabel = new Label("Select Target Directory:", Label.LEFT);
	Label SourceLabel = new Label("");
	Label SourceLabel2 = new Label("Source:");
	Label FileLabel = new Label("File Name:");
	Label MessageLabel = new Label("");
	
	//inputs
	TextField FileName = new TextField(90);
	boolean targetGiven = false, outfileGiven = false, sourceGiven = false;
	String filename = "";
	
	Main(File dir)
	{
		this.curDir = dir;
		//set up the size of the window and arrange the columns and rows
		double[] colWeight = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		double[] rowWeight = {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		int[] colWidth = { 1, 1, 1, 1, 1, 1, 1, 1 };
		int[] rowHeight = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
		GridBagConstraints c = new GridBagConstraints();
		GridBagLayout window = new GridBagLayout();
		window.rowHeights = rowHeight;
		window.columnWidths = colWidth;
		window.columnWeights = colWeight;
		window.rowWeights = rowWeight;
		c.anchor = 13;
		
		this.setBounds(0, 0, 200, 800);
		this.setLayout(window);
		
		//file list
		this.list.setSize(200, 700);
		c.weightx = 1.0D;
		c.weighty = 1.0D;
		c.gridwidth = 8;
		c.gridheight = 13;
		c.fill = 1;
		c.gridx = 0;
		c.gridy = 0;
		window.setConstraints(this.list, c);
		
		//source label
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 0.0D;
		c.weighty = 0.0D;
		c.fill = 0;
		c.gridx = 0;
		c.gridy = 13;
		window.setConstraints(this.SourceLabel2, c);
		
		//message label
		c.gridy = 16;
		window.setConstraints(this.MessageLabel, c);
		
			
		//target label
		c.gridx = 1;
		c.gridy = 14;
		window.setConstraints(this.TargetLabel, c);
		
		//target button
		c.gridx = 0;
		c.gridy = 14;
		window.setConstraints(this.TargetButton, c);
		
		//file label
		c.gridx = 0;
		c.gridy = 15;
		window.setConstraints(this.FileLabel, c);
		
		//OK button
		c.gridx = 8;
		c.gridy = 15;
		window.setConstraints(this.OKButton, c);
		
		//source label
		c.weightx = 1.0D;
		c.gridwidth = 6;
		c.fill = 2;
		c.gridx = 1;
		c.gridy = 13;
		window.setConstraints(this.SourceLabel, c);
	
		
		//file name label
		c.gridx = 1;
		c.gridy = 15;
		window.setConstraints(this.FileName, c);
		
		
		//add items to window
		add(this.list);
		add(this.FileName);
		add(this.TargetButton);
		add(this.OKButton);
		add(this.SourceLabel2);
		add(this.FileLabel);
		add(this.TargetLabel);
		add(this.SourceLabel);
		add(this.MessageLabel);
		
		//set to display component
		this.setResizable(true);
		pack();
		this.setVisible(true);
		//add listeners for events
		addWindowListener(this);
		this.FileName.addActionListener(this);
		this.TargetButton.addActionListener(this);
		this.OKButton.addActionListener(this);
		this.list.addActionListener(this);
		display(null);
	}
	
	void display(String name)
	{
		if (name != null)
		{
			if (name.equals(".."))
			{
				this.curDir = new File(this.curDir.getParent());
			}
			else
			{
				File f = new File(this.curDir, name);
				if (f.isDirectory())
				{
					this.curDir = new File(this.curDir, name);
				}
				else if ((this.sourceGiven) && (this.targetGiven))
				{
					this.FileName.setText(name);
					this.outfileGiven = true;
				}
				else
				{
					this.SourceLabel.setText(this.curDir.getAbsolutePath() + "/" + name);
					this.sourceGiven = true;
				}
			}
		}
		String[] filenames = this.curDir.list();
		
		//check filenames wont throw errors using array methods
		if (filenames == null)
		{
			filenames = new String [0];
		}
		else
		{
			setTitle(this.curDir.getAbsolutePath());
			for (String s : filenames)
			{
				File f = new File(this.curDir, s);
				if (f.isDirectory())
				{
					String[] contents = f.list();
			
					if (contents != null)
					{
						int i = 0;
						while ((i < contents.length) && (!s.endsWith(" +")))
						{
							if (new File(f, contents[i]).isDirectory())
							{
								s = s + " +";
							}
							i++;
						}
					}
				}
			}
			//update list
			this.list.removeAll();
			if (this.curDir.getParent() != null)
			{
				this.list.add("..");
			}
			for (int i = 0; i < filenames.length; i++)
			{
				this.list.add(filenames[i]);
			}
		}
	}
	
	//checks action events and performs functions
	public void actionPerformed(ActionEvent e)
	{
		Object source = e.getSource();
		
		//user entered text in filename field
		if (source == this.FileName)
		{
			this.MessageLabel.setText("");
			this.filename = this.FileName.getText();
			if (this.filename.length() != 0)
			{
				this.outfileGiven = true;
				CopyFile();
			}
			else
			{
				this.MessageLabel.setText(this.MessageLabel.getText() + " Target file not specified.");
			}
		}
		//user clicked ok button
		else if (source == this.OKButton)
		{
			this.MessageLabel.setText("");
			this.filename = this.FileName.getText();
			if (this.filename.length() != 0)
			{
				this.outfileGiven = true;
				CopyFile();
			}
			else
			{
				this.MessageLabel.setText(this.MessageLabel.getText() + " Target file not specified.");
			}
		}
		//user clicked target button
		else if (source == this.TargetButton)
		{
			this.MessageLabel.setText("");
			this.TargetLabel.setText(this.curDir.getAbsolutePath());
			this.targetGiven = true;
		}
		//user selected file from list
		else if (source == this.list)
		{
			this.MessageLabel.setText("");
			String item = this.list.getSelectedItem();
			if (item != null)
			{
				if (item.endsWith(" +"))
				{
					item = item.substring(0, item.length() - 2);
				}
				display(item);
			}
		}
	}
	
	//stops all listeners and removes window components
	public void Stop() 
	{
		this.FileName.removeActionListener(this);
		this.TargetButton.removeActionListener(this);
		this.OKButton.removeActionListener(this);
		this.list.removeActionListener(this);
		removeWindowListener(this);
		dispose();
	}
	
    //copys the selected files
    public void CopyFile()
    {
		String directory = new String("");
		boolean pass = true;
        
        //check if all the files are given
        if ((this.targetGiven) && (this.outfileGiven) && (this.sourceGiven))
        {
        	//get directory from the label
			directory = this.TargetLabel.getText();
			File dir = new File(directory);
			if ((dir.isDirectory()) && (dir.exists()))
			{
				try
				{
					//check exists
					File f = new File(directory + "/" + this.filename);
					if (f.exists())
					{
						this.MessageLabel.setText("Output file " + this.filename + " exists.");
					}
					else
					{
						this.MessageLabel.setText("");
					}
					outfile = new PrintWriter(new FileWriter(directory + "/" + this.filename));
				}
            	catch (IOException e)
            	{
              		this.MessageLabel.setText("Caught exception: " + e);
              		pass = false; //invalid file
            	}
          	}
          	if (pass)
          	{
            	try
            	{
              		try
              		{
				        this.inputfile = new FileReader(this.SourceLabel.getText());
				        this.infile = new BufferedReader(this.inputfile);
				        int c;
				        while ((c = this.infile.read()) != -1)
				        {
            				outfile.write(c);
                		}
		                this.infile.close();
		                outfile.close();
              		}
              		//file does not exist
              		catch (FileNotFoundException e)
              		{
                		this.MessageLabel.setText("Error opening file. Caught Exception:" + e);
              		}
            	}
            	catch (IOException e)
            	{
             		System.err.println("Caught exception: " + e);
            	}
            	//reset labels
	            this.TargetLabel.setText("Select Target Directory:");
	            this.SourceLabel.setText("");
	            this.FileName.setText("");
	            this.MessageLabel.setText(this.MessageLabel.getText() + "File Copied");
	            this.targetGiven = false;
	            this.outfileGiven = false;
	            this.sourceGiven = false;
          	}
        }
        else
        {
            //unspecified source
            if (!this.sourceGiven)
            {
                this.MessageLabel.setText(this.MessageLabel.getText() + " Source file not specified.");
            }
            //unspecified target
            if (!this.targetGiven)
            {
                this.MessageLabel.setText(this.MessageLabel.getText() + " Target directory not specified.");
            }
        }
    }
	
	//stop processes after winClose
	public void windowClosing(WindowEvent e) 
	{
		Stop();
	}
	
	public static void main (String[] args) 
	{
		if (args.length >= 1)
		{
			File dir = new File(args[0]);
			try
			{
				if (dir.exists())
					new Main(new File(dir.getAbsolutePath()));
			}
			catch (Exception e)
			{
				System.err.println("Error caught: " + e +"\n\n");
				System.err.println(dir + " is not a valid path");
			}
		}
		else
			new Main(new File(new File(System.getProperty("user.dir")).getAbsolutePath()));
	}
	
}
