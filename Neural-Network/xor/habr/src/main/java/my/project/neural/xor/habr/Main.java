package my.project.neural.xor.habr;

import java.util.Random;

import static java.lang.String.format;

public class Main {

    public static void main(String[] args) {
//        XORNeuronNetwork neuronNetwork = XORNeuronNetwork.build((out) -> out * (1 - out),
//            0.45, 0.78, -0.12, 0.13, 1.5, -2.3);
        int[][] trainSet = {
            {0, 0, 0},
            {0, 1, 1},
            {1, 0, 1},
            {1, 1, 0}
        };
        int epochs = 1000;

        for (int tries = 0; tries < 1000; tries++) {
            var neuronNetwork = XORNeuronNetwork.build((out) -> out * (1 - out), generateWeights(6, 0, 1));
            trainNeuralNetwork(neuronNetwork, epochs, trainSet);
        }

        System.out.println();
        System.out.printf("The smallest common mistake is %.9f%%", BestResult.commonMistake * 100);
    }

    private static void trainNeuralNetwork(XORNeuronNetwork neuronNetwork, int epochs, int[][] trainSet) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            double[][] results = new double[trainSet.length][5];
            double commonMistake = 0;

            for (int i = 0; i < trainSet.length; i++) {
                int[] row = trainSet[i];
                double leftOperand = row[0];
                double rightOperand = row[1];
                double expected = row[2];
                double res = neuronNetwork.activate(leftOperand, rightOperand);
                double mistake = (expected - res) * (expected - res);

                commonMistake += mistake;
                results[i] = new double[]{leftOperand, rightOperand, expected, res, mistake};
                neuronNetwork.backpropagation(expected, res);
            }

            if(BestResult.commonMistake > commonMistake) {
                BestResult.commonMistake = commonMistake;
            }
            printTrainResult(epoch, results, commonMistake);
        }
    }

    private static void printTrainResult(int epoch, double[][] results, double commonMistake) {
        System.out.println();
        System.out.printf("epoch: %d:\n", epoch);

        for (double[] result : results) {
            double leftOperand = result[0];
            double rightOperand = result[1];
            double expected = result[2];
            double actual = result[3];
            double mistake = result[4];

            System.out.printf("%f ^ %f = %f, actual = %f, mistake = %.9f%%\n",
                leftOperand, rightOperand, expected, actual, mistake * 100);
        }

        System.out.println(format("common mistake is %f%%", commonMistake * 100));
        System.out.println();
    }

    private static double[] generateWeights(int size, double b, double scale) {
        Random r = new Random();
        double[] res = new double[size];
        for (int i = 0; i < res.length; i++) {
            res[i] = r.nextDouble() * scale + b;
        }

        return res;
    }

    private static class BestResult {
        public static double commonMistake = 4;
        public static String weights;
    }
}
