package my.project.neural.xor.habr;

import my.project.neural.core.node.InputNeuron;
import my.project.neural.core.node.InternalNeuron;
import my.project.neural.core.node.OutputNeuron;
import my.project.neural.core.synapse.Synapse;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;

import static java.util.List.of;
import static my.project.neural.core.FunctionActivations.*;

public class XORNeuronNetwork {
    private final InputNeuron input1;
    private final InputNeuron input2;
    private final InternalNeuron internal1;
    private final InternalNeuron internal2;
    private final OutputNeuron output;
    private final DoubleUnaryOperator derivative;
    private double velocity = 0.7;
    private double moment = 0.3;

    private XORNeuronNetwork(InputNeuron input1, InputNeuron input2, InternalNeuron internal1,
                             InternalNeuron internal2, OutputNeuron output, DoubleUnaryOperator derivative) {
        this.input1 = input1;
        this.input2 = input2;
        this.internal1 = internal1;
        this.internal2 = internal2;
        this.output = output;
        this.derivative = derivative;
    }


    public static XORNeuronNetwork build(DoubleUnaryOperator derivative, double ...weights) {
        Function<Double, Double> activation = sigmoid;

        Synapse left1 = new Synapse(weights[0]);
        Synapse left2 = new Synapse(weights[1]);
        Synapse left3 = new Synapse(weights[2]);
        Synapse left4 = new Synapse(weights[3]);
        Synapse right1 = new Synapse(weights[4]);
        Synapse right2 = new Synapse(weights[5]);

        InputNeuron input1 = new InputNeuron(left1, left2);
        InputNeuron input2 = new InputNeuron(left3, left4);
        InternalNeuron internal1 = new InternalNeuron(of(left1, left3), of(right1), activation);
        InternalNeuron internal2 = new InternalNeuron(of(left2, left4), of(right2), activation);
        OutputNeuron output = new OutputNeuron(activation, right1, right2);

        return new XORNeuronNetwork(input1, input2, internal1, internal2, output, derivative);
    }


    public double activate(double x1, double x2) {
        input1.activate(x1);
        input2.activate(x2);
        internal1.activate();
        internal2.activate();
        return output.activate();
    }

    public void backpropagation(double expected, double actual) {
        double deltaOutput = (expected - actual) * derivative.applyAsDouble(actual);
        double deltaInternal1 = calculateDeltaInternal(internal1, deltaOutput);
        double deltaInternal2 = calculateDeltaInternal(internal2, deltaOutput);
        updateSynapse(input1.getOutputs().get(0), deltaInternal1, input1.getIntermediate());
        updateSynapse(input1.getOutputs().get(1), deltaInternal2, input1.getIntermediate());
        updateSynapse(input2.getOutputs().get(0), deltaInternal1, input2.getIntermediate());
        updateSynapse(input2.getOutputs().get(1), deltaInternal2, input2.getIntermediate());
    }

    private double calculateDeltaInternal(InternalNeuron internal, double deltaOutput) {
        Synapse outputSynapse = internal.getOutputs().get(0);
        double synapseWeight = outputSynapse.getWeight();
        double gradient = deltaOutput * internal.getIntermediate();
        double deltaWeight = velocity * gradient + moment * outputSynapse.getDeltaWeight();
        outputSynapse.addWeight(deltaWeight);
        return synapseWeight * deltaOutput * derivative.applyAsDouble(internal.getIntermediate());
    }

    private void updateSynapse(Synapse synapse, double deltaOutput, double internalIntermediate) {
        double gradient = deltaOutput * internalIntermediate;
        double deltaWeight = velocity * gradient + moment * synapse.getDeltaWeight();
        synapse.addWeight(deltaWeight);
    }

}
