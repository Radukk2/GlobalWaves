package app.user;

import app.Admin;
import app.audio.Collections.Album;
import app.audio.Collections.Playlist;
import app.audio.Files.Song;
import app.pages.ArtistPage;
import app.player.PlayerSource;
import app.utils.Enums;
import fileio.input.CommandInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;

public class Artist extends SpecialUsers {
    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param age      the age
     * @param city     the city
     */
    private ArrayList<Album> albums = new ArrayList<>();
    @Getter
    private ArtistPage artistPage;

    class Event {
        @Getter
        private String name;
        @Getter
        private String description;
        @Getter
        private String date;
        private static final int LENGTH = 3;
        private static final int LAST = 2023;
        private static final int FIRST = 1900;
        private static final int MONTHS = 12;
        private static final int JANUARY = 31;
        private static final int FEBRUARY = 28;
        private static final int TWO = 2;
        private static final int FOUR = 4;
        private static final int SIX = 6;
        private static final int NINE = 9;
        private static final int ELEVEN = 11;
        private static final int FEB = 29;
        private static final int JUNE = 30;

        Event(final CommandInput commandInput) {
            this.description = commandInput.getDescription();
            this.date = commandInput.getDate();
            this.name = commandInput.getName();
        }

        public final boolean checkDateValidity() {
            String[] dateComponents = this.date.split("-");
            if (dateComponents.length != LENGTH) {
                return false;
            }
            int day = Integer.parseInt(dateComponents[0]);
            int month = Integer.parseInt(dateComponents[1]);
            int year = Integer.parseInt(dateComponents[2]);
            if (year > LAST || year < FIRST) {
                return false;
            }
            if (month > MONTHS) {
                return false;
            }
            if (day > JANUARY) {
                return false;
            }
            if (day > JUNE && (month == FOUR || month == SIX || month == NINE || month == ELEVEN)) {
                return false;
            }
            if (day > FEBRUARY && month == TWO && year % FOUR != 0) {
                return false;
            }
            if (day > FEB && month == TWO) {
                return false;
            }
            return true;
        }
    }

    @Getter
    private ArrayList<Event> events;
    @Getter
    private int totalLikes = 0;

    class Merch {
        @Getter
        private String name;
        @Getter
        private String description;
        @Getter
        private int price;

        Merch(final CommandInput commandInput) {
            this.description = commandInput.getDescription();
            this.price = commandInput.getPrice();
            this.name = commandInput.getName();
        }
    }

    private ArrayList<Merch> merches;

    public Artist(final String username, final int age,
                  final String city, final String type) {
        super(username, age, city, type);
        albums = new ArrayList<>();
        events = new ArrayList<>();
        merches = new ArrayList<>();
        totalLikes = 0;

    }

    public final ArrayList<Album> getAlbums() {
        return albums;
    }

    public final void setAlbums(final ArrayList<Album> albums) {
        this.albums = albums;
    }

    /**
     * adds a new event to the event list
     * @param event given event
     */
    public final void addNewEvent(final Event event) {
        this.events.add(event);
    }

    /**
     * checks validity of the new event
     * @param commandInput the command input
     * @return returns a message depending on the operation's result
     */

    public final String checkValidityy(final CommandInput commandInput) {
        for (Event event : this.events) {
            if (event.name.equals(commandInput.getName())) {
                return commandInput.getUsername() + " has another event with the same name.";
            }
        }
        Event event = new Event(commandInput);
        if (!event.checkDateValidity()) {
            return "Event for " + commandInput.getUsername() + " does not have a valid date.";
        }
        this.addNewEvent(event);
        return commandInput.getUsername() + " has added new event successfully.";
    }

    /**
     * creates the events in a page
     * @return returns the complete page
     */
    public final ArrayList<String> createEventPage() {
        ArrayList<String> fullMessage = new ArrayList<>();
        for (Event event : this.events) {
            String message = new String();
            StringBuilder stringBuilder = new StringBuilder(message);
            stringBuilder.append(event.name);
            stringBuilder.append(" - " + event.date + ":\n\t");
            stringBuilder.append(event.description);
            message = stringBuilder.toString();
            fullMessage.add(message);
        }
        return fullMessage;
    }

    /**
     * searches an album by name
     * @param name album's name
     * @return the album or null if not found
     */
    public final Album getAlbum(final String name) {
        for (Album album : this.albums) {
            if (album.getName().equals(name)) {
                return album;
            }
        }
        return null;
    }

    /**
     * creaates the merch section of an artits's page
     * @return returns the merch section
     */
    public final ArrayList<String> createMerchPage() {
        ArrayList<String> fullMessage = new ArrayList<>();
        for (Merch merch : this.merches) {
            String message = new String();
            StringBuilder stringBuilder = new StringBuilder(message);
            stringBuilder.append(merch.name);
            stringBuilder.append(" - " + merch.price + ":\n\t");
            stringBuilder.append(merch.description);
            message = stringBuilder.toString();
            fullMessage.add(message);
        }
        return fullMessage;
    }

    /**
     * creates the merch product
     * @param commandInput the command input
     * @return a message depending on if the merch was created orn not
     */
    public final String createMerchandise(final CommandInput commandInput) {
        for (Merch merch : this.merches) {
            if (commandInput.getName().equals(merch.getName())) {
                return commandInput.getUsername() + " has merchandise with the same name.";
            }
        }
        if (commandInput.getPrice() < 0) {
            return "Price for merchandise can not be negative.";
        }
        Merch merch = new Merch(commandInput);
        this.merches.add(merch);
        return commandInput.getUsername() + " has added new merchandise successfully.";
    }

