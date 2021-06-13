/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JOptionPane;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 *
 * @author VikyVixxxen
 */
public class Music {

    /**Plays audio
     * @param filepath - path tothe audiofile
     */
    
    public static void playMusic(String filepath){
        InputStream music;
        try{
            music=new FileInputStream(new File(filepath));
            AudioStream audios = new AudioStream(music);
            AudioPlayer.player.start(audios);
            
        }
        catch(IOException e){
            JOptionPane.showMessageDialog(null,"Error");
        }
    }

    /**test main
     *
     * @param args
     */
    public static void main(String[] args) {
        playMusic("Music"+ File.separator+File.separator +"cheer.wav");
    }
    
    
}
