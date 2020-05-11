package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sound {
    static Clip clip;
    private static final String PATH_SWING_SOUND = "src/res/sounds/gather_mine.wav";
    private static final String PATH_BACKGROUND_SOUND = "src/res/sounds/008_jason hayes_daymountain.wav";

    /**
     * plays looped background soundtrack
     */
    public static void playBackground(){
        try {

            File audio = new File(PATH_BACKGROUND_SOUND);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audio);

            clip = AudioSystem.getClip();

            clip.open(audioIn);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-40.0f); // Reduce volume by 15 decibels.
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        } catch(LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stopBackground(){
        clip.stop();
    }
    /**
     * plays chosen audio file effect
     * @param choice variable deciding which file will be played
     */
    //static method playing given effect
    public static void playEffect(int choice){
        try {
            File audio = new File(PATH_SWING_SOUND);
            //chosing audiofile for sword swing
            if(choice == 1){
                audio = new File(PATH_SWING_SOUND);
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audio);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f); // Reduce volume by 15 decibels.
            clip.start();

        }catch(UnsupportedAudioFileException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }catch(LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
