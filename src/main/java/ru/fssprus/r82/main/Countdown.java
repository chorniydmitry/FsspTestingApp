package ru.fssprus.r82.main;

/**
 * @author Chernyj Dmitry
 *
 */
import java.util.Timer;
import java.util.TimerTask;

public class Countdown {
    public static void main(final String args[]) {

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = Integer.parseInt("30");
            public void run() {
                System.out.println(i--);
                if (i< 0)
                    timer.cancel();
            }
        }, 0, 1000);
    }
}

