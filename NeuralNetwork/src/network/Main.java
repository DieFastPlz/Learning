package network;


import java.util.Random;

import static java.util.Arrays.sort;

/**
 * Created by Ali Golmakani on 4/24/2016.
 */

public class Main {

    public static double[] generateRandomArray(int n) {
        Random generator = new Random();
        double[] result = new double[n];
        for (int i = 0; i < n; i++) {
            result[i] = (generator.nextDouble() - 0.5) * 8;
        }
        return result;
    }
    public static double[] reverse(double[] d) {
        double[] result = new double[d.length];
        for (int i = 0; i < d.length; i++) {
            result[i] = d[d.length - 1 - i];
        }
        return result;
    }
    public static void main(String[] args) {
        Machine a = new Machine(5, 15, 5);



        for (int i = 0; i < 300000; i++) {
            double[] t = generateRandomArray(5);
            double[] ts = t.clone();
            sort(ts);
            a.learn(t, ts);
        }
        double[] qq = generateRandomArray(5);
        double[] qqq = qq.clone();
        sort(qqq);
        System.out.println("fuck");
        a.setInput(qq);
        a.compute();
        for (double d : reverse(Sigmoid.sigmoid(qqq))) {
            System.out.println(d);
        }
        System.out.println("ss");
        System.out.println(a.getOutput());
        System.out.println(a.getCost(qq, qqq));

    }
}

