package menu;
import JDBC.DatabaseConfig;
import JukeboxDAOimpl.*;
import MODEL.Song;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args )
    {
        try {


            System.out.println("\t\t==========================WELCOME TO THE JUKEBOX==========================");
            System.out.println("\t==========================***************************============================");
            System.out.println("\t\t\t============FIRST CREATE NEW ACCOUNT AND THEN LOGING==============");
            System.out.println("\t                        ***************************                                     ");




            Scanner scanObj=new Scanner(System.in);
            Connection conn = DatabaseConfig.getConnection();
            System.out.println();

            UserDAO user=new UserDAO();
            while(true){
                System.out.println("\n1.Create a new Account");
                System.out.println("\n2.Login Account");
                System.out.println("\nPress Key");

                int choice=scanObj.nextInt();
                if(choice==1) {
                    user.createUser(conn); //Going to create user method
                }
                else if(choice==2) {
                    Scanner s=new Scanner(System.in);
                    System.out.print("\nEnter your username: ");
                    String username=s.next();
                    System.out.print("\nEnter your password: ");
                    String password=s.next();

                    boolean validation=user.checkUsername(conn, username, password); //Going to Check details of User if it is correct
                    if(validation) {
                        songMenu(username);
                    }
                    else {
                        System.out.println("Incorrect username/password");
                    }
                    break;
                }
                else {
                    System.out.print("\nInvalid choice. Please select from 1 or 2.");
                    continue;
                }
            }

            System.out.print("\n\t\tTHANK YOU SEE YOU AGAIN.");

        } catch (Exception e) {
            System.out.println("Sorry for the Inconvenience. We are working on a Solution.");
            System.exit(0);
            //e.printStackTrace();
        }
    }
    public static void songMenu(String username) {
        try {
            Connection conn = DatabaseConfig.getConnection();
            SongDAO song=new SongDAO();
            PodcastDAO podcast=new PodcastDAO();
            PlaylistDAO playlist=new PlaylistDAO();
            PlaySongDAO playSong=new PlaySongDAO();
            System.out.println();
            while(true){
                System.out.println("\n\t\tEnter:\n\t\t1. View All Songs\n\t\t2. Search Songs\n\t\t3. View All Podcast\n\t\t4. Search Podcast\n\t\t5. Create playlist\n\t\t6. View playlist\n\t\t7. Exit");
                int choice=new Scanner(System.in).nextInt();
                switch(choice) {
                    case 1:
                        List<Song> songList=song.storeSongs(conn);
                        song.displaySongs(songList); //Display Songs
                        playSong.playSongs();  //call play song method
                        break;
                    case 2:
                        songList=song.storeSongs(conn); // declaring a list to pass it as a parameter in search song method
                        song.searchSongs(songList);
                        break;
                    case 3:
                        podcast.displayPodcast(conn); //display podcasts
                        break;
                    case 4:
                        podcast.searchPodcast(conn); //method to search through podcasts
                        break;
                    case 5:
                        playlist.createUserPlaylist(conn, username); //to create a playlist username is passed as a parameter
                        break;
                    case 6:
                        playlist.viewSongsInPlaylist(conn,username); //to view the songs in playlist
                        break;
                    case 7:
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice please give again");
                        continue;
                }
            }
        }
        catch(Exception e) {
            e.getMessage();
        }


    }
}
