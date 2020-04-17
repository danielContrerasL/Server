package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;

import constant.Constant;
import constant.MyCommand;

public abstract class Connection {

	private Socket socket;
	private DataInputStream input;
	private DataOutputStream output;
	private Thread thread;

	public Connection(Socket socket) throws IOException {
		this.socket = socket;
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		startConnection();
	}

	public Connection(String ip, int port) throws IOException {
		this.socket = new Socket(ip, port);
		input = new DataInputStream(socket.getInputStream());
		output = new DataOutputStream(socket.getOutputStream());
		startConnection();
	}

	public void send(String data) throws IOException {
		output.writeUTF(data);
	}
	
	public String readResponse() throws IOException {
		return input.readUTF();
	}

	public void close() throws IOException {
		output.close();
		input.close();
		socket.close();
	}

	private void startConnection() {
		thread = new Thread(new Runnable() {
			@Override
			public void run() {
				String request = "";
				while (true) {
					try {
						request = readResponse();
						if (request.contains(MyCommand.PUBLISH_IMAGE.getType())) {
							saveImage(request);
						} else {
							String[] imageRequest = { request, readResponse() };
							readClientRequest(imageRequest);
						}
					} catch (IOException e) {
					}
				}

			}

			private void saveImage(String request) throws IOException {
				String[] folderList = request.split(";");
				folderList = saveImageInServer(folderList[1]);
				BufferedImage ima = ImageIO.read(ImageIO.createImageInputStream(socket.getInputStream()));
				paintMiImage(ima);
				String date = Calendar.getInstance().getTime().toString()
						.replace(' ', '_');
				date = date.replace(':', '_');
				String ip = socket.getInetAddress().toString().substring(1).replace('.', '_');
				String name = "";
				for (String folder : folderList) {
					name = folder + "_" + date + "_" + ip + ".jpg";
					File out = new File(Constant.IMAGE_SOURCE_PATH+ folder + "/" + name);
					ImageIO.write(ima, "jpg", out);
				}
			}

		});
		thread.start();
	}
	
	
	protected void sendImage(String path) {
		Image image = new ImageIcon(path).getImage();

		BufferedImage bImage = toBufferedImage(image);

		RenderedImage rImage = (RenderedImage) bImage;
		try {
			ImageIO.write(rImage, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = bimage.createGraphics();
		g2.drawImage(img, 0, 0, null);
		g2.dispose();
		return bimage;
	}
	
	private void paintMiImage(BufferedImage bi) {
		JDialog d = new JDialog(){
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawImage(bi, 0, 0, 600,600,this);
			}
		};
		d.setSize(600, 600);
		d.setTitle("Soy el servidor");
		d.setLocationRelativeTo(null);
		d.setVisible(true);
		
	}

	abstract void readClientRequest(String[] request);

	abstract String[] saveImageInServer(String request);

}