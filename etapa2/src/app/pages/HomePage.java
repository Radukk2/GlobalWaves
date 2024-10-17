package app.pages;

import app.Admin;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HomePage implements UserPages {

    @Getter
    private ArrayList<Song> likedSongs = new ArrayList<Song>();
    @Getter
    private ArrayList<Playlist> followedPlaylist = new ArrayList<>();

    @Override
    public final String displayPage(final User user) {
        String message = new String();
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.append("Liked songs:\n\t");
        ArrayList<String> songs = new ArrayList<>();
        ArrayList<Song> songs1 = new ArrayList<>();
        for (Song song : user.getLikedSongs()) {
            songs1.add(song);
        }
        Collections.sort(songs1, Comparator.comparingInt(Song::getLikes).reversed());
        for (Song song : songs1) {
            songs.add(song.getName());
        }
        songs = Admin.getInstance().top5String(songs);
        stringBuilder.append(songs);
        ArrayList<String> playlists = new ArrayList<>();
        stringBuilder.append("\n\nFollowed playlists:\n\t");
        for (Playlist playlist : user.getFollowedPlaylists()) {
            playlists.add(playlist.getName());
        }
        stringBuilder.append(playlists);
        message = stringBuilder.toString();
        return message;
    }
}
