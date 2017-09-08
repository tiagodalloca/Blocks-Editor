const {app, BrowserWindow, remote} = require('electron');
const path = require('path');
const url = require('url');

var win

function createWindow () {
		// Create the browser window.
		win = new BrowserWindow({width: 800, height: 800, fullscreen: false});

		// and load the index.html of the app.
		win.loadURL(url.format({
				pathname: path.join(__dirname, 'index.html'),
				protocol: 'file:',
				slashes: true
		}))

		win.on('closed', () => {
				win = null
		})
}

app.on('ready', createWindow)

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
