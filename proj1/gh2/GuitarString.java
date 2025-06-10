package gh2;

import deque.ArrayDeque;
import deque.Deque;

public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private Deque<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int) Math.round(SR / frequency);

        buffer = new ArrayDeque<>();
        fill(capacity);
    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        for (int i = 0; i < buffer.size(); i++) {
            double rand = Math.random() - 0.5;
            double val = buffer.removeFirst();
            if (val == 0) {
                buffer.addLast(rand);
            } else {
                buffer.addLast(val);
            }
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        double front = buffer.removeFirst();
        double newSample = (front + buffer.get(0)) * 0.5 * DECAY;
        buffer.addLast(newSample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.get(0);
    }

    /**
     * fill the buffer with the initial value 0
     * @param capacity the capacity of the given buffer
     */
    private void fill(int capacity) {
        Double init = 0.0;
        for (int i = 0; i < capacity; i++) {
            buffer.addLast(init);
        }
    }

}
