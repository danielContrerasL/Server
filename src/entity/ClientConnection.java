package entity;

import java.io.IOException;
import java.net.Socket;

import constant.MyCommand;
import logic.FileManager;

public class ClientConnection extends Connection {

	private static int cont_id = 0;
	private int id;
	private String[] request;
	private FileManager fileManager;
	private String ip;
	private String deafulFolder;

	public ClientConnection(Socket socket) throws IOException {
		super(socket);
		fileManager = new FileManager();
		ip = socket.getInetAddress().toString();
		id = cont_id++;
		deafulFolder = "Default";
	}

	@Override
	void readClientRequest(String[] request) {
		this.request = request;
		if (fileManager.validateFolder(request[1])) {
			try {
				for (String path : fileManager.getFolderPath(request[1])) {
					System.out.println("Buscando ->" + path);
					super.send("hola mundo");
					super.sendImage(path);
					System.out.println("Enviado.");
				}
				super.send(MyCommand.FINISH.getType());
				super.close();
				System.out.println("Flush . . . ");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	@Override
	String[] saveImageInServer(String request) {
		String folderList = "";
		String[] exit = request.split("#");
		exit[0] = deafulFolder;
		fileManager.searchFilesInAFolder();
		for (String folder : fileManager.getFolderList()) {
			folderList += folder + ",";
		}
		for (String hashtag : exit) {
			if (!folderList.contains(hashtag))
				FileManager.createFolder(hashtag);

		}
		return exit;

	}

	public String[] getRequest() {
		return request;
	}

	public int getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	@Override
	public String toString() {
		return "Cliente " + id + ", ip " + ip;
	}

}
