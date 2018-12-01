import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;
   
/****************
    Code for running a small simple .wav file.
    All credit goes to Chua Hock-Chuan
    http://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html

    Obtained .wav files from https://freesound.org
 ****************/

// To play sound using Clip, the process need to be alive.
// Hence, we use a Swing application.
public class SoundClip extends JFrame {
   
   // Constructor
   public SoundClip(String file_name) {

      try {
         // Open an audio input stream.
         URL url = this.getClass().getClassLoader().getResource(file_name);
         AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
         // Get a sound clip resource.
         Clip clip = AudioSystem.getClip();
         // Open audio clip and load samples from the audio input stream.
         clip.open(audioIn);
         clip.start();
      } catch (UnsupportedAudioFileException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (LineUnavailableException e) {
         e.printStackTrace();
      }
   }
}