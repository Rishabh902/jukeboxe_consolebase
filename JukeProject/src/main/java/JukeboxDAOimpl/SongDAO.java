package JukeboxDAOimpl;
import MODEL.Song;
import MODEL.User;
import jukeboxDAO.ISongDAO;
import menu.App;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;
public class SongDAO implements ISongDAO {

    PlaySongDAO playSong=new PlaySongDAO();
    App a=new App();
    User user=new User();

    @Override
    public List<Song> storeSongs(Connection conn) throws SQLException {
        Statement statementObj = conn.createStatement();
        ResultSet rs = statementObj.executeQuery("select * from song limit 11");
        ArrayList<Song> songList = new ArrayList<Song>();
        while (rs.next()) {
            songList.add(new Song(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
        }
        return songList;
    }
    @Override
    public void displaySongs(List<Song> songList) {
        System.out.printf("\n%-10s %-30s %-20s %-20s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
        System.out.println("\n===================================================================================================================================");
        songList.forEach(s->{
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
            System.out.format("%-10s %-30s %-20s %-20s %-20s %-15s\n", s.getSongID(),s.getSongName(),s.getSongArtist(),s.getSongGenre(),s.getSongAlbum(),s.getSongDuration());
        });
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
    }
    @Override
    public void  searchSongs(List<Song> songList) {
        try {
            Scanner scanObj=new Scanner(System.in);
            System.out.println("\n\t\tSearch:\n\t\t1.Artist\n\t\t2.genre \n\t\t3.Album");
            int choice=scanObj.nextInt();
            List<Song> list=new ArrayList<Song>();
            switch(choice) {
                case 1:
                    System.out.print("\n\t\tEnter Artist to Search: ");
                    String artistName=scanObj.next();
                    list=searchByArtist(songList,artistName);
                    if(list.size()>0) {
                        System.out.printf("\n%-10s %-30s %-20s %-15s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
                        System.out.println("\n======================================================================================================================================");
                        list.forEach(s -> {
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.format("%-10s %-30s %-20s %-15s %-20s %-15s\n", s.getSongID(), s.getSongName(), s.getSongArtist(), s.getSongGenre(), s.getSongAlbum(), s.getSongDuration());
                        });
                        playSong.playSongs();
                    }
                    else{
                        System.out.println("try to other songs");
                        System.out.println("this name songs i will update on later");
                        a.songMenu(user.getUsername());
                    }
                    break;
                case 2:
                    System.out.print("\n\t\tSearch by Genre: ");
                    String genreName=scanObj.next();
                    list=searchByGenre(songList,genreName);
                    if(list.size()>0) {
                        System.out.printf("\n%-10s %-30s %-20s %-15s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
                        System.out.println("\n===================================================================================================================================");
                        list.forEach(s -> {
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.format("%-10s %-30s %-20s %-15s %-20s %-15s\n", s.getSongID(), s.getSongName(), s.getSongArtist(), s.getSongGenre(), s.getSongAlbum(), s.getSongDuration());
                        });
                        playSong.playSongs();
                    }
                    else{
                        System.out.println("try to other songs");
                        System.out.println("this name songs i will update on later");
                        a.songMenu(user.getUsername());
                    }

                    playSong.playSongs();
                    break;
                case 3:
                    System.out.print("\n\t\tSearch by artist : ");
                    String albumName=scanObj.next();
                    list=searchByAlbum(songList,albumName);
                    if(list.size()>0) {
                        System.out.printf("\n%-10s %-30s %-20s %-15s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
                        System.out.println("\n===================================================================================================================================");
                        list.forEach(s -> {
                            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
                            System.out.format("%-10s %-30s %-20s %-15s %-20s %-15s\n", s.getSongID(), s.getSongName(), s.getSongArtist(), s.getSongGenre(), s.getSongAlbum(), s.getSongDuration());
                        });
                        playSong.playSongs();
                    }
                    else{
                        System.out.println("try to other songs");
                        System.out.println("this name songs i will update on later");
                        a.songMenu(user.getUsername());
                    }
                    break;
                default:
                    System.out.print("\n\t\tInvalid choice");
            }
        }
        catch(Exception e) {
            System.out.print(e);
        }
    }
    @Override
    public List<Song> searchByArtist(List<Song> songList, String artistName){
        List<Song> list=new ArrayList<Song>();
        list=songList.stream().filter(s->s.getSongArtist().contains(artistName)).collect(Collectors.toList());
        return list;
    }
    @Override
    public List<Song> searchByGenre(List<Song> songList, String genreName) {
        List<Song> list=new ArrayList<Song>();
        list=songList.stream().filter(s->s.getSongGenre().contains(genreName)).collect(Collectors.toList());
        return list;
    }
    @Override
    public List<Song> searchByAlbum(List<Song> songList, String albumName) {
        List<Song> list=new ArrayList<Song>();                         //java array list
        list=songList.stream().filter(s->s.getSongAlbum().contains(albumName)).collect(Collectors.toList());
        return list;

    }
    @Override
    public void close() throws Exception {

    }
}
