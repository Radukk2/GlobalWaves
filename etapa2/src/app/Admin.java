package app;

import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.user.Artist;
import app.user.SpecialUsers;
import app.user.User;
import fileio.input.EpisodeInput;
import fileio.input.PodcastInput;
import fileio.input.SongInput;
import fileio.input.UserInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * The type Admin.
 */
public final class Admin {
    private static List<User> users = new ArrayList<>();
    @Getter
    private static ArrayList<SpecialUsers> specialUsers = new ArrayList<>();
    private static List<Song> songs = new ArrayList<>();
    private static List<Podcast> podcasts = new ArrayList<>();
    @Getter
    private static List<Album> albums = new ArrayList<>();
    private static int timestamp = 0;
    private static final int LIMIT = 5;

    private static Admin instance = null;

    private Admin() {

    }

    /**
     * gets instance for Singleton
     * @return the instance of admin
     */
    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }


    /**
     * Sets users.
     *
     * @param userInputList the user input list
     */

    public void setUsers(final List<UserInput> userInputList) {
        users = new ArrayList<>();
        for (UserInput userInput : userInputList) {
            users.add(new User(userInput.getUsername(), userInput.getAge(), userInput.getCity()));
        }
    }
//    public
    /**
     * Sets songs.
     *
     * @param songInputList the song input list
     */
    public void setSongs(final List<SongInput> songInputList) {
        songs = new ArrayList<>();
        for (SongInput songInput : songInputList) {
            songs.add(new Song(songInput.getName(), songInput.getDuration(), songInput.getAlbum(),
                    songInput.getTags(), songInput.getLyrics(), songInput.getGenre(),
                    songInput.getReleaseYear(), songInput.getArtist()));
        }
    }

    /**
     * a functiont that gets all the artist
     * @return
     */
    public ArrayList<Artist> getArtists() {
        ArrayList<Artist> artists = new ArrayList<>();
        for (SpecialUsers specialUsers1 : specialUsers) {
            if (specialUsers1.getType().equals("artist")) {
                Artist artist = (Artist) specialUsers1;
                artists.add(artist);
            }
        }
        return artists;
    }

    /**
     * Sets podcasts.
     *
     * @param podcastInputList the podcast input list
     */
    public void setPodcasts(final List<PodcastInput> podcastInputList) {
        podcasts = new ArrayList<>();
        for (PodcastInput podcastInput : podcastInputList) {
            List<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episodeInput : podcastInput.getEpisodes()) {
                episodes.add(new Episode(episodeInput.getName(),
                                         episodeInput.getDuration(),
                                         episodeInput.getDescription()));
            }
            podcasts.add(new Podcast(podcastInput.getName(), podcastInput.getOwner(), episodes));
        }
    }

    /**
     * Gets songs.
     *
     * @return the songs
     */
    public List<Song> getSongs() {
        return songs;
    }

    /**
     * Gets podcasts.
     *
     * @return the podcasts
     */
    public List<Podcast> getPodcasts() {
        return podcasts;
    }

    /**
     * Gets playlists.
     *
     * @return the playlists
     */
    public List<Playlist> getPlaylists() {
        List<Playlist> playlists = new ArrayList<>();
        for (User user : users) {
            playlists.addAll(user.getPlaylists());
        }
        return playlists;
    }

    /**
     * Gets user.
     *
     * @param username the username
     * @return the user
     */
    public User getUser(final String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    /**
     * a function that searches a host or an artist by name
     * @param username the user's name
     * @return if the user was found, it returns the user, else returns null
     */
    public SpecialUsers getSpecialUser(final String username) {
        for (SpecialUsers user : specialUsers) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    /**
     * Update timestamp.
     *
     * @param newTimestamp the new timestamp
     */
    public void updateTimestamp(final int newTimestamp) {
        int elapsed = newTimestamp - timestamp;
        timestamp = newTimestamp;
        if (elapsed == 0) {
            return;
        }

        for (User user : users) {
            user.simulateTime(elapsed);
        }
    }

    /**
     * Gets top 5 songs.
     *
     * @return the top 5 songs
     */
    public List<String> getTop5Songs() {
        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort(Comparator.comparingInt(Song::getLikes).reversed());
        List<String> topSongs = new ArrayList<>();
        int count = 0;
        for (Song song : sortedSongs) {
            if (count >= LIMIT) {
                break;
            }
            topSongs.add(song.getName());
            count++;
        }
        return topSongs;
    }

    /**
     * Gets top 5 playlists.
     *
     * @return the top 5 playlists
     */
    public List<String> getTop5Playlists() {
        List<Playlist> sortedPlaylists = new ArrayList<>(getPlaylists());
        sortedPlaylists.sort(Comparator.comparingInt(Playlist::getFollowers)
                .reversed()
                .thenComparing(Playlist::getTimestamp, Comparator.naturalOrder()));
        List<String> topPlaylists = new ArrayList<>();
        int count = 0;
        for (Playlist playlist : sortedPlaylists) {
            if (count >= LIMIT) {
                break;
            }
            topPlaylists.add(playlist.getName());
            count++;
        }
        return topPlaylists;
    }

    /**
     * Reset.
     */
    public void reset() {
        users = new ArrayList<>();
        songs = new ArrayList<>();
        podcasts = new ArrayList<>();
        albums = new ArrayList<>();
        specialUsers = new ArrayList<>();
        timestamp = 0;
    }

    /**
     * function that gets the artists with the most likes
     * @return returns at most 5 artists with the most likes
     */
    public ArrayList<String> getTop5Albums() {
        ArrayList<String> topAlbums = new ArrayList<>();
        for (Album album : Admin.getAlbums()) {
            album.getNumberOfLikes();
        }
        Collections.sort(albums, Comparator
                .comparingInt(Album::getLikes)
                .reversed()
                .thenComparing(Album::getName));
        int size = albums.size();
        if (size > LIMIT) {
            size = LIMIT;
        }
        for (int i = 0; i < size; i++) {
            topAlbums.add(albums.get(i).getName());
        }
        return topAlbums;
    }

    /**
     * helper function that truncates an array list to 5 elements at most
     * @param arrayList given array list
     * @return resulted array list
     */
    public ArrayList<String> top5String(final ArrayList<String> arrayList) {
        if (arrayList.size() <= LIMIT) {
            return arrayList;
        } else {
            ArrayList<String> newArraylist = new ArrayList<>();
            for (int i = 0; i < LIMIT; i++) {
                newArraylist.add(arrayList.get(i));
            }
            return newArraylist;
        }
    }

    /**
     * function that gets the albums with the most likes
     * @return  returns at most 5 albums with the most likes
     */
    public ArrayList<String> getTop5Artists() {
        ArrayList<String> topArtists = new ArrayList<>();
        ArrayList<Artist> artistArrayList = new ArrayList<>();
        for (Artist artist : Admin.getInstance().getArtists()) {
            artist.getAllArtistLikes();
            artistArrayList.add(artist);
        }
        Collections.sort(artistArrayList, Comparator
                .comparingInt(Artist::getTotalLikes)
                .reversed()
                .thenComparing(Artist::getUsername));
        int size = albums.size();
        if (size > LIMIT) {
            size = LIMIT;
        }
        for (int i = 0; i < size; i++) {
            topArtists.add(artistArrayList.get(i).getUsername());
        }
        return topArtists;
    }
}
