package me.scoin0.dharmaproject.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.concurrent.TimeUnit;
import java.util.List;

public class Utils {

    /**
     * Obtains a list of online players
     * @return A List of Players
     */
    @SuppressWarnings("unchecked")
    public static List<Player> getOnlinePlayers() {
        return (List<Player>) Bukkit.getOnlinePlayers();
    }

    /**
     * Convert milliseconds to human readable time
     * @param millis
     * @return The correct human readable time
     */
    public static String convertMilliToReadableTime(long millis) {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        return (days != 0 ? days + " days, " : "") + (hours != 0 ? hours + " hours, " : "") + (minutes != 0 ? minutes + " minutes, " : "") + (seconds != 0 ? seconds + " seconds. " : "");
    }

    /**
     * Convert seconds to a more human readable time
     * @param seconds
     * @return The correct time in minutes and seconds.
     */
    public static String convertSecondsToReadableTime(long seconds) {
        long minutes = TimeUnit.SECONDS.toMinutes(seconds);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(seconds));
        return (minutes != 0 ? minutes + " minutes, " : "") + (second != 0 ? second + " seconds " : "");
    }
}