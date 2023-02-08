package application;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Controller implements Initializable {

    @FXML
    private ComboBox<?> changeButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button playButton;

    @FXML
    private Button previousButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label songLabel;

    @FXML
    private Slider volumeSlider;
    
    private int songNumber;
    
    private int[] speeds = {25, 50, 75, 100};
    
    private Timer timer;
    private TimerTask task;
    private boolean running;
    
    private Media media;
    private MediaPlayer mediaPlayer;
    
    private Random randomPlay;
    
    private ArrayList<File> songs;
    
    private String option;
    
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
    	Music musics = new Music();
    	
    	this.songs = musics.getSongs();
    	Random song;
    	
    	//Random play
    	randomPlay = new Random();
    	songNumber = randomPlay.nextInt(songs.size() - 1);
    	this.media = new Media(songs.get(songNumber).toURI().toString());
    	System.out.println(songNumber);
    	
    	//Play Music
    	this.mediaPlayer = new MediaPlayer(this.media);
    	
    	//Change Song title
    	songLabel.setText(songs.get(songNumber).getName());
    	
    	//Play Song
    	mediaPlayer.play();
    	
    	//Progress Bar
    	beginTimer();
    	
    	//Disable Play button(Default)
    	playButton.setDisable(true); //Disable Button
    	
    	//Volume slider
    	volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				// TODO Auto-generated method stub
				mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
				
			}
    		
    	});
    	
    	
	}
    
    @FXML
    private void changeOption(ActionEvent event) {
    	
    	
    	
    }

    @FXML
    public void next(ActionEvent event) {
    	
    	if(songNumber >= songs.size() - 1) {
    		
    		songNumber = 0;
    		
    	}
    	
    	else {
    		
    		songNumber++;
    	}
    	
    	//Stop Current Music for playing
    	mediaPlayer.stop();
    	
    	//Set Progress Bar
    	if(running) {
    		cancelTimer();
    	}
    	
    	this.media = new Media(songs.get(songNumber).toURI().toString());
    	
    	//Play Music
    	this.mediaPlayer = new MediaPlayer(this.media);
    	
    	//Reset Progress Bar
    	beginTimer();
    	
    	//Change Song title
    	songLabel.setText(songs.get(songNumber).getName());
    	
    	//Play Song
    	mediaPlayer.play();
    	
    	//Reset volume
    	mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
    	
    	//Disable Play button(Default)
    	playButton.setDisable(true); //Disable Button
    	
    }

    @FXML
    public void pause(ActionEvent event) {
    	
    	//Set progress Bar
    	cancelTimer();
    	
    	mediaPlayer.pause();
    	pauseButton.setDisable(true); //Disable button
    	playButton.setDisable(false); //Enable Button
    	
    }

    @FXML
    public void play(ActionEvent event) {
    	
    	//Set progress Bar
    	beginTimer();
    	
    	mediaPlayer.play();
    	pauseButton.setDisable(false); //Enable button
    	playButton.setDisable(true); //Disable Button
    	
    }

    @FXML
    public void previous(ActionEvent event) {
    	
    	if(songNumber <= 0) {
    		songNumber = songs.size() - 1;
    	}
    	
    	else {
    		songNumber--;
    	}
    	
    	//Stop Current Music for playing
    	mediaPlayer.stop();
    	
    	//Set Progress Bar
    	if(running) {
    		cancelTimer();
    	}
    	
    	
    	this.media = new Media(songs.get(songNumber).toURI().toString());
    	
    	//Play Music
    	this.mediaPlayer = new MediaPlayer(this.media);
    	
    	//Reset Progress Bar
    	beginTimer();
    	
    	//Change Song title
    	songLabel.setText(songs.get(songNumber).getName());
    	
    	//Play Song
    	mediaPlayer.play();
    	
    	//Reset volume
    	mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
    	
    	//Disable Play button(Default)
    	playButton.setDisable(true); //Disable Button
    	
    }
    
    public void beginTimer() {
    	
    	timer = new Timer();
    	
    	task = new TimerTask() {
    		
    		public void run() {
    			
    			running = true;
    			double current = mediaPlayer.getCurrentTime().toSeconds();
    			double end = media.getDuration().toSeconds();
    			//System.out.println(current/end);
    			progressBar.setProgress(current/end);
    			
    			if(current/end == 1) {
    				cancelTimer();
    			}
    			
    		}
    		
    	};
    	
    	timer.scheduleAtFixedRate(task, 0, 1000);
    	
    }
    
    public void cancelTimer() {
    	
    	running = false;
    	timer.cancel();
    	
    }
    

}
