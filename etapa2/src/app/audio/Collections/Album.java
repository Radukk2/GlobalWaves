package app.audio.Collections;

import app.Admin;
import app.audio.Files.AudioFile;
import app.audio.Files.Song;
import fileio.input.CommandInput;
import fileio.input.SongInput;

import java.util.ArrayList;
import java.util.List;

public class Album extends AudioCollection {

    /**
     * Instantiates a new Audio collection.
     *
     * @param name  the name
     * @param owner the owner
     */
    private List<Song> songs = new ArrayList<>();
    private String description;
    private int releaseYear;
    private int likes = 0;
    public Album(final String name, final String owner) {
        super(name, owner);

    }

    @Override
    public final int getNumberOfTracks() {
        return this.songs.size();
    }

    @Override
    public final AudioFile getTrackByIndex(final int index) {
        return songs.get(index);
    }

    public final List<Song> getSongs() {
        return songs;
    }

    public final void setSongs(final List<Song> songs) {
        this.songs = songs;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final int getReleaseYear() {
        return releaseYear;
    }

    public final void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public final int getLikes() {
        return likes;
    }

    public final void setLikes(final int likes) {
        this.likes = likes;
    }

    /**
     * Album constructor and also the function that adds the songs in admin
     * @param commandInput the given command
     */
    public final void configurateAlbum(final CommandInput commandInput) {
        for (SongInput songInput : commandInput.getSongs()) {
            Song song = new Song(songInput.getName(), songInput.getDuration(),
                    songInput.getAlbum(), songInput.getTags(), songInput.getLyrics(),
                    songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist());
            Admin.getInstance().getSongs().add(song);
            this.getSongs().add(song);
        }
        this.setDescription(commandInput.getDescription());
        this.setReleaseYear(commandInput.getReleaseYear());
    }

    /**
     * function that computes the number of likes of an album
     */
    public final void getNumberOfLikes() {
        for (Song song : this.songs) {
            this.likes += song.getLikes();
        }
    }

}
