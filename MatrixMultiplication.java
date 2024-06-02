class MatrixMultiplierThread extends Thread {
    private final int[][] matrixA;
    private final int[][] matrixB;
    private final int[][] resultMatrix;
    private final int startRowIndex;
    private final int endRowIndex;

    public MatrixMultiplierThread(int[][] matrixA, int[][] matrixB, int[][] resultMatrix, int startRowIndex, int endRowIndex) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.startRowIndex = startRowIndex;
        this.endRowIndex = endRowIndex;
    }

    @Override
    public void run() {
        multiplyMatricesInRange();
    }

    private void multiplyMatricesInRange() {
        for (int i = startRowIndex; i < endRowIndex; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixB.length; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
}

public class MatrixMultiplication {
    public static void main(String[] args) throws InterruptedException {
        int[][] matrixA = {
            {23, 2, 40, 51, 55},
            {10, 77, 22, 1, 6},
            {9, 5, 14, 13, 75},
            {76, 7, 98, 19, 60},
            {31, 52, 3, 74, 95}
        };

        int[][] matrixB = {
            {56, 19, 10, 8, 51},
            {1, 58, 89, 3, 40},
            {11, 4, 49, 18, 7},
            {12, 55, 88, 6, 44},
            {81, 72, 26, 5, 99}
        };

        int[][] resultMatrix = new int[5][5];

        int numThreads = 5;
        MatrixMultiplierThread[] threads = new MatrixMultiplierThread[numThreads];

        int rowsPerThread = 5 / numThreads;
        int startRowIndex = 0;
        int endRowIndex = startRowIndex + rowsPerThread;

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new MatrixMultiplierThread(matrixA, matrixB, resultMatrix, startRowIndex, endRowIndex);
            threads[i].start();
            startRowIndex = endRowIndex;
            endRowIndex = startRowIndex + rowsPerThread;
        }

        waitForThreadsToFinish(threads);

        printResultMatrix(resultMatrix);
    }

    private static void waitForThreadsToFinish(MatrixMultiplierThread[] threads) throws InterruptedException {
        for (MatrixMultiplierThread thread : threads) {
            thread.join();
        }
    }

    private static void printResultMatrix(int[][] resultMatrix) {
        for (int[] row : resultMatrix) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
