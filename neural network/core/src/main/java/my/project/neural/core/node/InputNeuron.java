package my.project.neural.core.node;

import my.project.neural.core.synapse.Synapse;

import java.util.List;

public class InputNeuron {
    private final List<Synapse> outputs;
    private double intermediate;

    public InputNeuron(Synapse... outputs) {
        this.outputs = List.of(outputs);
    }


    public void activate(double value) {
        this.intermediate = value;
        outputs.forEach(output -> output.calculateValue(value));
    }


    public List<Synapse> getOutputs() {
        return outputs;
    }

    public double getIntermediate() {
        return intermediate;
    }
}
