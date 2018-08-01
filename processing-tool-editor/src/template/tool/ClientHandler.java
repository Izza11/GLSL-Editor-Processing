package template.tool;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler extends Thread {
	     
	   ServerSocket serverSocket;
	   Runnable clientHandler;
	   String dataPath;
	   
	   
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
		          System.out.println("Server started. Waiting for clients to connect...");
		        }
		        socket = serverSocket.accept();
		        
		        while (true) {
		        	
		        	
		        	InputStream is = socket.getInputStream();
		        	InputStreamReader isr = new InputStreamReader(is);
		        	BufferedReader br = new BufferedReader(isr);
		        	
		        	DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());
		        	
		        	String message = br.readLine();
		        	System.out.println("Message received from client is " + message + "\n");
		        	
		        	outToClient.flush();
		        	outToClient.writeBytes(dataPath);
		        	outToClient.flush();
		        	
		        	
		        	
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
		        }
		        catch(Exception e){}
		    }


	       
	    }
	}
