package app;

import app.audio.Collections.*;
import app.audio.Files.Episode;
import app.audio.Files.Song;
import app.pages.HomePage;
import app.pages.LikedContentPage;
import app.player.PlayerStats;
import app.searchBar.Filters;
import app.user.Artist;
import app.user.Host;
import app.user.SpecialUsers;
import app.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.input.CommandInput;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Command runner.
 */
public final class CommandRunner {
    /**
     * The Object mapper.
     */
    private static ObjectMapper objectMapper = new ObjectMapper();

    private CommandRunner() {
    }

    /**
     * Search object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode search(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        Filters filters = new Filters(commandInput.getFilters());
        String type = commandInput.getType();
        if (user == null) {
            return null;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (user.isOnline()) {
            ArrayList<String> results = user.search(filters, type);
            String message = "Search returned " + results.size() + " results";
            objectNode.put("message", message);
            objectNode.put("results", objectMapper.valueToTree(results));
        } else {
            objectNode.put("message", user.getUsername() + " is offline.");
            ArrayList<String> results = new ArrayList<String>();
            objectNode.put("results", objectMapper.valueToTree(results));
        }
        return objectNode;
    }

    /**
     * Select object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode select(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.select(commandInput.getItemNumber());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Load object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode load(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.load();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Play pause object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode playPause(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.playPause();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Repeat object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode repeat(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.repeat();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Shuffle object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode shuffle(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        Integer seed = commandInput.getSeed();
        String message = user.shuffle(seed);

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Forward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode forward(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.forward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Backward object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode backward(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.backward();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Like object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode like(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        if (!user.isOnline()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
        } else {
            String message = user.like();
            objectNode.put("message", message);
        }

        return objectNode;
    }

    /**
     * Next object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode next(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.next();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Prev object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode prev(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.prev();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Create playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode createPlaylist(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.createPlaylist(commandInput.getPlaylistName(),
                                             commandInput.getTimestamp());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Add remove in playlist object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addRemoveInPlaylist(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.addRemoveInPlaylist(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Switch visibility object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode switchVisibility(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String message = user.switchPlaylistVisibility(commandInput.getPlaylistId());

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Show playlists object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showPlaylists(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        ArrayList<PlaylistOutput> playlists = user.showPlaylists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Follow object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode follow(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        String message = user.follow();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("message", message);

        return objectNode;
    }

    /**
     * Status object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode status(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        PlayerStats stats = user.getPlayerStats();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("stats", objectMapper.valueToTree(stats));

        return objectNode;
    }

    /**
     * Show liked songs object node.
     *
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode showLikedSongs(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        ArrayList<String> songs = user.showPreferredSongs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets preferred genre.
     *
     * @param commandInput the command input
     * @return the preferred genre
     */
    public static ObjectNode getPreferredGenre(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        String preferredGenre = user.getPreferredGenre();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("user", commandInput.getUsername());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(preferredGenre));

