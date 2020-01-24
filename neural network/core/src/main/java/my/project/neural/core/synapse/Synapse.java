package my.project.neural.core.synapse;

public class Synapse {

    private double weight;
    private double value;
    private double deltaWeight;

    public Synapse(double weight) {
        this.weight = weight;
    }


    public void calculateValue(double value) {
        this.value = value * weight;
    }

    public void addWeight(double delta) {
        this.deltaWeight = delta;
        this.weight = weight + delta;
    }


    public double getWeight() {
        return weight;
    }

    public double getValue() {
        return value;
    }

    public double getDeltaWeight() {
        return deltaWeight;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }
}
