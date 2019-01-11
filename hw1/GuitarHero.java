import synthesizer.*;
/** A client that uses the synthesizer package to replicate 37 notes on the chromatic. */

public class GuitarHero {
    private final static int N = 37;
    private final static String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    //private final static double[] strings = new double[N];
    private final static GuitarString[] buffer = new GuitarString[N];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++){
            buffer[i] = new synthesizer.GuitarString(440 * Math.pow(2, (i-24)/12));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    buffer[index].pluck();
                }
            }

            /* compute the superposition of samples */
            double samples = 0;
            for (int i = 0; i < N; i++) {
                samples += buffer[i].sample();
            }

            /* play the sample on standard audio */
            StdAudio.play(samples);

            /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < N; i++) {
                buffer[i].tic();
            }
        }
    }

}