        return objectNode;
    }

    /**
     * Gets top 5 songs.
     *
     * @param commandInput the command input
     * @return the top 5 songs
     */
    public static ObjectNode getTop5Songs(final CommandInput commandInput) {
        List<String> songs = Admin.getInstance().getTop5Songs();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(songs));

        return objectNode;
    }

    /**
     * Gets top 5 playlists.
     *
     * @param commandInput the command input
     * @return the top 5 playlists
     */
    public static ObjectNode getTop5Playlists(final CommandInput commandInput) {
        List<String> playlists = Admin.getInstance().getTop5Playlists();

        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(playlists));

        return objectNode;
    }

    /**
     * Switches a user to online/offline
     * @param commandInput the command input
     * @return the object node
     */
    public static  ObjectNode switchConnectionStatus(final CommandInput commandInput) {
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (user == null) {
            if (specialUsers == null) {
                objectNode.put("message", "The username "
                        + commandInput.getUsername() + " doesn't exist.");
            } else {
                objectNode.put("message", commandInput.getUsername()
                        + " is not a normal user.");
            }
        } else {
            objectNode.put("message", commandInput.getUsername()
                    + " has changed status successfully.");
            user.activateDeactivateUser();
        }
        return objectNode;
    }

    /**
     * shows all users with online status true
     * @param commandInput the command input
     * @return the online users
     */
    public static  ObjectNode getOnlineUsers(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        List<User> users = Admin.getInstance().getUsers();
        ArrayList<String> onlineUsers = new ArrayList<>();
        for (User user : users) {
            if (user.isOnline()) {
                onlineUsers.add(user.getUsername());
            }
        }
        objectNode.put("result", objectMapper.valueToTree(onlineUsers));
        return objectNode;
    }

    /**
     * Adds a default user, artist or host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addUser(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user != null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " is already taken.");
            return objectNode;
        }
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers != null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " is already taken.");
            return objectNode;
        }
        objectNode.put("message", "The username "
                + commandInput.getUsername() + " has been added successfully.");
        if (commandInput.getType().equals("artist")) {
            Artist newUser = new Artist(commandInput.getUsername(),
                    commandInput.getAge(), commandInput.getCity(), commandInput.getType());
            Admin.getInstance().getSpecialUsers().add(newUser);
        }
        if (commandInput.getType().equals("host")) {
            Host newUser = new Host(commandInput.getUsername(),
                    commandInput.getAge(), commandInput.getCity(), commandInput.getType());
            Admin.getInstance().getSpecialUsers().add(newUser);
        }
        if (commandInput.getType().equals("user")) {
            User user1 = new User(commandInput.getUsername(), commandInput.getAge(),
                    commandInput.getCity());
            Admin.getInstance().getUsers().add(user1);
        }
        return objectNode;
    }

    /**
     * Adds an album
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAlbum(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers == null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }
        if (user != null || (user == null && specialUsers.getType().equals("host"))) {
            objectNode.put("message", commandInput.getUsername() + " is not an artist.");
            return objectNode;
        }
        Artist artist = (Artist) specialUsers;
        for (Album album : artist.getAlbums()) {
            if (album.getName().equals(commandInput.getName())) {
                objectNode.put("message", commandInput.getUsername()
                        + " has another album with the same name.");
                return objectNode;
            }
        }
        for (int i = 0; i < commandInput.getSongs().size(); i++) {
            for (int j = 0; j < commandInput.getSongs().size(); j++) {
                if (commandInput.getSongs().get(i).getName()
                        .equals(commandInput.getSongs().get(j).getName()) && i != j) {
                    objectNode.put("message", commandInput.getUsername()
                            + " has the same song at least twice in this album.");
                    return objectNode;
                }
            }
        }
        Album newAlbum = new Album(commandInput.getName(), commandInput.getUsername());
        newAlbum.configurateAlbum(commandInput);
        objectNode.put("message", commandInput.getUsername()
                + " has added new album successfully.");
        artist.getAlbums().add(newAlbum);
        Admin.getInstance().getAlbums().add(newAlbum);
        return objectNode;
    }

    /**
     * Shows all the albums of a given user
     * @param commandInput the command input
     * @return the albums
     */
    public static ObjectNode showAlbums(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers == null) {
            return null;
        }
        if (!specialUsers.getType().equals("artist")) {
            return null;
        }
        Artist artist = (Artist) specialUsers;
        List<Album> albums = artist.getAlbums();
        ArrayList<AlbumOutput> result = new ArrayList<AlbumOutput>();
        for (Album album : albums) {
            AlbumOutput albumOutput = new AlbumOutput();
            albumOutput.setName(album.getName());
            for (Song songInput : album.getSongs()) {
                albumOutput.getSongs().add(songInput.getName());
            }
            result.add(albumOutput);
        }
        objectNode.put("result", objectMapper.valueToTree(result));
        return objectNode;
    }

    /**
     * Gets the current page of the user and shows it
     * @param commandInput the command input
     * @return the page
     */
    public static ObjectNode printCurrentPage(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null) {
            return null;
        }
        if (!user.isOnline()) {
            objectNode.put("message", commandInput.getUsername() + " is offline.");
            return objectNode;
        }
        if (user.getCurrentPage().equals("ArtistPage")) {
            String message = user.printSpecialPages();
            objectNode.put("message", message);
            return objectNode;
        }
        if (user.getCurrentPage().equals("Home")) {
            HomePage homePage = new HomePage();
            objectNode.put("message", homePage.displayPage(user));
            return objectNode;
        }
        if (user.getCurrentPage().equals("LikedContent")) {
            LikedContentPage likedContentPage = new LikedContentPage();
            objectNode.put("message", likedContentPage.displayPage(user));
            return objectNode;
        }
        return objectNode;
    }

    /**
     * Adds an event
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addEvent(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (specialUsers == null && user == null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }
        if (user != null || !specialUsers.getType().equals("artist")) {
            objectNode.put("message", commandInput.getUsername() + " is not an artist.");
            return objectNode;
        }
        Artist artist = (Artist) specialUsers;
        objectNode.put("message", artist.checkValidityy(commandInput));
        return objectNode;
    }

    /**
     * Adds merch
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addMerch(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (specialUsers == null && user == null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }
        if (user != null || !specialUsers.getType().equals("artist")) {
            objectNode.put("message", commandInput.getUsername() + " is not an artist.");
            return objectNode;
        }
        Artist artist = (Artist) specialUsers;
        objectNode.put("message", artist.createMerchandise(commandInput));
        return objectNode;
    }

    /**
     * Shows all users
     * @param commandInput the command input
     * @return all the users
     */
    public static ObjectNode getAllUsers(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        ArrayList<String> results = new ArrayList<>();
        for (User user : Admin.getInstance().getUsers()) {
            results.add(user.getUsername());
        }
        for (SpecialUsers specialUsers : Admin.getInstance().getSpecialUsers()) {
            if (specialUsers.getType().equals("artist")) {
                results.add(specialUsers.getUsername());
            }
        }
        for (SpecialUsers specialUsers : Admin.getInstance().getSpecialUsers()) {
            if (specialUsers.getType().equals("host")) {
                results.add(specialUsers.getUsername());
            }
        }
        objectNode.put("result", objectMapper.valueToTree(results));
        return objectNode;
    }

    /**
     * Deletes a user, if possible
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode deleteUser(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user != null) {
            objectNode.put("message", user.deleteUser());
            return objectNode;

        }
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers != null) {
            if (specialUsers.getType().equals("artist")) {
                Artist artist = (Artist) specialUsers;
                artist.remove();
                objectNode.put("message", artist.remove());
                return objectNode;
            }
            if (specialUsers.getType().equals("host")) {
                Host host = (Host) specialUsers;
                host.remove();
                objectNode.put("message", host.remove());
                return objectNode;
            }
        }
        objectNode.put("message", "The username "
                + commandInput.getUsername() + " doesn't exist.");
        return objectNode;
    }

    /**
     * Adds a podcast to a host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addPodcast(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (user == null && specialUsers == null) {
            objectNode.put("message", "The username "
                    + commandInput.getUsername() + " doesn't exist.");
            return objectNode;
        }
        if (user != null || !specialUsers.getType().equals("host")) {
            objectNode.put("message", commandInput.getUsername() + " is not a host.");
            return objectNode;
        }
        Host host = (Host) specialUsers;
        String message = host.fixPodcast(commandInput);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Adds an announcement to a host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode addAnnouncement(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (specialUsers == null) {
            if (user == null) {
                objectNode.put("message", "The username "
                        + commandInput.getUsername() + " doesn't exist.");
            } else {
                objectNode.put("message", commandInput.getUsername() + " is not a host.");
            }
            return objectNode;
        }
        if (specialUsers.getType().equals("artist")) {
            objectNode.put("message", commandInput.getUsername() + " is not a host.");
            return objectNode;
        }
        Host host = (Host) specialUsers;
        String message = host.addAnnouncement(commandInput);
        objectNode.put("message", message);
        return objectNode;
    }

    /**
     * Removes an announcement from a host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAnnouncement(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers == null) {
            if (user == null) {
               objectNode.put("message", "The username "
                       + commandInput.getUsername() + " doesn't exist.");
                return objectNode;
            } else {
                objectNode.put("message", commandInput.getUsername() + " is not a host.");
                return objectNode;
            }
        }
        if (!specialUsers.getType().equals("host")) {
            objectNode.put("message", commandInput.getUsername() + " is not a host.");
            return objectNode;
        }
        Host host = (Host) specialUsers;
        objectNode.put("message", host.removeAnnouncement(commandInput));
        return objectNode;
    }

    /**
     * Shows the podcasts of a host
     * @param commandInput the command input
     * @return all the podcasts
     */
    public static ObjectNode showPodcasts(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        ArrayList<PodcastOutput> podcastOutputs = new ArrayList<>();
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        Host host = (Host) specialUsers;
        for (Podcast podcast : host.getPodcasts()) {
            PodcastOutput podcastOutput = new PodcastOutput();
            podcastOutput.setName(podcast.getName());
            for (Episode episode : podcast.getEpisodes()) {
                podcastOutput.getEpisodes().add(episode.getName());
            }
            podcastOutputs.add(podcastOutput);
        }
        objectNode.put("result", objectMapper.valueToTree(podcastOutputs));
        return objectNode;
    }

    /**
     * Removes a podcast from a host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeAlbum(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers == null) {
            if (user == null) {
                objectNode.put("message", "The username "
                        + commandInput.getUsername() + " doesn't exist.");
                return objectNode;
            } else {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            }
        }
        if (!specialUsers.getType().equals("artist")) {
            objectNode.put("message", commandInput.getUsername() + " is not an artist.");
            return objectNode;
        }
        Artist artist = (Artist) specialUsers;
        objectNode.put("message", artist.removeAudios(commandInput));
        return objectNode;
    }

    /**
     * Changes the current page to the given one
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode changePage(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        objectNode.put("message", user.changePage(commandInput.getNextPage()));
        return objectNode;
    }

    /**
     * Removes a podcast from a host
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removePodcast(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        if (specialUsers == null) {
            if (user == null) {
                objectNode.put("message", commandInput.getUsername());
                return objectNode;
            } else {
                objectNode.put("message", commandInput.getUsername());
                return objectNode;
            }
        }
        if (!specialUsers.getType().equals("host")) {
            objectNode.put("message", commandInput.getUsername());
            return objectNode;
        }
        Host host = (Host) specialUsers;
        objectNode.put("message", host.removePodcast(commandInput.getName()));
        return objectNode;
    }

    /**
     * Removes an event
     * @param commandInput the command input
     * @return the object node
     */
    public static ObjectNode removeEvent(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("user", commandInput.getUsername());
        User user = Admin.getInstance().getUser(commandInput.getUsername());
        SpecialUsers specialUsers = Admin.getInstance().getSpecialUser(commandInput.getUsername());
        if (specialUsers == null) {
            if (user == null) {
                objectNode.put("message", "The username "
                        + commandInput.getUsername() + " doesn't exist.");
                return objectNode;
            } else {
                objectNode.put("message", commandInput.getUsername()
                        + " is not an artist.");
                return objectNode;
            }
        }
        if (!specialUsers.getType().equals("artist")) {
            objectNode.put("message", commandInput.getUsername()
                    + " is not an artist.");
            return objectNode;
        }
        Artist artist = (Artist) specialUsers;
        objectNode.put("message", artist.removeEvent(commandInput.getName()));
        return objectNode;
    }

    /**
     * Gets the most liked 5 (at most) albums
     * @param commandInput the command input
     * @return the albums
     */

    public static ObjectNode getTop5Albums(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(Admin.getInstance().getTop5Albums()));
        return objectNode;
    }

    /**
     * Gets the most liked 5 (at most) artists
     * @param commandInput the command input
     * @return the artists
     */
    public static ObjectNode getTop5Artists(final CommandInput commandInput) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("command", commandInput.getCommand());
        objectNode.put("timestamp", commandInput.getTimestamp());
        objectNode.put("result", objectMapper.valueToTree(Admin.getInstance().getTop5Artists()));
        return objectNode;
    }
}
