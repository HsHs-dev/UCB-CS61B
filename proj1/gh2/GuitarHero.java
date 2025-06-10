package gh2;
import edu.princeton.cs.algs4.StdAudio;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A client that uses the synthesizer package to replicate a plucked guitar string sound
 */
public class GuitarHero {
    private static final String KEYBOARD = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

    public static void main(String[] args) {
        /* create an array of guitar strings */
        GuitarString[] strings = new GuitarString[KEYBOARD.length()];

        /* initialize the strings with their proper frequencies */
        for (int i = 0; i < strings.length; i++) {
            double freq = 440 * Math.pow(2, (i - 24) / 12.0);
            strings[i] = new GuitarString(freq);
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = KEYBOARD.indexOf(key);
                if (index != -1) {
                    strings[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString string: strings) {
                sample += string.sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(sample);

            /* advance the simulation of all guitar strings by one step */
            for (GuitarString string: strings) {
                string.tic();
            }

        }
    }
}

