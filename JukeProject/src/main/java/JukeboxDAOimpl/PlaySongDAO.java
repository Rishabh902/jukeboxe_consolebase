package JukeboxDAOimpl;
import MODEL.Song;
import MODEL.User;
import jukeboxDAO.IPlaySongDAO;
import menu.App;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.List;
import java.util.Scanner;
public class PlaySongDAO implements IPlaySongDAO {

    Long currentFrame;
    Clip clip;
    String status;
    AudioInputStream inputStream;

    User user=new User();
    @Override
    public void playSongs()  {
        try {
            Scanner scanObj=new Scanner(System.in);
            System.out.print("\n\t\tEnter SongID:  ");
            String songID=scanObj.next();

            System.out.print("\t\tPlaying");
            String url="C:\\Users\\hp\\IdeaProjects\\JukeProject\\src\\main\\java\\resource\\"+songID+".wav";

            clip=AudioSystem.getClip();
            File file=new File(url);

            inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

            clip.open(inputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            status = "play";

            while(true) {
                System.out.print("\n\t\t1. Pause");
                System.out.print("\t\t2. Resume");
                System.out.print("\t\t3. Restart");
                System.out.print("\t\t4. Stop");

                int choice=scanObj.nextInt();
                operations(choice, url);
                if(choice==4) {
                    System.out.print("\n\t\tDo You Wish Continue?(Y/N) ");
                    String plays=scanObj.next();
                    if(plays.equalsIgnoreCase("Y")) {
                        App app=new App();
                        app.songMenu(user.getUsername());
                    }
                    else if(plays.equalsIgnoreCase("N")) {
                        System.out.println("Thank You");
                        System.exit(0);
                    }
                }
            }
        }
        catch(Exception e) {
            System.out.println("Song Not Found");
            System.out.print("\n\t\tTHANKYOU");
        }
    }

    @Override
    public void operations(int choice, String url) {
        try {
            switch(choice) {
                case 1:
                    this.currentFrame=this.clip.getMicrosecondPosition();
                    clip.stop();
                    status="paused";
                    break;
                case 2:

                    clip.setMicrosecondPosition(currentFrame);
                    clip.start();
                    status="play";
                    break;

                case 3:
                    clip.stop();
                    clip.close();

                    clip=AudioSystem.getClip();
                    File file=new File(url);

                    inputStream=AudioSystem.getAudioInputStream(file.getAbsoluteFile());

                    clip.open(inputStream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                    clip.start();
                    status = "play";
                    break;
                case 4:
                    currentFrame=0L;
                    clip.stop();
                    clip.close();
                    break;

            }
        }
        catch(Exception e) {
            e.getMessage();
        }
    }


    @Override
    public void playSongsPlaylist(List<Song> songList){try {
        for(int i=0;i<songList.size();i++) {

            String url="C:\\Users\\hp\\IdeaProjects\\JukeProject\\src\\main\\java\\resource\\"+songList.get(i).getSongID()+".wav";
            Clip clip=AudioSystem.getClip();
            File f=new File(url);

            AudioInputStream inputStream=AudioSystem.getAudioInputStream(f.getAbsoluteFile());

            clip.open(inputStream);
            clip.loop(0);
            clip.start();

            System.out.print("\n\t\t"+songList.get(i).getSongName()+"  getting played");
            System.out.print("\n\t\t1. Next");
            System.out.print("\t\t2. Prev");
            System.out.print("\t\t3. Restart");
            System.out.print("\t\t4. Stop");

            int choice=new Scanner(System.in).nextInt();
            while(true) {
                if(clip.isActive()) {

                    if(choice==1) {
                        clip.close();
                        clip.stop();
                        break;
                    } else if(choice==2) {
                        clip.close();
                        clip.stop();
                        i=i-2;
                        break;
                    } else if(choice==3) {
                        clip.close();
                        clip.stop();
                        i=i-1;
                        break;
                    } else if(choice==4) {
                        break;
                    }

                }
                else {
                    clip.close();
                    clip.stop();
                    break;
                }
            }
            if(choice==4) {
                clip.close();
                clip.stop();
                System.out.print("\n\t\tDo you wish to continue?(Y/N) ");
                String listen=new Scanner(System.in).next();
                if(listen.equalsIgnoreCase("Y")) {
                    App app=new App();
                    app.songMenu(user.getUsername());

                    break;
                }
                else if(listen.equalsIgnoreCase("N")) {
                    System.out.print("\n\t\tThanks ");
                    System.exit(0);
                }

            }
        }
    }
    catch(Exception e) {
        System.out.print("\n\t\tJukebox stopped");
    }
    }

    @Override
    public void close() throws Exception {

    }
}
