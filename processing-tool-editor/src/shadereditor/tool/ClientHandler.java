package shadereditor.tool;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
	     
	   ServerSocket serverSocket;
	   Runnable clientHandler;
	   String dataPath;
	   
	   InputStream is = null;
	   InputStreamReader isr = null;
	   BufferedReader br = null;	   	
	   DataOutputStream outToClient = null;
	   
	   
	   ClientHandler (String path) {
		   dataPath = path;
	   }
	   
	   private Socket socket = null; //initialize in const'r	 

	   @Override
	   public void run() {
		   
		   try
		    {
		        int port = 25000;
		        if (serverSocket == null) {         
		          serverSocket = new ServerSocket(port);
		          System.out.println("Server started. Waiting for Shader Editor to connect...");
		        }
		        socket = serverSocket.accept();

		        int inputStreamAvailable = 1;
		        
		        while (inputStreamAvailable == 1) {
		        	is = socket.getInputStream();
		        	isr = new InputStreamReader(is);
		        	br = new BufferedReader(isr);
		        	
		        	outToClient = new DataOutputStream(socket.getOutputStream());
		        	
		        	String message = br.readLine();
		        	System.out.println("Shader Editor says hello " + message + "\n");
		        	
		        	outToClient.flush();
		        	outToClient.writeBytes(dataPath);
		        	outToClient.flush();
		        	inputStreamAvailable = is.available();
		        			        	
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
