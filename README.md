# GLSL-Editor-Processing

Instructions to run and test:

1. Download the repo as zip to a local directory and unzip the contents.

2. Launching Shder from command line:
   Open a terminal/console/command prompt, change to the Shdr-master directory. Run the following commands,   

   Install Electron locally:
   npm install --save-dev electron
   
   If you get a permissions issue with the above command try running this:
   sudo npm install electron --unsafe-perm=true
   
   Install Electron globally:
   npm install electron -g
   
   Install Node.js:
   npm install
   
   Launch Shdr:
   npm start
   
   The last command (npm start) should launch Shdr editor as a separate window. Close the Shdr editor.

2. Install Apache Ant

    On Windows and Linux, use the installation instructions(http://ant.apache.org/manual/)
    Or on Ubuntu 16.04, it's just a matter of sudo apt-get install ant
    On macOS, it’s much easier to install via Homebrew or MacPorts(https://stackoverflow.com/questions/3222804/how-can-i-install-apache-ant-on-mac-os-x#3222993).
    Ant 1.8 or later is required.

3. Build the processing-tool-editor project:

    Using Eclipse:
    Follow the necessary download requirements for the tool template [here](https://github.com/processing/processing-tool-template).         After successful compilation you should see "Shader Tool" in the Tools dropdown inside the PDE.

    Using command line:
    Open a terminal/console/command prompt, change to the resources directory inside processing-tool-editor folder, and type and run the     command 'ant'. This will build and install the tool and you should see "Shader Tool" in the Tools dropdown inside the PDE.

4. Launching Processing from command line

   In your terminal/console/command prompt, change to the 'GLSL-Editor-Processing/processing/build' directory and run the following commands:
   
   ant clean
   ant build
   ant run
   
   The last command should launch the PDE.
   
5. Run the sketch by clicking the play button (make sure the sketch is not empty).
   
4. Once the sketch is running, open the 'Shader Tool' from the Tools menu. (Make sure you have either saved your sketch or have an          existing sketch opened before you run the tool or you will be asked to save your sketch.) Successful run should print this in            console: 

    "Server started. Waiting for clients to connect.."

4. Next, in your terminal/console/command prompt, change to the 'GLSL-Editor-Processin/Shdr-master' directory. Type and Run this            command: 

    npm start

  This should launch Shdr as a separate window with a text editor on the left and a model of a 3D monkey on the right. Once that happens   you should see these printed messages:

    PDE's console: 
    "Message received from client is World" 
    
    Command Line:
    "Connected to Server".

5. Make changes to Shdr editor contents. If shader has been called inside the sketch you should see changes applied to PDE rendering        display in real-time

