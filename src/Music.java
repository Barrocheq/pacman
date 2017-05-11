import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music extends Thread{
	
	private Clip clip;
	private AudioInputStream stream;
	
	public Music(){

		try {
		    File soundFile = new File("pacman_chomp.wav");
		    this.stream = AudioSystem.getAudioInputStream(soundFile);
		    AudioFormat format = stream.getFormat();
		    DataLine.Info info = new DataLine.Info(Clip.class, format);
		    this.clip = (Clip) AudioSystem.getLine(info);
		    
		    this.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void run(){
		while(true){
			try {
				this.clip.open(this.stream);
			} catch (LineUnavailableException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.clip.start();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.clip.close();
		}
	}
}
