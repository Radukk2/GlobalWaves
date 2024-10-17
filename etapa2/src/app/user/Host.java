package app.user;

import app.Admin;
import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.player.PlayerSource;
import app.utils.Enums;
import fileio.input.CommandInput;
import fileio.input.EpisodeInput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;

public class Host extends SpecialUsers {
    @Getter
    private ArrayList<Podcast> podcasts;

    class Announcement {
        private String name;
        private String description;

        Announcement(final String name, final String description) {
            this.name = name;
            this.description = description;
        }
    }

    private ArrayList<Announcement> announcements;

    public Host(final String username, final int age, final String city, final String type) {
        super(username, age, city, type);
        podcasts = new ArrayList<>();
        announcements = new ArrayList<>();
    }

    /**
     * adds a podcast to the list
     * @param commandInput the command input
     * @return a message
     */
    public final String fixPodcast(final CommandInput commandInput) {
        for (Podcast podcast : this.getPodcasts()) {
            if (podcast.getName().equals(commandInput.getName())) {
                return commandInput.getUsername() + " has another podcast with the same name.";
            }
        }
        ArrayList<Episode> episodes = new ArrayList<>();
        for (EpisodeInput episodeInput : commandInput.getEpisodes()) {
            Episode episode = new Episode(episodeInput.getName(),
                    episodeInput.getDuration(), episodeInput.getDescription());
            episodes.add(episode);
        }
        for (int i = 0; i < episodes.size(); i++) {
            for (int j = 0; j < episodes.size(); j++) {
                if (episodes.get(i).getName().equals(episodes.get(j).getName()) && i != j) {
                    return commandInput.getUsername() + " has the same episode in this podcast.";
                }
            }
        }
        Podcast podcast = new Podcast(commandInput.getName(), commandInput.getUsername(), episodes);
        this.podcasts.add(podcast);
        Admin.getInstance().getPodcasts().add(podcast);
        return commandInput.getUsername() + " has added new podcast successfully.";
    }

    /**
     * adds a new announcement to the list
     * @param commandInput the command input
     * @return a message
     */
    public final String addAnnouncement(final CommandInput commandInput) {
        for (Announcement announcement : this.announcements) {
            if (announcement.name.equals(commandInput.getName())) {
                return commandInput.getUsername()
                        + " has already added an announcement with this name.";
            }
        }
        Announcement announcement = new Announcement(commandInput.getName(),
                commandInput.getDescription());
        this.announcements.add(announcement);
        return commandInput.getUsername() + " has successfully added new announcement.";
    }

    /**
     * gets a podcast by name
     * @param name the name
     * @return the podcast
     */
    public final Podcast getPodcast(final String name) {
        for (Podcast podcast : this.podcasts) {
            if (podcast.getName().equals(name)) {
                return podcast;
            }
        }
        return null;
    }

    /**
     * adds the page for announcements
     * @param message1 the message before adding this part of the page
     * @return the message after adding this part of the page
     */
    public final String addAnnouncementPage(final String message1) {
        String message = message1;
        StringBuilder stringBuilder = new StringBuilder(message);
        ArrayList<String> news = new ArrayList<>();
        for (Announcement announcement : this.announcements) {
            String ann = new String();
            StringBuilder stringBuilder1 = new StringBuilder(ann);
            stringBuilder1.append(announcement.name + ":\n\t" + announcement.description + "\n");
            ann = stringBuilder1.toString();
            news.add(ann);
        }
        stringBuilder.append(news);
        message = stringBuilder.toString();
        return message;
    }

    /**
     * removes ana announcement from the list
     * @param commandInput the command input
     * @return a message in relation with the outcome
     */
    public final String removeAnnouncement(final CommandInput commandInput) {
        for (Announcement announcement : this.announcements) {
            if (commandInput.getName().equals(announcement.name)) {
                this.announcements.remove(announcement);
                return commandInput.getUsername() + " has successfully deleted the announcement.";
            }
        }
        return commandInput.getUsername() + " has no announcement with the given name.";
    }

    /**
     * removes a podcast from the host
     * @param podcastName the name of the podcast
     * @return a message in relation with the outcome
     */
    public final String removePodcast(final String podcastName) {
        Podcast podcast = this.getPodcast(podcastName);
        if (podcast == null) {
            return this.getUsername() + " doesn't have a podcast with the given name.";
        }
        for (User user : Admin.getInstance().getUsers()) {
            if (user.getPlayer().getSource() == null) {
                continue;
            }
            PlayerSource source = user.getPlayer().getSource();
            if (source.getType() == Enums.PlayerSourceType.PODCAST) {
                if (source.getAudioCollection().getName().equals(podcastName)) {
                    return this.getUsername() + " can't delete this podcast.";
                }
            }
        }
        this.getPodcasts().remove(podcast);
        Admin.getInstance().getPodcasts().remove(podcast);
        return this.getUsername() + " deleted the podcast successfully.";
    }

    /**
     * returns a list of podcast's names
     * @return array list of podcast names
     */
    public final ArrayList<String> getPodcastNames() {
        ArrayList<String> strings = new ArrayList<>();
        for (Podcast podcast : this.getPodcasts()) {
            strings.add(podcast.getName());
        }
        return strings;
    }
    @Override
    public final String remove() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList = this.getPodcastNames();
        for (User user : Admin.getInstance().getUsers()) {
            if (user.getPlayer().getSource() == null) {
                continue;
            }
            PlayerSource source = user.getPlayer().getSource();
            if (source.getType() == Enums.PlayerSourceType.PODCAST) {
                if (stringArrayList.contains(source.getAudioCollection().getName())) {
                    return this.getUsername() + " can't be deleted.";
                }
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
        Iterator<Podcast> iterator = Admin.getInstance().getPodcasts().iterator();
        while (iterator.hasNext()) {
            Podcast podcast = iterator.next();
            if (podcast.getOwner().equals(this.getUsername())) {
                iterator.remove();
            }
        }

        Admin.getSpecialUsers().remove(this);
        return this.getUsername() + " was successfully deleted.";
    }
}

