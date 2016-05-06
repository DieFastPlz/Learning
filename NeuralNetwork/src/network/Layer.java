package network;

/**
 * Created by Ali Golmakani on 4/24/2016.
 */
public class Layer {
    private final static double BIAS_MAX = 10;
    private int count;
    private Matrix values;
    private Matrix bias;

    private Matrix inputMatrix;

    private Layer preLayer;
    private void setCount(int count) {
        this.count = count;

    }
    public int getCount() {
        return count;
    }
    private void shuffleBias() {
        bias.shuffle();
    }
    public Layer(Layer preLayer, int count) {
        setCount(count);
        values = new Matrix(count, 1);
        bias = new Matrix(count, 1);
        shuffleBias();
        this.preLayer = preLayer;
        int preLayerCount = 1;
        try {
            preLayerCount = preLayer.count;
        }
        catch(Exception e) {

        }
        inputMatrix = new Matrix(count, preLayerCount);
        inputMatrix.shuffle();
    }
    public void compute() {
        values = bias.plus(inputMatrix.mult(preLayer.values));
        values.sigmoid();
    }
    public Matrix getValues() {
        return values;
    }
    public void setValues(double[] values) {
        if(values.length != count) {
            throw new RuntimeException("fuck again and again");
        }
        for (int i = 0; i < values.length; i++) {
            this.values.set(i, 0, Sigmoid.sigmoid(values[i]));
        }
    }
    public void changeWeight(Matrix m) {
        if(m.getRow() != inputMatrix.getRow() || m.getColumn() != inputMatrix.getColumn()) {
            throw new RuntimeException("uuuh");
        }
        inputMatrix = inputMatrix.plus(m);
    }
    public void changeBias(Matrix m) {
        if(m.getRow() != bias.getRow() || m.getColumn() != bias.getColumn()) {
            throw new RuntimeException("uuuh");
        }
        bias = bias.plus(m);
    }
    public Matrix getDSigmoid() {
        Matrix result;
        result = getValues().multD(Matrix.constMatrix(getCount(), 1, 1).sub(getValues()));
        return result;
    }
    public Matrix getInputMatrix() {
        return inputMatrix;
    }
}