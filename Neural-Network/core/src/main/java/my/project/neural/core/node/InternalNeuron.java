package my.project.neural.core.node;

import my.project.neural.core.synapse.Synapse;

import java.util.List;
import java.util.function.Function;

public class InternalNeuron {

    private final List<Synapse> inputs;
    private final List<Synapse> outputs;
    private final Function<Double, Double> activation;
    private final double shift;
    private double intermediate;

    public InternalNeuron(List<Synapse> inputs, List<Synapse> outputs, Function<Double, Double> activation) {
        this(inputs, outputs, activation, 0);
    }

    public InternalNeuron(List<Synapse> inputs, List<Synapse> outputs, Function<Double, Double> activation, double shift) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.activation = activation;
        this.shift = shift;
    }


    public void activate() {
        double sum = inputs.stream().mapToDouble(Synapse::getValue).sum();
        final double normalized = activation.apply(sum + shift);
        this.intermediate = normalized;
        outputs.forEach(output -> output.calculateValue(normalized));
    }


    public List<Synapse> getInputs() {
        return inputs;
    }

    public List<Synapse> getOutputs() {
        return outputs;
    }

    public double getIntermediate() {
        return intermediate;
    }

    public double getShift() {
        return shift;
    }
}
