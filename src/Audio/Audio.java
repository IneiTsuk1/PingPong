package Audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Audio {
    private Clip clip;

    public Audio(String fileName) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(fileName);
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            clip = null; // Ensure clip is null if loading fails
        }
    }

    public void play() {
        if (clip != null) {
            clip.stop(); // Stop any previous playback
            clip.setFramePosition(0); // Reset sound to the beginning
            clip.start(); // Play the sound
        }
    }
}
