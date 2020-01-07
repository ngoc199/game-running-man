package entities;

import java.awt.image.BufferedImage;

public class Animation {
	
	// Animation Frames
	private BufferedImage[] frames;
	private int currentFrame;
	
	// Timer
	private long startTime;
	private long delay; // Time Between Each Frame
	
	// Loop
	private boolean playedOnce;
	
	public Animation() {
		playedOnce = false;
	}
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();
		playedOnce = false;
	}
	
	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	public BufferedImage getLastFrame() {
		return frames[frames.length - 1];
	}
	
	public BufferedImage getImage() {
		return frames[currentFrame];
	}
	
	public boolean hasPlayedOnce() {
		return playedOnce;
	}
	
	public void update() {
		
		if (delay <= 0) return;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		
		if (elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		
		if (currentFrame == frames.length - 1) {
			playedOnce = true;
		}
		
		if (currentFrame == frames.length) {
			currentFrame = 0;
		}
		
	}
	
}
