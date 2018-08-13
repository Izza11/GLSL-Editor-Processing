package shadereditor.tool;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import processing.app.Sketch;

public class ClientHandler extends Thread {
	     
	   ServerSocket serverSocket;
	   String dataPath;
	   
	   InputStream is = null;
	   InputStreamReader isr = null;
	   BufferedReader br = null;	   	
	   DataOutputStream outToClient = null;
	   String fragName;
	   String vertName;
	   Sketch pSketch;
	   boolean saveSketch;
	   
	   ClientHandler (String path, Sketch sketch, String vName, String fName) {
		   serverSocket = null;
		   dataPath = path;
		   pSketch = sketch;
		   vertName = vName;
		   fragName = fName;
		   saveSketch = false;
	   }
	   
	   private Socket socket = null; //initialize in const'r	
	   
	   public void fromClient() throws IOException { // reading message from client i.e Shader Editor
		   is = socket.getInputStream();		   
	       isr = new InputStreamReader(is);
	       br = new BufferedReader(isr);
	       String message = null;
	       message = br.readLine();	
	       System.out.println("Message received from Shdr is " + message);
	   }

	   
	   
	   public void toClient(String data) throws IOException { // writing to client
		   outToClient = new DataOutputStream(socket.getOutputStream());
		   outToClient.flush();
	       outToClient.writeBytes(data);
	       outToClient.flush();
	       System.out.println("toClient has ended");
	   }

	   public void closeConnection() throws IOException {
		   socket.close();
           serverSocket.close();
           is.close();
           isr.close();
           br.close();
           outToClient.close();	
	   }
	   
	   @Override
	   public void run() {
		   
		   try
		    {
		        int port = 25000;
		        try {
			        serverSocket = new ServerSocket(port);
			        System.out.println("Server started. Waiting for Shader to connect...");
		        } catch(IOException e) {
		            // local port cannot be opened, it's in use
		        	System.out.println("Warning: Shader Editor is already running. Close the existing tool first.");
		        	//return;
		        }
		        
		        try {
		        socket = serverSocket.accept();
		        } catch(IOException e) {
		            // local port cannot be opened, it's in use
		        	System.out.println("Warning: Shader Editor is already running. Close the existing tool first.");
		        	//return;
		        }
		        
		        int inputStreamAvailable = 1;
		        
		        boolean finalPath = false;
		        boolean saveSketch = true;
		        
		        while (true) {
		        	
		        	if (!pSketch.isUntitled() && !finalPath) {
		        		//System.out.println("Sketch has been saved!");
		        		try {
		        			fromClient();
		        		} catch(Exception e) {
		        			System.out.println("caught exceptionnnnn");
		        			break;				            
		        		}
		        		if (!saveSketch) {
		        			dataPath = pSketch.getDataFolder().getCanonicalPath();
			        		dataPath = dataPath.replace("\\", "/"); // because javascript requires backward slash
			        		dataPath = dataPath + ";" + fragName + ";" + vertName + ";";	
			        		//System.out.println("sending New path....");

		        		}
		        		dataPath = dataPath + "final!";
		        		//System.out.println("Path sent is " + dataPath);
		        		finalPath = true;
		        		try{
		        			toClient(dataPath);		        			
		        		} catch(Exception e) {
		        			System.out.println("caught ANOTHER exceptionnnnn");
		        			break;				            
		        		}
		        	} else if (saveSketch && !finalPath) {
		        		try {
		        			fromClient();
		        		} catch(Exception e) {
		        			System.out.println("caught exceptionnnnn");
		        			break;				            
		        		}
		        		dataPath = dataPath + "temp!";
		        		//System.out.println("Path sent is " + dataPath);
		        		saveSketch = false;
		        		try{
		        			toClient(dataPath);		        			
		        		} catch(Exception e) {
		        			System.out.println("caught ANOTHER exceptionnnnn");
		        			break;				            
		        		}
		        				        		
		        	} else {
		        		try {
		        			fromClient();
		        		} catch(Exception e) {
		        			System.out.println("caught exceptionnnnn");
		        			break;				            
		        		}
		        		
		        		try{
		        			toClient("wait");		        			
		        		} catch(Exception e) {
		        			System.out.println("caught ANOTHER exceptionnnnn");
		        			break;				            
		        		}
		        	}
		        	
		        	
		        	//inputStreamAvailable = is.available();
		        	//System.out.println("Value of inputStream is " + is.available());
		        			        	
		        }
		    }
		   
		    catch (Exception e)
		    {	            
		        e.printStackTrace();		
		    }
		    finally
		    {
		        try
		        {
		        	System.out.print("Closed everything!!!!");
		        	socket.close();
		            serverSocket.close();
		            is.close();
		            isr.close();
		            br.close();
		            outToClient.close();	
		        }
		        catch(Exception e)
		        {
		        	e.printStackTrace();		        	
		        }
		    }
	       
	    }
	}
