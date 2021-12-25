package my.project.neural.core;

import java.util.function.Function;

import static java.lang.Math.exp;

public class FunctionActivations {
    public static final Function<Double, Double> sigmoid = (x) -> 1/(1+ exp(-x));
    public static final Function<Double, Double> perceptron = (x) -> x > 0d ? 1 : 0d;
}
