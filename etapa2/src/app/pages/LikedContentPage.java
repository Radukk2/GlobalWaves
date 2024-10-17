package app.pages;

import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.user.User;

import java.util.ArrayList;

public class LikedContentPage implements UserPages {
    @Override
    public final String displayPage(final User user) {
        String message = new String();
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.append("Liked songs:\n\t");
        ArrayList<String> songs = new ArrayList<>();
        for (Song song : user.getLikedSongs()) {
            StringBuilder stringBuilder1 = new StringBuilder(song.getName());
            stringBuilder1.append(" - " + song.getArtist());
            String newStr = stringBuilder1.toString();
            songs.add(newStr);
        }
        stringBuilder.append(songs);
        stringBuilder.append("\n\nFollowed playlists:\n\t");
        ArrayList<String> playlists = new ArrayList<>();
        for (Playlist playlist : user.getFollowedPlaylists()) {
            StringBuilder stringBuilder1 = new StringBuilder(playlist.getName());
            stringBuilder1.append(" - " + playlist.getOwner());
            String newStr = stringBuilder1.toString();
            playlists.add(newStr);
        }
        stringBuilder.append(playlists);
        message = stringBuilder.toString();
        return message;
    }
}
