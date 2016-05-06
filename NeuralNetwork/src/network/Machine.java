package network;

/**
 * Created by Ali Golmakani on 4/24/2016.
 */
public class Machine {
    private final static double learnCoef = 0.5;
    private final static int learnRate = 20;
    private int learnTime = 0;
    private Layer input;
    private Layer hidden;
    private Layer output;
    private double cost;
    private Matrix costGR;
    private Matrix hiddenBiasChanges;
    private Matrix outputBiasChanges;
    private Matrix inputHiddenWeightChanges;
    private Matrix hiddenOutputWeightChanges;
    public Machine(int inputCount, int hiddenCount, int outputCount) {
        input = new Layer(null, inputCount);
        hidden = new Layer(input, hiddenCount);
        output = new Layer(hidden, outputCount);
        inputHiddenWeightChanges = new Matrix(hiddenCount, inputCount);
        hiddenOutputWeightChanges = new Matrix(outputCount, hiddenCount);
        hiddenBiasChanges = new Matrix(hiddenCount, 1);
        outputBiasChanges = new Matrix(outputCount, 1);
    }
    public void setInput(double[] inputValues) {
        input.setValues(inputValues);
    }
    public Matrix getOutput() {
        return output.getValues();
    }
    public void compute() {
        hidden.compute();
        output.compute();
    }
    public double getCost(double[] inputSample, double[] targetOutput){
        int outputCount = output.getCount();
        setInput(inputSample);
        compute();
        Matrix machineOutput = getOutput();
        Matrix correctOutput = new Matrix(outputCount, 1);
        for (int i = 0; i < outputCount; i++) {
            correctOutput.set(i, 0, targetOutput[i]);
        }
        correctOutput.sigmoid();
        double c = machineOutput.sub(correctOutput).length();
        return c;

    }
    public void learn(double[] inputSample, double[] targetOutput) {
        int outputCount = output.getCount();
        setInput(inputSample);
        compute();
        Matrix machineOutput = getOutput();
        Matrix correctOutput = new Matrix(outputCount, 1);
        for (int i = 0; i < outputCount; i++) {
            correctOutput.set(i, 0, targetOutput[i]);
        }
        correctOutput.sigmoid();
        costGR = machineOutput.sub(correctOutput);
        Matrix outputError = output.getDSigmoid().multD(costGR);
        outputBiasChanges = outputBiasChanges.plus(outputError.mult(-learnCoef / learnRate));
        Matrix hiddenError = ((output.getInputMatrix().transpose()).mult(outputError)).multD(hidden.getDSigmoid());
        hiddenBiasChanges = hiddenBiasChanges.plus(hiddenError.mult(-learnCoef / learnRate));
        hiddenOutputWeightChanges = hiddenOutputWeightChanges.plus(Matrix.multWise(outputError, hidden.getValues()).mult(-learnCoef / learnRate));
        inputHiddenWeightChanges = inputHiddenWeightChanges.plus(Matrix.multWise(hiddenError, input.getValues()).mult(-learnCoef / learnRate));
        learnTime += 1;
        if(learnTime == learnRate) {
            hidden.changeBias(hiddenBiasChanges);
            hidden.changeWeight(inputHiddenWeightChanges);
            output.changeBias(outputBiasChanges);
            output.changeWeight(hiddenOutputWeightChanges);
            //System.out.println(hidden.getInputMatrix());
            outputBiasChanges = Matrix.constMatrix(output.getCount(),1,0);
            hiddenBiasChanges = Matrix.constMatrix(hidden.getCount(),1,0);
            hiddenOutputWeightChanges = Matrix.constMatrix(output.getCount(),hidden.getCount(),0);
            inputHiddenWeightChanges = Matrix.constMatrix(hidden.getCount(),input.getCount(),0);
            //System.out.println(getCost(inputSample, targetOutput));
            learnTime = 0;
        }
    }
}
