package sound;

import java.util.HashMap;

import javax.sound.sampled.*; //JavaZoom mp3
import javax.swing.*;

/**
	Special class for loading sound file including mp3.
*/
public class SoundClip{

	private final Clip clip;
	private final FloatControl control;
	public SoundClip(String url){
		Clip clip = null;
		try{
			final AudioInputStream in = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(url));
			clip = AudioSystem.getClip();
			if(url.endsWith(".mp3")){
				final AudioFormat baseFormat = in.getFormat();
				clip.open(AudioSystem.getAudioInputStream(new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
																							baseFormat.getSampleRate(),
																							16,
																							baseFormat.getChannels(),
																							baseFormat.getChannels() * 2,
																							baseFormat.getSampleRate(),
																							false),in));
			}else
				clip.open(in);
			in.close();
		}catch(Exception e){
			JOptionPane.showMessageDialog(null,e.toString(),"sound file loading error: " + url,JOptionPane.WARNING_MESSAGE);
		}
		this.clip = clip;
		FloatControl control = null;
		try{
			control = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		}catch(Exception e){}
		this.control = control;
	}
	

	/**
	* Load the sound file.
	* @param url
	* @return SoundID (id of SoundClip array arraySound)
	* @since alpha1.0
	*/
	private static final HashMap<String, SoundClip> soundClipRecords = new HashMap<String, SoundClip>();
	public static final SoundClip loadSound(String url){ //画像読み込みメソッド
		if(soundClipRecords.containsKey(url))
			return soundClipRecords.get(url);
		final SoundClip NEW_CLIP = new SoundClip(url);
		soundClipRecords.put(url, NEW_CLIP);
		return NEW_CLIP;
	}
	
	public SoundClip(){
		clip = null;
		control = null;
	}
	public final void play(){
		if(clip != null)
			clip.start();
	}
	public final void pause(){
		if(clip != null)
			clip.stop();
	}
	public final void stop(){
		if(clip != null){
			clip.stop();
			clip.setFramePosition(0);
		}
	}
	public final void loop(int count){
		if(clip != null)
			clip.loop(count);
	}
	public final void loop(){
		if(clip != null)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public final void setVolume(double volume){
		if(control != null)
			control.setValue((float)Math.log10(volume) * 20);
	}
	public final boolean isRunning(){
		if(clip != null)
			return clip.isRunning();
		else
			return false;
	}
}