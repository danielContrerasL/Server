package logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import constant.Constant;
import entity.ClientConnection;

public class Server extends MyThread {

	private ServerSocket serverSocket;
	private ArrayList<ClientConnection> listConnection;
	private boolean isNewClient;
	private volatile String request;

	public Server() throws IOException {
		isNewClient = false;
		request = "";
		listConnection = new ArrayList<>();
		serverSocket = new ServerSocket(3001);
		System.out.println("Server online...");
		new Thread() {
			@Override
			public void run() {
				try {
					while (true) {
						System.out.println("Esperando . . .");
						Socket newConnection = serverSocket.accept();
						listConnection.add(new ClientConnection(newConnection));
						isNewClient = true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		super.start();
	}

	public boolean isNewClient() {
		return isNewClient;
	}

	public ArrayList<ClientConnection> getListConnection() {
		isNewClient = false;
		return listConnection;
	}

	@Override
	protected void executeTask() {
		synchronized (listConnection) {
			request = "";
			for (ClientConnection clientConnection : listConnection) {
				if (clientConnection.getRequest() != null) {
					String[] auxRequest = clientConnection.getRequest();
					request += "Cleinte " + clientConnection.getId() + ", "
							+ auxRequest[0] + ", " + auxRequest[1] + "\n";
				}
			}
		}

	}
	
	public synchronized String getRequest() {
		return request;
	}

	@Override
	protected long getSleep() {
		return Constant.TIME_TO_SLEEP;
	}
}

/**
 * Un cliente se conecta y va a tener dos opciones(botones) una es obtener lista
 * de noticias y la otra obtener un meme aleatorio(imagen), el servidor puede
 * manejar varias conexiones y peticiones con un maximo de 10 por usuario
 * 
 * */
