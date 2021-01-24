package my.project.neural.xor.perceptron;

import my.project.neural.core.node.InputNeuron;
import my.project.neural.core.node.InternalNeuron;
import my.project.neural.core.node.OutputNeuron;
import my.project.neural.core.synapse.Synapse;

import static java.util.List.of;
import static my.project.neural.core.FunctionActivations.perceptron;

public class XORPerceptronApp {
    public static void main(String[] args) {
        XORPerceptron network = XORPerceptron.build();
        System.out.println(network.activate(1, 1));
    }

    /**
     * Architecture of this network see in this link on google drive:
     * https://docs.google.com/drawings/d/1M4nSrFpTrvBw7gM0BlgheJ0ONG_aKRuRP2_WFUkHLeM
     *
     * X xor Y = notX & Y | X & notY
     */
    private static class XORPerceptron {
        private InputNeuron xInput;
        private InputNeuron yInput;
        private InternalNeuron x;
        private InternalNeuron notX;
        private InternalNeuron y;
        private InternalNeuron notY;
        private InternalNeuron leftAnd;
        private InternalNeuron rightAnd;
        private OutputNeuron output;

        public double activate(int x, int y) {
            this.xInput.activate(x);
            this.yInput.activate(y);

            this.notX.activate();
            this.x.activate();
            this.y.activate();
            this.notY.activate();

            this.leftAnd.activate();
            this.rightAnd.activate();

            return output.activate();
        }

        public static XORPerceptron build() {
            Synapse betweenXinputAndNotX = new Synapse(-1);
            Synapse betweenXinputAndX = new Synapse(1);
            Synapse betweenYinputAndY = new Synapse(1);
            Synapse betweenYinputAndNotY = new Synapse(-1);

            Synapse betweenNotXandAnd = new Synapse(2);
            Synapse betweenYandAnd = new Synapse(2);
            Synapse betweenXandAnd = new Synapse(2);
            Synapse betweenNotYandAnd = new Synapse(2);

            Synapse betweenAndAndResLeft = new Synapse(2);
            Synapse betweenAndAndResRight = new Synapse(2);


            XORPerceptron result = new XORPerceptron();
            result.xInput = new InputNeuron(betweenXinputAndNotX, betweenXinputAndX);
            result.yInput = new InputNeuron(betweenYinputAndY, betweenYinputAndNotY);

            result.notX = new InternalNeuron(
                of(betweenXinputAndNotX),
                of(betweenNotXandAnd),
                perceptron,
                1
            );
            result.x = new InternalNeuron(
                of(betweenXinputAndX),
                of(betweenXandAnd),
                perceptron
            );
            result.y = new InternalNeuron(
                of(betweenYinputAndY),
                of(betweenYandAnd),
                perceptron
            );
            result.notY = new InternalNeuron(
                of(betweenYinputAndNotY),
                of(betweenNotYandAnd),
                perceptron,
                1
            );

            result.leftAnd = new InternalNeuron(
                of(betweenYandAnd, betweenNotXandAnd),
                of(betweenAndAndResLeft),
                perceptron,
                -3
            );
            result.rightAnd = new InternalNeuron(
                of(betweenNotYandAnd, betweenXandAnd),
                of(betweenAndAndResRight),
                perceptron,
                -3
            );

            result.output = new OutputNeuron(perceptron, -1, betweenAndAndResLeft, betweenAndAndResRight);

            return result;
        }
    }
}
