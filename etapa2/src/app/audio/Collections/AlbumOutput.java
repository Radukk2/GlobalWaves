package app.audio.Collections;

import lombok.Getter;

import java.util.ArrayList;

public class AlbumOutput {
    private String name;
    @Getter
    private ArrayList<String> songs = new ArrayList<>();

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

}
