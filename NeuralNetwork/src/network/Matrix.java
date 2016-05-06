package network;

import java.util.Random;

/**
 * Created by Ali Golmakani on 4/24/2016.
 */
public class Matrix {
    private final int row;

    private final int column;
    private double[][] matrix;
    public Matrix(int row, int column) {
        this.row = row;
        this.column = column;
        matrix = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    public void shuffle() {
        Random generator = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = 2 * (0.5 - generator.nextDouble());
            }
        }
    }
    public Matrix mult(Matrix m) {
        if(m.row != column) {
            throw new RuntimeException("Fuck");
        }
        Matrix result = new Matrix(row, m.column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < m.column; j++) {
                double sum = 0;
                for(int k = 0; k < column; k++) {
                    sum += matrix[i][k] * m.matrix[k][j];
                }
                result.matrix[i][j] = sum;
            }
        }
        return result;
    }
    public Matrix multD(Matrix m) {
        if(row != m.row || column != m.column) {
            throw new RuntimeException("pooof!");
        }
        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[i][j] = matrix[i][j] * m.matrix[i][j];
            }
        }
        return result;
    }
    public Matrix plus(Matrix m) {
        if(m.row != row || m.column != column) {
            throw new RuntimeException("fuck again");
        }
        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[i][j] = matrix[i][j] + m.matrix[i][j];
            }
        }
        return result;
    }
    public Matrix sub(Matrix m) {
        if(m.row != row || m.column != column) {
            throw new RuntimeException("fuck again");
        }
        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[i][j] = matrix[i][j] - m.matrix[i][j];
            }
        }
        return result;
    }
    public double length() {
        if(column != 1) {
            throw new RuntimeException("again and again");
        }
        double result = 0;
        for (int i = 0; i < row; i++) {
            result += matrix[i][0] * matrix[i][0];
        }
        return result;
    }
    public String toString() {
        String result = "(" + row + "," + column + ")" + "\n";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result += Math.floor(matrix[i][j] * 100) / 100 + " ";
            }
            result += "\n";
        }
        return result;
    }
    public void set(int i, int j, double d) {
        try {
            matrix[i][j] = d;
        }
        catch (Exception e) {

        }
    }
    public void sigmoid() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                matrix[i][j] = Sigmoid.sigmoid(matrix[i][j]);
            }
        }
    }
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
    public static Matrix constMatrix(int row, int column, double value) {
        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[i][j] = value;
            }
        }
        return result;
    }
    public Matrix transpose() {
        Matrix result = new Matrix(column, row);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[j][i] = matrix[i][j];
            }
        }
        return result;
    }
    public Matrix mult(double d) {
        Matrix result = new Matrix(row, column);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result.matrix[i][j] = matrix[i][j] * d;
            }
        }
        return result;
    }
    public static Matrix multWise(Matrix m1, Matrix m2) {
        if(m1.column != 1 || m2.column != 1) {
            throw new RuntimeException("oooh");
        }
        Matrix result = new Matrix(m1.row, m2.row);
        for (int i = 0; i < m1.row; i++) {
            for (int j = 0; j < m2.row; j++) {
                result.matrix[i][j] = m1.matrix[i][0] * m2.matrix[j][0];
            }
        }
        return result;
    }
}
