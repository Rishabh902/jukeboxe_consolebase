package jukeboxDAO;

import MODEL.Song;

import java.util.List;

public interface IPlaySongDAO extends AutoCloseable{


    void playSongs();

    void operations(int choice, String url);

    void playSongsPlaylist(List<Song> songList);
}
