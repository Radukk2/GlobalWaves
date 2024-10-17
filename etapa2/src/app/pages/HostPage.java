package app.pages;

import app.audio.Collections.Podcast;
import app.audio.Files.Episode;
import app.user.Host;
import app.user.SpecialUsers;

import java.util.ArrayList;

public class HostPage implements SpecialPages {
    @Override
    public final String showPage(final SpecialUsers specialUsers) {
        Host host = (Host) specialUsers;
        String message = new String();
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.append("Podcasts:\n\t");
        ArrayList<String> podcasts = new ArrayList<>();
        for (Podcast podcast : host.getPodcasts()) {
            ArrayList<String> episodes = new ArrayList<>();
            StringBuilder stringBuilder1 = new StringBuilder(podcast.getName() + ":\n\t");
            for (Episode episode : podcast.getEpisodes()) {
                StringBuilder stringBuilder2 = new StringBuilder(episode.getName());
                stringBuilder2.append(" - " + episode.getDescription());
                String ep = stringBuilder2.toString();
                episodes.add(ep);
            }
            stringBuilder1.append(episodes + "\n");
            String newPodcast = stringBuilder1.toString();
            podcasts.add(newPodcast);
        }
        stringBuilder.append(podcasts + "\n\nAnnouncements:\n\t");
        message = stringBuilder.toString();
        return host.addAnnouncementPage(message);
    }
}