    @Override
    public final String remove() {
        ArrayList<String> songs = new ArrayList<>();
        for (Album album : albums) {
            for (Song song : album.getSongs()) {
                songs.add(song.getName());
            }
        }
        for (User user : Admin.getInstance().getUsers()) {
            if (songs.contains(user.getPlayerStats().getName())) {
                return this.getUsername() + " can't be deleted.";
            }
        }
        for (User user : Admin.getInstance().getUsers()) {
            if (user.getSearchBar().getSelectedUser() == null) {
                continue;
            }
            if (!user.getCurrentPage().equals("ArtistPage")) {
                continue;
            }
            if (user.getSearchBar().getSelectedUser().getUsername().equals(this.getUsername())) {
                return this.getUsername() + " can't be deleted.";
            }
        }
        Iterator<Song> iterator = Admin.getInstance().getSongs().iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getArtist().equals(this.getUsername())) {
                iterator.remove();
            }
        }
        for (User user : Admin.getInstance().getUsers()) {
            Iterator<Song> iterator1 = user.getLikedSongs().iterator();
            while (iterator1.hasNext()) {
                Song song = iterator1.next();
                if (song.getArtist().equals(this.getUsername())) {
                    iterator1.remove();
                }
            }
            for (Playlist playlist : user.getPlaylists()) {
                Iterator<Song> iterator2 = playlist.getSongs().iterator();
                while (iterator2.hasNext()) {
                    Song song = iterator2.next();
                    if (song.getArtist().equals(this.getUsername())) {
                        iterator2.remove();
                    }
                }
            }
        }
        Admin.getSpecialUsers().remove(this);
        return this.getUsername() + " was successfully deleted.";
    }

    @Override
    public final String removeAudios(final CommandInput commandInput) {
        Album album = this.getAlbum(commandInput.getName());
        if (album == null) {
            return commandInput.getUsername() + " doesn't have an album with the given name.";
        }
        for (User user : Admin.getInstance().getUsers()) {
            if (user.getPlayer().getSource() == null) {
                continue;
            }
            PlayerSource source = user.getPlayer().getSource();
            if (source.getType() == Enums.PlayerSourceType.ALBUM) {
                if (source.getAudioCollection().getName().equals(album.getName())) {
                    return commandInput.getUsername() + " can't delete this album.";
                }
            }
            if (source.getType() == Enums.PlayerSourceType.LIBRARY) {
                ArrayList<String> songs = new ArrayList<>();
                for (Song song : album.getSongs()) {
                    songs.add(song.getName());
                }
                if (songs.contains(source.getAudioFile().getName())) {
                    return commandInput.getUsername() + " can't delete this album.";
                }
            }
            if (source.getType() == Enums.PlayerSourceType.PLAYLIST) {
                ArrayList<String> songs = new ArrayList<>();
                for (Song song : album.getSongs()) {
                    songs.add(song.getName());
                }
                Playlist playlist = (Playlist) source.getAudioCollection();
                for (Song song : playlist.getSongs()) {
                    if (songs.contains(song.getName())) {
                        return commandInput.getUsername() + " can't delete this album.";
                    }
                }
            }
        }
        for (User user : Admin.getInstance().getUsers()) {
            Iterator<Song> likedSongsIterator = user.getLikedSongs().iterator();
            while (likedSongsIterator.hasNext()) {
                Song song = likedSongsIterator.next();
                if (song.getAlbum().equals(commandInput.getName())) {
                    likedSongsIterator.remove();
                }
            }
            for (Playlist playlist : user.getPlaylists()) {
                Iterator<Song> playlistSongsIterator = playlist.getSongs().iterator();

                while (playlistSongsIterator.hasNext()) {
                    Song song = playlistSongsIterator.next();
                    if (song.getAlbum().equals(commandInput.getName())) {
                        playlistSongsIterator.remove();
                    }
                }
            }
        }
        for (Song song : album.getSongs()) {
            Admin.getInstance().getSongs().remove(song);
        }
        Admin.getAlbums().remove(album);
        return commandInput.getUsername() + " deleted the album successfully.";
    }

    /**
     * gets the event by the name
     * @param eventName event's name
     * @return the event
     */
    public final Event getEvent(final String eventName) {
        for (Event event : this.events) {
            if (event.getName().equals(eventName)) {
                return event;
            }
        }
        return null;
    }

    /**
     * removes an event
     * @param eventName event's name
     * @return a string depending on the validity of the operation
     */
    public final String removeEvent(final String eventName) {
        Event event = this.getEvent(eventName);
        if (event == null) {
            return getUsername() + " doesn't have an event with the given name.";
        }
        Iterator<Event> eventIterator = this.getEvents().iterator();
        while (eventIterator.hasNext()) {
            Event event1 = eventIterator.next();
            if (event1.getName().equals(eventName)) {
                eventIterator.remove();
            }
        }
        return this.getUsername() + " deleted the event successfully.";
    }

    /**
     * gets the number of likes of an album
     */
    public final void getAllArtistLikes() {
        for (Album album : this.albums) {
            for (Song song : album.getSongs()) {
                this.totalLikes += song.getLikes();
            }
        }
    }
}
