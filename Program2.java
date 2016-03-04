/*Brandon Arnold
 *Max Conroy
 *Group 1
 *CET 350
 *con3644@calu.edu
 *arn4181@calu.edu
 */


/*
 * ------- This java console app will open files, read strings from files, process strings, and write data to files. --------
 */
  
import java.io.*;
import java.util.StringTokenizer;

public class Program2 
{
    //The custom word object used when parsing the input file

    // The main method
    static int currentIndex = 0; 
    static Word words[] = new Word[100];

    public static void main(String[] args) throws IOException
    {
        try
        {
            if (args.length == 1)
            {
                copy(args[0], null);
            }
            else if (args.length == 0)
            {
                copy(null, null);
            }
            else 
            {
                copy(args[0], args[1]);
            }
        }
        catch (IOException e)
        {
            System.out.println("Caught exception: " + e);
        }
        
        System.exit(0);
    }

    //copy files, test that files exist
    public static void copy(String infile, String outfile) throws IOException
    {
        //variables
        int integersum = 0;
        int uniqueWords = 0;
    
        //get input file if it wasnt given
        File inputfile = null; 
        if (infile == null)
            inputfile = getInputFile();
        else
        {
            inputfile = new File(infile);
            if (!inputfile.exists())
                inputfile = getInputFile();
            else
                inputfile = new File(infile);
        }
    
        // If only the input file was given, outfile is null. This gets the output file
        File outputfile = null;
        if (outfile == null)
            outputfile = getOutputFile(inputfile, outputfile, false);
        else
        {
            outputfile = new File(outfile);
            if (!outputfile.exists())
                outputfile.createNewFile();
            else
                outputfile = getOutputFile(inputfile, outputfile, true);
        }
        
        //begin inputting, splitting, and parsing the input file strings        
        FileInputStream in = new FileInputStream(inputfile);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        
        String line;
        String d = "~!@#$%^&*_+=-`;:,./?|\\\"' ";
        String t;
        //read lines until eof
        while((line = br.readLine())!= null)
        {
            StringTokenizer st = new StringTokenizer(line, d, false);
            //for each string d in array tokens
            while (st.hasMoreTokens())
            {
                t = st.nextToken();
                if (isNumeric(t))
                {
                    integersum += Integer.parseInt(t);
                }
                else
                {
                    if (findit(t))        
                    {
                        continue;
                    }
                    else
                    {
                        words[currentIndex] = new Word(t);
                        currentIndex++;
                        uniqueWords++;
                    }
                }
            }
        }
        br.close();        
        
        //wrtie to outfile
        FileWriter f = new FileWriter(outputfile);
        BufferedWriter out = new BufferedWriter(f);
        
        for (Word w : words)
        {
            if (!(w == null))
            {
                out.write(w.printWord());
                out.newLine();
            }
        }
        out.newLine();
        out.newLine();
        out.write("Sum of integers: " + integersum);
        out.newLine();
        out.write("Total unique words: " + uniqueWords);
        out.close();
        return;
    }
    
    //find a word in the master list of words
    public static boolean findit(String s)
    {
        int i = 0;
        boolean found = false;
        while (i < currentIndex && !found)
        {
            found = words[i++].getName().equals(s);
        }
        i--;
        if (found)    
            words[i].increment();
        return found;
    }
    
    //get the input file if it was not given on the command line
    public static File getInputFile() throws IOException
    {
        boolean invalidinput = true;
        File infile = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do
        {
            System.out.print("\nEnter the filename for the input file: ");
            String response = in.readLine();
            infile = new File(response);
            if (infile.exists()) //if it exists break out of the loop
                invalidinput = false;
            else
            {
                infile.createNewFile();
                invalidinput = false;
            }
        }while(invalidinput);
        return infile;
    }
    
    //get the input file if it was not given on the command line
    public static File getOutputFile(File inputfile, File outputfile, boolean outputExists) throws IOException
    {
        boolean invalidinput = true;
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        String response;
        while(invalidinput)
        {
            if (!outputExists)
            {
                System.out.print("\nEnter the filename for the output file: ");
                response = in.readLine();
                outputfile = new File(response);
            }
            if (outputfile.equals(inputfile))
            {
                System.out.println("\nThe output file given is the same as the input file");
                System.out.println("Please enter a new file name.");
                continue;
            }
            if (!outputfile.exists()) //if it doesnt exist, make it. Also, if it doesnt
                                   //exist, then there is no way it can be equal to inputfile
            {
                outputfile.createNewFile();
                invalidinput = false;
            }
            else //existing output file
            {
                try
                {
                    System.out.println("\nThe file given already exists.");
                    System.out.println("Please choose an option:"); 
                    System.out.println("1) Enter new filename");
                    System.out.println("2) Back up " + outputfile + " and continue");
                    System.out.println("3) Overwrite " + outputfile + " and continue");
                    System.out.println("Exit program");
                    response = in.readLine();
                    int choice = Integer.parseInt(response);
                    
                    switch (choice)
                    {
                        case 1:
                            continue;
                        case 2:
                            System.out.println("A backup of " + outputfile + " has been saved as backup.txt");
                            backup(outputfile);
                            outputfile = clearFile(outputfile);
                            return outputfile;
                        case 3:
                            outputfile = clearFile(outputfile);
                            return outputfile;
                        default:
                            System.out.println("\nGoodbye");
                            System.exit(0);
                    }
                }
                catch (IOException e) {;}    
            }
        }    
        return outputfile;    
    }
    
    //clear a file
    public static File clearFile(File f) throws FileNotFoundException
    {
        PrintWriter w = new PrintWriter(f);
        w.print("");
        w.close();
        return f;
    }
    
    //backup a file
    public static void backup(File f) throws IOException
    {
        File backup = new File("backup.txt");
        backup.createNewFile();
        InputStream in = new FileInputStream(f);
        OutputStream out = new FileOutputStream(backup);
    
        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) 
            out.write(buf, 0, len);

        in.close();
        out.close();       
    }

    //check if a string is an int
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        int d = Integer.parseInt(str);  
      }  
      catch(NumberFormatException e)  
      {  
        return false;  
      }  
      return true;  
    }
}

class Word
{
    private String wordname;
    private int count;

    //constructor
    public Word(String word)
    {
        wordname = word;
        count = 1;
    }
    
    public String printWord()
    {
        return this.wordname + ": " + this.count;
    }

    public void increment()
    {
        this.count++;
        return;
    }

    public int getCount(Word word)
    {
        return this.count;
    }

    public String getName()
    {
        return this.wordname;
    }

    public void setName(String name)
    {
        this.wordname = name;
        return;
    }
}
