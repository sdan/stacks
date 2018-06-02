/**
 * Utilities for opening a text file. Files will be found and read from,
 * and can be created amd wrttien to.
 *
 * @author Surya Dantuluri
 * @version 1.0
 * @since June 1, 2018
 *
 */
 import java.util.Scanner;
 import java.io.File;
 import java.io.FileNotFoundException;
 import java.io.PrintWriter;

 public class ServerConfig
 {

     String defaultServerConfigDirectory;
     String newServerConfigDirectory;
     String serverBlockName;
     String baseName;
     String portForward;
     String portNumber;

     /**
     * 0 @param DEFAULT_SERVER_CONFIG_DIRECTORY
     * 1 @param NEW_SERVER_CONFIG_DIRECTORY
     * 2 @param SERVER_BLOCK_NAME
     * 3 @param BASE_NAME
     * 4 @param REVERSE_PROXY
     * 5 @param PORT_NUMBER
     */
    public static void main(String[]args)
    {
        ServerConfig nginx = new ServerConfig(args[0],args[1],args[2],args[3],args[4],args[5]);
        if(args[4].equals("false"))
            nginx.proxy();
        else
            nginx.reverseProxy();
    }

    public ServerConfig(String d, String n, String s, String b, String p, String r)
    {
        defaultServerConfigDirectory = d;
        newServerConfigDirectory = n;
        serverBlockName = s;
        baseName = b;
        portForward = p;
        portNumber = r;
    }

    public void proxy()
    {
        Scanner fromfile = openToRead(defaultServerConfigDirectory);
        PrintWriter outfile = openToWrite(newServerConfigDirectory);

        String temp = null;
        while(fromfile.hasNext())
        {
            temp = fromfile.nextLine();
            if(temp.contains("root"))
                temp = "root /var/www/"+serverBlockName+"."+baseName+"/html;";
            else if(temp.contains("server_name"))
                temp = "server_name "+serverBlockName+"."+baseName+" www."+serverBlockName+"."+baseName+";";

            System.out.println(temp);
            outfile.println(temp);
        }
        fromfile.close();
        outfile.close();
    }

     public void reverseProxy()
     {
         Scanner fromfile = openToRead(defaultServerConfigDirectory);
         PrintWriter outfile = openToWrite(newServerConfigDirectory);


         String temp = null;


         while(fromfile.hasNext())
         {
             temp = fromfile.nextLine();
             if(temp.contains("location"))
                break;
             if(temp.contains("root"))
                 temp = "root /var/www/"+serverBlockName+"."+baseName+"/html;";
             else if(temp.contains("server_name"))
                 temp = "server_name "+serverBlockName+"."+baseName+" www."+serverBlockName+"."+baseName+";";

             System.out.println(temp);
             outfile.println(temp);
         }

         int num = Integer.parseInt(portNumber);
         System.out.println("PORT NUMBER: "+num);

         outfile.println(" location / {");
         outfile.println("proxy_pass http://localhost:"+num);
         outfile.println("proxy_http_version 1.1");
         outfile.println("proxy_set_header Upgrade $http_upgrade");
         outfile.println("proxy_set_header Connection 'upgrade'");
         outfile.println("proxy_set_header Host $host");
         outfile.println("proxy_cache_bypass $http_upgrade");
         outfile.println("}");
         outfile.println("}");

         fromfile.close();
         outfile.close();

     }


     /**
      * Open a file for reading.
      * @param filestring     The name of the file to be opened
      * @return               A Scanner instance of a file to be open.
      */
     public Scanner openToRead(String filestring){
        Scanner fromfile = null;
        File filename = new File(filestring);
        try{
            fromfile = new Scanner(filename);
        }catch(FileNotFoundException e){
            System.out.println("\n\nSorry the file could not be found.\n\n");
            System.exit(1);
        }
        return fromfile;
     }
     /**
      * Open a file for writing.
      * @param filestring     The name of the file to be opened to be created.
      * @return               A PrintWriter instance of a file to be opened(created).
      */
     public PrintWriter openToWrite(String filestring){
         PrintWriter outfile = null;
        try{
            outfile = new PrintWriter(filestring);
        }catch(Exception e){
            System.out.println("\n\nSorry the file could not be created.\n\n");
            System.exit(2);
        }
        return outfile;
     }

 }
