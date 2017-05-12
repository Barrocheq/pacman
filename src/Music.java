import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music{
	
	private Clip clip;
	private AudioInputStream stream;
	private Hero myHero;
	
	public Music(Hero h){

		this.myHero = h;


		try {
		    File soundFile = new File("pacman_chomp.wav");
		    this.stream = AudioSystem.getAudioInputStream(soundFile);
		    AudioFormat format = stream.getFormat();
		    DataLine.Info info = new DataLine.Info(Clip.class, format);
		    this.clip = (Clip) AudioSystem.getLine(info);
			this.clip.open(this.stream);


		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void launch() {
		if (this.myHero.getRunMusic()) {
			if(!this.clip.isRunning()) {
				this.clip.start();
				this.clip.setFramePosition(0);
			}
		}
	}

}
