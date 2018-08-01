const {app, BrowserWindow, ipcMain} = require('electron')
const path = require('path')
const url = require('url')
var f = require('fs')

// Keep a global reference of the window object, if you don't, the window will
// be closed automatically when the JavaScript object is garbage collected.
let win

function createWindow () {
  // Create the browser window.
  win = new BrowserWindow({width: 800, height: 600})

  
  win.webContents.on('did-finish-load', () => {
    win.webContents.send('send counter value', 'Hello renderer!');
  });
  
  // and load the index.html of the app.
  win.loadURL('file:///X:/GLSL-Editor-Processing/Shdr-master/sources/editor.html')



  // Open the DevTools.
  //win.webContents.openDevTools()

  // Emitted when the window is closed.
  win.on('closed', () => {
    // Dereference the window object, usually you would store windows
    // in an array if your app supports multi windows, this is the time
    // when you should delete the corresponding element.
    win = null
  })
}

ipcMain.on('counter value sent', (event, arg) => {

    console.log("Value of counter is : " + arg);
    //event.sender.send('send counter value', 0);

})



// This method will be called when Electron has finished
// initialization and is ready to create browser windows.
// Some APIs can only be used after this event occurs.
app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', () => {
  // On macOS it is common for applications and their menu bar
  // to stay active until the user quits explicitly with Cmd + Q
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  // On macOS it's common to re-create a window in the app when the
  // dock icon is clicked and there are no other windows open.
  if (win === null) {
    createWindow()
  }
})

// In this file you can include the rest of your app's specific main process
// code. You can also put them in separate files and require them here.



//////////// IPC///////////////////////////////////////


const net = require('net')

const client = net.createConnection({ port: 25000 }, () => {
  // 'connect' listener
  console.log('connected to server!');
  client.write('world!\r\n');
});


var recPath = "";
var sendPath = false;

client.on('data', function(data) {

  var d = data.toString();

  if (d.charAt(d.length - 1) != "!") {   // checking that entire message has been received and not broken as it usually breaks
    recPath += d;
  } else {
    recPath += d;
    sendPath = true;
    console.log('Message is : ' + recPath);

  }
  
  
    //client.write('DATA: ' + data);
    // Close the client socket completely
    //client.destroy();
    
});

ipcMain.on('send shader path', (event, arg) => {

  if (sendPath) {
    event.sender.send('shader path sent', recPath);
    sendPath = false;
    console.log("Sending path to renderer: " + recPath);

  } 

})

ipcMain.on('print', (event, arg) => {

  console.log(arg);

})


client.on('end', () => {
  console.log('disconnected from server');
});
client.on('error', function(ex) {
  console.log("handled error");
  console.log(ex);
});

client.on('error', () => console.log('errored'));






