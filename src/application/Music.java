package application;

import java.io.File;
import java.util.ArrayList;

public class Music {
	
	private File directory;
	private File[] files;
	
	private ArrayList<File> songs;
	
	Music() {
		
		this.songs = new ArrayList<File>();
		
		directory = new File("Music");
		
		files = directory.listFiles();
		
		if(files != null) {
			
			for(File file : files ) {
				this.songs.add(file);
				//System.out.println("File added");
			}
			
		}
		
		
		
	}
	
	/**
	 * Returns an ArrayList of songs;
	 * @return
	 */
	ArrayList<File> getSongs() {
		
		return this.songs;
		
	}
	
}
