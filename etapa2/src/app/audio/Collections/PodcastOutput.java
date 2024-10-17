package app.audio.Collections;

import lombok.Getter;

import java.util.ArrayList;

public class PodcastOutput {
    @Getter
    private ArrayList<String> episodes = new ArrayList<>();
    private String name;

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }
}
