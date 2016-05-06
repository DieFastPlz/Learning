package network;

/**
 * Created by Ali Golmakani on 4/24/2016.
 */
public class Sigmoid {
    public static double sigmoid(double d) {
        double result = 1 / (1 + Math.exp(-d));
        return result;
    }
    public static double dSigmoid(double d) {
        double result = sigmoid(d) * (1 - sigmoid(d));
        return result;
    }
    public static double[] sigmoid(double[] input) {
        double[] result = new double[input.length];
        for(int i = 0; i < input.length; ++i) {
            result[i] = 1 / (1 + Math.exp(-input[i]));
        }
        return result;
    }
}
