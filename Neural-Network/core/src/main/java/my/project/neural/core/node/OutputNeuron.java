package my.project.neural.core.node;

import my.project.neural.core.synapse.Synapse;

import java.util.List;
import java.util.function.Function;

public class OutputNeuron {
    private final List<Synapse> inputs;
    private final Function<Double, Double> activation;
    private final double shift;

    public OutputNeuron(Function<Double, Double> activation, Synapse... inputs) {
        this(activation, 0, inputs);
    }

    public OutputNeuron(Function<Double, Double> activation, double shift, Synapse... inputs) {
        this.activation = activation;
        this.shift = shift;
        this.inputs = List.of(inputs);
    }


    public Double activate() {
        double sum = inputs.stream().mapToDouble(Synapse::getValue).sum();
        return activation.apply(sum + shift);
    }


    public List<Synapse> getInputs() {
        return inputs;
    }

    public Function<Double, Double> getActivation() {
        return activation;
    }

    public double getShift() {
        return shift;
    }
}
