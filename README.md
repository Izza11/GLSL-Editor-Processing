# GLSL-Editor-Processing

Instructions to run and test:

1. Download the repo as zip to a local directory and unzip the contents.

2. Download[(https://nodejs.org/en/download/)] Node.js if not already installed on your computer.

### Build instructions for Shdr
1. Launching Shdr from command line:
   
   Open a terminal/console/command prompt, change to the Shdr-master directory. Run the following commands,   

   Install Electron locally:
   
         npm install --save-dev electron
   
   If you get a permissions issue with the above command try running this:
   
         sudo npm install electron --unsafe-perm=true
   
   Install Electron globally:
   
         npm install electron -g
   
   Install Node.js:
   
         npm install
     
2. Open and edit the 'index.js' file inside Shdr-master. Set the appropriate path for 'editor.html' in the following function:

         win.loadURL('file:///X:/GLSL-Editor-Processing/Shdr-master/sources/editor.html')
   
3. Launch Shdr using by running this command while in the GLSL-Editor-Processing/Shdr-master directory
   
         npm start
   
   The last command (npm start) should launch Shdr editor as a separate window. Close the Shdr editor.

### Packaging instructions for Shdr

1. Open a terminal/console/command prompt, change to the Shdr-master directory. Type and run this command:

   #### For Windows:
   
         npm install electron-packager --save-dev
         
   And then run this ('--arch=ia32' for Windows 32-bit OR '--arch=x64' for Windows 64-bit):
   
         electron-packager . Shdr --overwrite --asar=true --platform=win32 --arch=ia32 --prune=true --out=release-builds
         
   #### For MacOS:
   
         electron-packager . --overwrite --platform=darwin --arch=x64 --prune=true --out=release-builds
   
   Running this command would create a folder by the name of 'release-builds' inside the Shdr-master directory, which would contain the    Shdr.exe. The path to Shdr.exe needs to be given to "Shader Editor" tool later as you proceed. 

### Build instructions for processing core and processing tool
1. Install Apache Ant

    On Windows and Linux, use the installation instructions[(http://ant.apache.org/manual/)]
    Or on Ubuntu 16.04, it's just a matter of sudo apt-get install ant
    On macOS, it’s much easier to install via Homebrew or MacPorts[(https://stackoverflow.com/questions/3222804/how-can-i-install-apache-ant-on-mac-os-x#3222993)].
    Ant 1.8 or later is required.

2. Build the processing-tool-editor project:

    #### Using Eclipse:
    
    1. Open the processing-tool-editor project in Elipse. 
    
    2. Right click the project in the left panel and go to properties-> Java Build Path-> Libraries. Add external jars(core.jar and            pde.jar) from the lib folder in the processing-tool-editor project directory.
    
    3. Set the appropriate classpath.local.location in build.properties in resources folder. Currently it is set to this:
    
            classpath.local.location=X:/GLSL-Editor-Processing/processing-tool-editor/lib
            
    4. Set the appropriate path to Shdr.exe in ShaderTool.java inside the run() function as shown below 
    
            Process process = new ProcessBuilder("X:\\GLSL-Editor-Processing\\Shdr-master\\release-builds\\Shdr-win32-ia32\\Shdr.exe").start();
            
    5. Compile using Ant:
    
      - From the menu bar, choose Window → Show View → Ant. A tab with the title "Ant" will pop up on the right side of your                     Eclipse editor.
      - Drag the resources/build.xml file in there, and a new item "ProcessingTools" will appear.
      - Press the "Play" button inside the "Ant" tab.
    
    After successful compilation you should see "Shader Editor" in the Tools dropdown inside the PDE.

    #### Using command line:
    
    1. Follow point (2) and (3) under 'Using Eclipse'
    
    2. Open a terminal/console/command prompt, change to the resources directory inside processing-tool-editor folder, and type and run        the command 
    
            'ant' 
    
    This will build and install the tool and you should see "Shader Editor" in the Tools dropdown inside the PDE.

3. Launching PDE (Processing Development Environment) from command line

   In your terminal/console/command prompt, change to the 'GLSL-Editor-Processing/processing/build' directory and run the following        commands:
   
         ant clean
         ant build
         ant run
   
   The last command should launch the PDE.
   
### Testing our shader tool
   
1. Run the PDE sketch by clicking the play button (make sure the sketch is not empty).
   
2. Once the sketch is running, open the 'Shader Tool' from the Tools menu. (Make sure you have either saved your sketch or have an          existing sketch opened before you run the tool or you will be asked to save your sketch.) Successful run should show a separate          display widow and this message printed on the PDE console: 

         "Server started. Waiting for clients to connect.."

3. Launching Shdr - Next, in your terminal/console/command prompt, change to the 'GLSL-Editor-Processin/Shdr-master' directory. Type and    Run this command: 

         npm start

   This should launch Shdr as a separate window with a text editor on the left and a model of a 3D monkey on the right. Once that          happens you should see these printed messages:

    PDE's console: 
    
         "Message received from client is World" 
    
    Command Line:
    
         "Connected to Server".

4. Make changes to Shdr editor contents. If shader has been called inside the sketch you should see changes applied to PDE rendering        display in real-time

