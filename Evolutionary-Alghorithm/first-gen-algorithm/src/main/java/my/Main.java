package my;

import java.util.ArrayList;
import java.util.List;

import static my.Solver.*;
import static my.Utils.*;

public class Main {
    public static void main(String[] args) {
        int count = 3;
        List<Gen> gens = init(count, 30);
        while (true) {
            List<Report> reports = solve(gens, 30);
            printChosen(reports);
            Gen noMistake = hasNoMistake(reports);
            if(noMistake != null) {
                System.out.println(noMistake);
                break;
            }
            List<Gen> chosen = chooseBestGens(reports, count);
            List<Gen> children = crossAll(chosen);
            mutate(chosen);

            chosen.addAll(children);
            gens = chosen;
        }
    }

    public static List<Gen> init(int count, int genExclusive) {
        List<Gen> res = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            res.add(Gen.random(genExclusive));
        }
        return res;
    }

    private static void printChosen(List<Report> chosen) {
        System.out.println("--------------START NEW SURVIVED---------------");
        chosen.forEach(report -> System.out.println("mistake is = " + report.getMistake()));
        System.out.println("--------------END NEW SURVIVED-----------------");
    }
}
