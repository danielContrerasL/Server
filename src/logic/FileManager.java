package logic;

import java.io.File;
import java.util.ArrayList;

import constant.Constant;

public class FileManager {

	private File folder;
	private ArrayList<String> folderList;

	public FileManager() {
		folderList = new ArrayList<>();
		new File(Constant.IMAGE_SOURCE_PATH + Constant.IMAGE_SOURCE_DEFAULT).mkdirs();
		folder = new File(Constant.IMAGE_SOURCE_PATH);
		searchFilesInAFolder(folder);
	}

	
	
	public ArrayList<String> getFolderPath(String FolderName) {
		ArrayList<String> aux =  new ArrayList<String>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().equals(FolderName)) {
				for (File paths : fileEntry.listFiles()) {
					aux.add(paths.getPath());
				}
				return aux;
			}
		}
		
		return null;
	}
	
	public boolean validateFolder(String validateThisFolder) {
		searchFilesInAFolder();
		for (String folderName : folderList) {
			if (folderName.equals(validateThisFolder)) {
				return true;
			}
		}
		return false;
		
	}
	
	public void searchFilesInAFolder() {
		folderList.clear();
		searchFilesInAFolder(folder);
	}

	private void searchFilesInAFolder(File folder) {
		System.out.println("   < < < < < < < < < > > > > > > > > > >  ");
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				System.out.println("28- Archivos encontrados en imaSource: " + fileEntry.getName());
				folderList.add(fileEntry.getName());
//				searchFilesInAFolder(fileEntry);
			}
		}
		System.out.println("- - - - - - - - - - - - - - - ");
	}

	public static void createFolder(String folderName) {
		System.out.println("36- FileManager: " + Constant.IMAGE_SOURCE_PATH + folderName);
		File c = new File(Constant.IMAGE_SOURCE_PATH + folderName);
		System.out.println(c.mkdirs());// mkdirs crea carpeta, y retorna un boleano si lo crea
	}

	public ArrayList<String> getFolderList() {
		return folderList;
	}
	
	public static void main(String[] args) {
		new FileManager();
	}
	
}
