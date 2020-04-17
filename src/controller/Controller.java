package controller;

import gui.mainWindow.MainWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.SwingWorker;

import constant.Constant;
import logic.Server;

public class Controller implements ActionListener{
	
	private MainWindow mainWindow;
	private Server server;
	
	public Controller() {
		try {
			server = new Server();
			mainWindow = new MainWindow();
			startThread();
			mainWindow.setVisible(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void startThread() {
		new SwingWorker<Void, Void>() {
			@Override
			protected Void doInBackground() throws Exception {
				while (true) {
					Thread.sleep(Constant.TIME_TO_SLEEP);
					mainWindow.updateTextArea(server.getRequest());
					if (server.isNewClient()) 
						mainWindow.updateList(server.getListConnection());
				}
			}
		}.execute();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
