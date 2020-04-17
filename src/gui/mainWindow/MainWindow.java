package gui.mainWindow;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import constant.ConstantGui;
import entity.ClientConnection;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {

	private JList<ClientConnection> jList;
	private DefaultListModel<ClientConnection> model;
	private JTextArea jTextArea;
	private String message;

	public MainWindow() {
		setSizeWindow();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(ConstantGui.WINDOW_NAME);
		initList();
		message = "Servidor en linea";
		initTextArea();
	}

	private void initTextArea() {
		jTextArea = new JTextArea(message);
		jTextArea.setEditable(false);
		add(new JScrollPane(jTextArea), BorderLayout.CENTER);
	}

	public void updateTextArea(String info) {
		jTextArea.setText("");
		jTextArea.setText(message += "\n" + info);
	}

	private void initList() {
		model = new DefaultListModel<ClientConnection>();
		jList = new JList<ClientConnection>(model);
		add(new JScrollPane(jList), BorderLayout.WEST);
	}

	public void updateList(ArrayList<ClientConnection> list) {
		model.clear();
		for (ClientConnection clientServer : list) {
			model.addElement(clientServer);
		}
		revalidate();

	}

	private void setSizeWindow() {
		setSize(ConstantGui.MY_WIDTH, ConstantGui.MY_HEIGTH);
		setMinimumSize(getSize());
		setLocationRelativeTo(null);

	}

}
