package app.pages;

import app.audio.Collections.Album;
import app.user.Artist;
import app.user.SpecialUsers;
import lombok.Getter;

import java.util.ArrayList;

public class ArtistPage implements SpecialPages {
    @Getter
    private ArrayList<String> albums = new ArrayList<>();

    @Override
    public final String showPage(final SpecialUsers specialUsers) {
        Artist artist = (Artist) specialUsers;
        String message = new String();
        StringBuilder stringBuilder = new StringBuilder(message);
        stringBuilder.append("Albums:\n\t");
        ArrayList<String> auxiliaryAlbums = new ArrayList<>();
        for (Album album : artist.getAlbums()) {
            auxiliaryAlbums.add(album.getName());
        }
        stringBuilder.append(auxiliaryAlbums);
        stringBuilder.append("\n\nMerch:\n\t");
        ArrayList<String> merch = artist.createMerchPage();
        stringBuilder.append(merch);
        stringBuilder.append("\n\nEvents:\n\t");
        ArrayList<String> newMessage = artist.createEventPage();
        stringBuilder.append(newMessage);
        message = stringBuilder.toString();
        return message;
    }
}
