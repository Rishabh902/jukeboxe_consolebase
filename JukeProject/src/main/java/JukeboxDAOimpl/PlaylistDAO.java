package JukeboxDAOimpl;
import MODEL.Podcast;
import MODEL.Song;
import jukeboxDAO.IPlaylistDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlaylistDAO implements IPlaylistDAO {

    PlaySongDAO playSong=new PlaySongDAO();

    @Override
    public String playlistIdCreation(Connection conn) throws SQLException{
        String playlistId="";
        String newPlaylistId;
        Statement statement=conn.createStatement();                // it is used for accessing our databases.
        ResultSet rs=statement.executeQuery("select PlayListId from userplaylist order by PlayListId");
        while(rs.next()) {                                               // asending and desecing order
            // iterate through the results from first to last
            playlistId=rs.getString(1);
        }
        newPlaylistId="PLY"+Integer.toString(Integer.parseInt(playlistId.substring(3,7))+1);
        return newPlaylistId;
    }
    @Override
    public void createUserPlaylist(Connection conn, String username) {
        try {
            String playlistID=playlistIdCreation(conn);
            PreparedStatement psmt=conn.prepareStatement("insert into userplaylist values(?,?,?)");
            psmt.setString(1, playlistID);
            System.out.print("\nEnter Name: ");
            String playlistName=new Scanner(System.in).next();
            psmt.setString(2, playlistName);
            psmt.setString(3, username);
            int row=psmt.executeUpdate();
            insertSongsIntoPlayList(playlistID,conn);
            psmt.close();
            if(row>0) {

                System.out.print("\nPlaylist successfully created.");
            }
            else {
                System.out.print("\nError");
            }
        }
        catch(Exception e) {
            System.out.print("\nCannot Create Playlist");
        }
    }

    @Override
    public void insertSongsIntoPlayList(String playlistID, Connection conn) throws  SQLException {
        Scanner scanObj=new Scanner(System.in);
        SongDAO song=new SongDAO();
        PodcastDAO pod=new PodcastDAO();
        List<Song> songList;
        songList=song.storeSongs(conn);
        song.displaySongs(songList);
        List<Podcast> podcastList;
        podcastList=pod.storePodcastInArray(conn);
        System.out.format("\n\n\n\n%-10s %-20s %-20s %-20s","Podcast ID","Celebrity","Genre","Date");
        System.out.format("\n===========================================================================================");
        podcastList.forEach(p->{
            System.out.println("---------------------------------------------------------------------------------------------------------");
            System.out.format("%-10s %-20s %-15s %-15s\n",p.getPodId(),p.getCelebrity(),p.getGenre(),p.getDateOfPodcast());});
        try {
            while(true){
                System.out.println("\n\nAdd\n1. Song\n2. Podcast\n\3.Exit");
                int choice=scanObj.nextInt();
                if(choice==1) {
                    PreparedStatement psmt=conn.prepareStatement("Insert into playlist (PlayListId,SongId,PodId) values(?,?,?)");
                    psmt.setString(1, playlistID);
                    System.out.print("\n\t\tSongID: ");
                    String songID=scanObj.next();
                    psmt.setString(2, songID);
                    psmt.setString(3, null);
                    int row=psmt.executeUpdate();
                    if(row>0) {
                        System.out.print("\n\nSong added.");
                    }
                    else {
                        System.out.print("\n\n\t\t Not found songs");
                    }
                }
                else if(choice==2) {
                    System.out.print("\nPodcast ID: ");
                    String podcastID=scanObj.next();

                    Statement statementObj=conn.createStatement();
                    ResultSet rs=statementObj.executeQuery("select SongId from podcastsonglist where PodId='"+podcastID+"'");
                    int row = 0;
                    while(rs.next()) {
                        // iterate through the results from first to last
                        PreparedStatement psmt=conn.prepareStatement("Insert into playlist (PlayListId,SongId,PodId) values(?,?,?)");
                        psmt.setString(1, playlistID);
                        psmt.setString(2, rs.getString(1));
                        psmt.setString(3, podcastID);
                        row=psmt.executeUpdate();
                    }
                    if(row>0) {
                        System.out.print("\nPodcast added.");
                    }
                    else {
                        System.out.print("\n\nUnable to add songs at this moment");
                    }
                }
                else if(choice==3) {
                    break;
                }
                else {
                    System.out.print("\n Y or N");
                }
            }
        }
        catch(Exception e) {
            System.out.print("\nInvalid Song Code.");
        }
    }
    @Override
    public List<Song> viewSongsInPlaylist(Connection conn, String username) {
        ArrayList<Song> songList= new ArrayList<>();
        try {
            Statement statementObj=conn.createStatement();
            ResultSet rs=statementObj.executeQuery("select playlistid,playlistname from userplaylist where username='"+username+"'");
            int count=0;
            while(rs.next()) {
                System.out.print("\n Playlist ID= "+rs.getString(1)+", Name= "+rs.getString(2));
                count++;
            }
            if(count!=0) {
                System.out.print("\n\nEnter Playlist ID: ");
                String playlistId=new Scanner(System.in).next();
                ResultSet rs2=statementObj.executeQuery("select * from song where songId in(select songId from PlayList  where PlaylistId  in (select playlistId from userplaylist where username='"+username+"' AND playlistId='"+playlistId+"'))");

                while(rs2.next()) {
                    songList.add(new Song(rs2.getString(1),rs2.getString(2),rs2.getString(3),rs2.getString(4),rs2.getString(5),rs2.getString(6)));
                }
                System.out.format("\n%-10s %-30s %-20s %-15s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
                System.out.println("\n===========================================================================================================");
                songList.forEach(s->System.out.format("%-10s %-30s %-20s %-15s %-20s %-15s\n", s.getSongID(),s.getSongName(),s.getSongArtist(),s.getSongGenre(),s.getSongAlbum(),s.getSongDuration()));
                playSong.playSongsPlaylist(songList);
            }
            //if(count==0) {
              //  System.out.print("Create a playlist before trying again");
                //createUserPlaylist(conn,username);
            //}

        }
        catch(SQLException se) {
            System.out.print("\n\t\tUnable to fetch songs at this moment.");
        }
        return songList;
    }

    @Override
    public void close() throws Exception {

    }
}
