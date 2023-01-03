package JukeboxDAOimpl;
import MODEL.Podcast;
import MODEL.Song;
import MODEL.User;
import jukeboxDAO.IPodcastDAO;
import menu.App;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class PodcastDAO implements IPodcastDAO {

    PlaySongDAO playSong=new PlaySongDAO();
    App a=new App();
    User user=new User();

    @Override
    public List<Podcast> storePodcastInArray(Connection conn) throws SQLException {
        List<Podcast> podcastList=new ArrayList<Podcast>();

        Statement statementObj=conn.createStatement();
        ResultSet rs=statementObj.executeQuery("select * from podcast");

        while(rs.next()) {
            podcastList.add(new Podcast(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
        }


        return podcastList;
    }


    @Override
    public void displayPodcast(Connection conn) throws SQLException{
        try {
            List<Podcast> podcastList=new ArrayList<Podcast>();
            podcastList=storePodcastInArray(conn);
            System.out.printf("\n\t\t%-10s %-20s %-20s %-20s","Podcast ID","Celebrity","Genre","Date");
            System.out.println("\n===================================================================");
            podcastList.forEach(p->{
                System.out.println("--------------------------------------------------------------------");

                System.out.printf("\t\t%-10s %-20s %-15s %-15s\n",p.getPodId(),p.getCelebrity(),p.getGenre(),p.getDateOfPodcast());});
            System.out.print("\nPodcastId=");
            String podcastID=new Scanner(System.in).next();
            displaySongsFromPodcast(conn, podcastID);

        } catch (SQLException e) {
            System.out.print("\n\t\tJukebox Not Responding");
        }

    }

    @Override
    public void searchPodcast(Connection conn) throws SQLException  {
        Scanner scanObj=new Scanner(System.in);
        List<Podcast> podcastList=storePodcastInArray(conn);
        List<Podcast> list;
        try {
            System.out.println("\n\t\tEnter your choice to search from Podcast:\n\t\t1.Celebrity\n\t\t2.Genre\n\t\t3.Date of Podcast");
            int choice=scanObj.nextInt();
            switch(choice) {
                case 1:
                    System.out.print("\n\t\tEnter Artist: ");
                    String Artist=scanObj.next();

                    list=podcastList.stream().filter(p->p.getCelebrity().contains(Artist)).collect(Collectors.toList());

                        System.out.printf("%-10s %-21s %-14s %-15s\n","PodId","Artist","Genre","DateOfPodcast");
                        System.out.println("==========================================================================================");
                        list.forEach(p->{
                            System.out.println("--------------------------------------------------------------------------------------------");

                            System.out.printf("%-10s %-20s %-15s %-15s\n",p.getPodId(),p.getCelebrity(),p.getGenre(),p.getDateOfPodcast());
                        });

                        System.out.print("\nPodcastId=");
                        String podcastID=new Scanner(System.in).next();
                        displaySongsFromPodcast(conn,podcastID);
                case 2:
                    System.out.print("\n\t\tEnter Genre: ");
                    String genre=scanObj.next();
                    list=podcastList.stream().filter(s->s.getCelebrity().contains(genre)).collect(Collectors.toList());
                    if(list.size()!=0){
                        System.out.printf("%-10s %-21s %-14s %-15s\n","PodId","Artist","Genre","DateOfPodcast");
                        System.out.println("=========================================================================================");
                        list.forEach(p->{
                            System.out.println("---------------------------------------------------------------------------------------------");

                            System.out.printf("%-10s %-20s %-15s %-15s\n",p.getPodId(),p.getCelebrity(),p.getGenre(),p.getDateOfPodcast());
                        });

                        System.out.print("\nPodcastId=");
                        String podID=new Scanner(System.in).next();
                        displaySongsFromPodcast(conn,podID);}
                    else{
                        System.out.println("We are working on adding new Podcasts.\n\n");
                        a.songMenu(user.getUsername());
                    }

                    break;
                case 3:
                    System.out.print("\n\t\tEnter Date:(YYYY-MM-DD) ");
                    String date=scanObj.next();
                    list=podcastList.stream().filter(s->s.getCelebrity().contains(date)).collect(Collectors.toList());
                    if(list.size()>0){
                        System.out.printf("%-10s %-21s %-20s %-20s\n","PodId","Artist","Genre","DateOfPodcast");
                        System.out.println("===============================================================================================");
                        list.forEach(p->{
                            System.out.println("----------------------------------------------------------------------------------------------------");

                            System.out.printf("%-10s %-20s %-15s %-15s\n",p.getPodId(),p.getCelebrity(),p.getGenre(),p.getDateOfPodcast());
                        });

                        System.out.print("\nPodcastId=");
                        String poID=new Scanner(System.in).next();
                        displaySongsFromPodcast(conn,poID);}
                    else{
                        System.out.println("We are working on adding new Podcasts.\n\n");
                        a.songMenu(user.getUsername());
                    }

                    break;
                default:
                    System.out.print("\n\t\tInvalid choice");
            }
        } catch (Exception e) {
            System.out.print("\n\t\tJukebox Not Responding");
        }
    }

    public void displaySongsFromPodcast(Connection conn,String podcastID) throws SQLException {
        Statement statementObj = conn.createStatement();

        ResultSet rs=statementObj.executeQuery("select * from song where songId in(select songId from podcastsonglist where PodId='"+podcastID+"')");
        ArrayList<Song> songList=new ArrayList<Song>();
        while(rs.next()) {
            songList.add(new Song(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
        }
        System.out.printf("\n%-10s %-20s %-20s %-15s %-20s %-15s","SongID","Song Name","Artist","Genre","Album","Duration");
        System.out.println("\n====================================================================================================");
        songList.forEach(s->{
            System.out.println("---------------------------------------------------------------------------------------------------------");

            System.out.printf("%-10s %-30s %-20s %-15s %-20s %-15s\n", s.getSongID(),s.getSongName(),s.getSongArtist(),s.getSongGenre(),s.getSongAlbum(),s.getSongDuration());});

        playSong.playSongs();
    }

    @Override
    public void close() throws Exception {

    }
}
