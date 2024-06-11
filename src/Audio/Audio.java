package Audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class Audio {
    private Clip clip;

    // Constructor to initialize the Audio object with a given file
    public Audio(String fileName) {
        try {
            // Get the input stream for the audio file
            InputStream audioSrc = getClass().getResourceAsStream(fileName);
            // Wrap the input stream in a BufferedInputStream for efficient reading
            InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            // Get an AudioInputStream from the buffered input stream
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            // Get a Clip instance to play the audio
            clip = AudioSystem.getClip();
            // Open the audio stream for playback
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            // Print any exceptions that occur during initialization
            e.printStackTrace();
            clip = null; // Ensure clip is null if loading fails
        }
    }

    // Method to play the audio
    public void play() {
        if (clip != null) {
            clip.stop(); // Stop any previous playback
            clip.setFramePosition(0); // Reset sound to the beginning
            clip.start(); // Play the sound
        }
    }
}
