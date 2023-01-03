package MODEL;

public class Song {
    String songID;
    String songName;
    String songArtist;
    String songGenre;
    String songAlbum;
    String songDuration;

    public Song(String songID, String songName, String songArtist, String songGenre, String songAlbum, String songDuration) {
        this.songID = songID;
        this.songName = songName;
        this.songArtist = songArtist;
        this.songGenre = songGenre;
        this.songAlbum = songAlbum;
        this.songDuration = songDuration;
    }

    public Song() {

    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public void setSongAlbum(String songAlbum) {
        this.songAlbum = songAlbum;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID='" + songID + '\'' +
                ", songName='" + songName + '\'' +
                ", songArtist='" + songArtist + '\'' +
                ", songGenre='" + songGenre + '\'' +
                ", songAlbum='" + songAlbum + '\'' +
                ", songDuration='" + songDuration + '\'' +
                '}';
    }
}
