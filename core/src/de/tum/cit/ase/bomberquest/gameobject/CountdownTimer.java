package de.tum.cit.ase.bomberquest.gameobject;


import java.util.ArrayList;
import java.util.List;

/**
 * The CountdownTimer class manages a countdown mechanism within the game.
 * It tracks the remaining time, updates it as the game progresses, and notifies
 * listeners when the time has run out.
 */

public class CountdownTimer {

    /**
     * Interface for listeners to receive countdown events.
     */
    public interface CountdownListener {
        /**
         * Called when the countdown reaches zero.
         */
        void onTimeUp();
    }

    private float timeRemaining; // Time remaining in seconds
    private boolean isTimeUp;    // Flag indicating if time has run out
    private List<CountdownListener> listeners; // Registered listeners

    /**
     * Constructs a new CountdownTimer with the specified initial time.
     *
     * @param initialTimeSeconds The starting time for the countdown in seconds.
     */
    public CountdownTimer(float initialTimeSeconds) {
        this.timeRemaining = initialTimeSeconds;
        this.isTimeUp = false;
        this.listeners = new ArrayList<>();
    }

    /**
     * Updates the countdown timer. This method should be called every frame,
     * passing the elapsed time since the last update.
     *
     * @param deltaTimeSeconds The time elapsed since the last update in seconds.
     */
    public void update(float deltaTimeSeconds) {
        if (isTimeUp) {
            return; // No need to update if time is already up
        }

        // Decrement the remaining time
        timeRemaining -= deltaTimeSeconds;

        // Check if time has run out
        if (timeRemaining <= 0) {
            timeRemaining = 0;
            isTimeUp = true;
            notifyTimeUp();
        }
    }

    /**
     * Adds time to the countdown timer. Useful for rewarding the player with
     * extra time through game events or power-ups.
     *
     * @param additionalTimeSeconds The amount of time to add in seconds.
     */
    public void addTime(float additionalTimeSeconds) {
        if (isTimeUp) {
            return; // Cannot add time if time is already up
        }
        timeRemaining += additionalTimeSeconds;
    }

    /**
     * Resets the countdown timer to a new initial time.
     *
     * @param newTimeSeconds The new starting time for the countdown in seconds.
     */
    public void reset(float newTimeSeconds) {
        this.timeRemaining = newTimeSeconds;
        this.isTimeUp = false;
    }

    /**
     * Retrieves the current remaining time.
     *
     * @return The remaining time in seconds.
     */
    public float getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Checks if the countdown has reached zero.
     *
     * @return True if time is up, false otherwise.
     */
    public boolean isTimeUp() {
        return isTimeUp;
    }

    /**
     * Registers a listener to receive countdown events.
     *
     * @param listener The listener to register.
     */
    public void addListener(CountdownListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregisters a listener from receiving countdown events.
     *
     * @param listener The listener to unregister.
     */
    public void removeListener(CountdownListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners that the time is up.
     */
    private void notifyTimeUp() {
        for (CountdownListener listener : listeners) {
            listener.onTimeUp();
        }
    }
}
