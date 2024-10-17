package app.user;

import fileio.input.CommandInput;
import lombok.Getter;

public class SpecialUsers {
    @Getter
    private String username;
    @Getter
    private int age;
    @Getter
    private String city;
    @Getter
    private String type;

    public SpecialUsers(final String username, final int age,
                        final String city, final String type) {
        this.username = username;
        this.age = age;
        this.city = city;
        this.type = type;
    }

    /**
     * removes a special user from the list
     * @return a message
     */
    public String remove() {
        return null;
    }

    /**
     * removes playlist/podcast
     * @param commandInput the command input
     * @return a message
     */
    public String removeAudios(final CommandInput commandInput) {
        return null;
    }
}
