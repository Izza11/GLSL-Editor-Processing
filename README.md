# GLSL-Editor-Processing

Instructions to run and test:

1. Download all the folders in the same directory

2. Build the processing-tool-editor project using eclipse. Before that, follow the necessary download requirements for the tool template [here](https://github.com/processing/processing-tool-template). After successful compilation you should see "Hello Tool" in the Tools dropdown inside the PDE.

3. Click the Hello Tool to start it. Successful run should print this in console: "Server started. Waiting for clients to connect.."

4. Next, open the command prompt and get inside the directory called "Shdr-master". Type and Run the command "electon ." This should build and run the Shdr.exe application. You should see another window open titled Shdr with a text editor on the left and a model of a 3D monkey on the right. Once that happens you should see another message printed on PDE's console: "Message received from client is World" and a message on the cmd "Connected to Server".

5. Make changes to Shdr editor contents and save the file to the "data" folder inside the "P3D-test" folder.

