/**
 * you can put a one sentence description of your tool here.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   ##author##
 * @modified ##date##
 * @version  ##tool.prettyVersion##
 */

package shadereditor.tool;

import processing.app.Base;
import processing.app.tools.Tool;
import processing.app.ui.Editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import processing.app.Sketch;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class ShaderEditor implements Tool {
  Base base;
  Process process;
  
  String[] vertex = {"uniform mat4 transform;","attribute vec4 position;", "attribute vec3 normal;", "void main()", "{", "gl_Position = transform * position;", "}"};
  String[] fragment = {"#ifdef GL_ES", "precision mediump float;", "precision mediump int;", "#endif", "void main()", "{", "  gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);", "}"};
  
  public void loadDefaultShaders(String[] shader, String dataPath) throws IOException {
	  FileWriter fw;
		fw = new FileWriter(dataPath);

	    for (int i = 0; i < shader.length; i++) {
	      fw.write(shader[i] + "\n");
	    }
	    fw.close();
  }
  
  public String getMenuTitle() {
    return "##tool.name##";
  }


  public void init(Base base) {
    // Store a reference to the Processing application itself
    this.base = base;
  }

  public String getDataFolderPath(Sketch s) {
	  String dataFolderPath = null;
	  File dataFolderLoc = s.prepareDataFolder(); 	
  	
  	try {
			dataFolderPath = dataFolderLoc.getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	return dataFolderPath;
  }
  
  public void run() {
    // Get the currently active Editor to run the Tool on it	
    Editor editor = base.getActiveEditor();   
    Sketch sketch = editor.getSketch();
    String fragName = null;
	String vertName = null;

    boolean dataExists = sketch.hasDataFolder();
    String dataPath = getDataFolderPath(sketch);
    
    if (!dataExists) {
    	//editor.handleSave(true);   	

        try {
    		loadDefaultShaders(vertex, dataPath + "//vert.glsl");
    		loadDefaultShaders(fragment, dataPath + "//frag.glsl");
    		fragName = "frag.glsl";
    		vertName = "vert.glsl";
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    } else {
    	File folder = new File(dataPath); //use getFolder
    	File[] shaderList = folder.listFiles();
    	
    	FileReader fr = null;
		try {
			fr = new FileReader(shaderList[0]);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	BufferedReader br=new BufferedReader(fr);
    	String str;
    	    
    	String keyword="gl_FragColor";
   	    
    	try {
			while ((str=br.readLine())!=null){
				if(str.contains(keyword)) {
					fragName = shaderList[0].getName();
					vertName = shaderList[1].getName();
					break;
					
				} else {
					fragName = shaderList[1].getName();
					vertName = shaderList[0].getName();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     	
    }
    
	dataPath = dataPath + ";" + fragName + ";" + vertName + ";";
	//System.out.println("Path being sent is: " + dataPath);
	
	// Start server thread 
    ClientHandler clientHandler =  new ClientHandler(dataPath, sketch, vertName, fragName); // s is the sketch object
    clientHandler.start();


    // Calling the Shdr.exe
    String OS = System.getProperty("os.name").toLowerCase();
    // Get the location of sketchbook tools folder to get Shdr.exe
    String toolFolder = null;
	try {
		toolFolder = Base.getSketchbookToolsFolder().getCanonicalPath();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    
	
    if (process == null) {
    	
    	if (OS.indexOf("win") >= 0) {
    		try {
    			//Edit path for Shdr.exe for windows
    			if (System.getenv("ProgramFiles(x86)") != null) {
    				Process process = new ProcessBuilder(toolFolder + "//ShaderEditor//app//win64//Shdr.exe").start();
    			} else {
    				Process process = new ProcessBuilder(toolFolder + "//ShaderEditor//app//win32//Shdr.exe").start();
    			}
    			
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	} else if (OS.indexOf("mac") >= 0) {
    		
    		try {
    			Process process = new ProcessBuilder(toolFolder + "//ShaderEditor//app//mac//Shdr.app//Contents//MacOS//Shdr").start();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	} 	
    	
    }
    
    
    
    
    
    // Fill in author.name, author.url, tool.prettyVersion and
    // project.prettyName in build.properties for them to be auto-replaced here.
    System.out.println("Shader Editor. ##tool.name## ##tool.prettyVersion## by ##author##");
  }
}





