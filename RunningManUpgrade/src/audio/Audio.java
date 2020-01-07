package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio {
//	private String path;
	private Clip clip;
	
	public Audio() {
		
	}
	
	public void setAudio(String path) {
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(path));
			clip = AudioSystem.getClip();
			clip.open(audio);
		} catch (Exception e) {
			System.out.println("Check " + path);
			e.printStackTrace();
		}
	}
	
	public void playAudio() {
		clip.start();
	}
	
	public void playLoop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.start();
	}
	
	public void stopAudio() {
		clip.stop();
	}

}
