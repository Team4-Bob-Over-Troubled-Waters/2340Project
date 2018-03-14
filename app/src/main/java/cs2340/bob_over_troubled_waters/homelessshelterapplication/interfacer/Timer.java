package cs2340.bob_over_troubled_waters.homelessshelterapplication.interfacer;

import android.os.AsyncTask;

import org.apache.commons.lang3.time.StopWatch;

/**
 * this is a thread that starts a timer and throws an exception if not stopped before
 * it hits a given amount of time
 *
 * this prevents infinite looping if there is no network connection
 */
class Timer extends AsyncTask<Void, Void, Boolean> {

    private long stopAt;
    private boolean done = false;

    static Timer getTimer(long stopAt) {
        Timer timer = new Timer(stopAt);
        timer.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return timer;
    }

    void stopTimer() {
        done = true;
    }

    static Timer getTimer() {
        return getTimer(2000);
    }

    private Timer(long stopAt) {
        this.stopAt = stopAt;
    }

    @Override
    protected Boolean doInBackground(Void ... params) {
        StopWatch timer = new StopWatch();
        timer.start();

        System.out.println("Timer has been started.");

        while (true) {
            try {
                if (timer.getTime() > stopAt) {
                    return false;
                }
                if (done) throw new InterruptedException();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("Timer has been turned off at " + timer.getTime() + ".");
                return true;
            }
        }
    }

    @Override
    protected void onPostExecute(final Boolean stopped) {
        if (!stopped) {
            System.out.println("Timed out.");
            throw new RuntimeException("There is an issue with your connection.");
        }
    }
}
