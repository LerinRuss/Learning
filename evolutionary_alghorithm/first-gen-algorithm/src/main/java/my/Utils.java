package my;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Utils {

    public static Gen hasNoMistake(List<Solver.Report> reports) {
        for (Solver.Report report : reports) {
            if (report.getMistake() == 0) {
                return report.getGen();
            }
        }
        return null;
    }

    public static void mutate(List<Gen> gens) {
        Random r = new Random();
        gens.forEach( gen-> mutate(gen, r.nextInt(4)));
    }

    public static void mutate(Gen gen, int xnum) {
        int delta = random() < 0.5 ? -1 : 1;
        switch (xnum) {
            case 0:
                gen.addX1Delta(delta);
            break;
            case 1:
                gen.addX2Delta(delta);
                break;
            case 2:
                gen.addX3Delta(delta);
                break;
            case 3:
                gen.addX4Delta(delta);
                break;
                default:
                    throw new IllegalArgumentException("Whats the xnum? (" + xnum + ")");
        }
    }

    public static List<Gen> crossAll(List<Gen> parents) {
        List<Gen> children = new ArrayList<>(partitioning(parents.size(), 2));
        parents.forEach(left -> parents.forEach(right -> {
            if(left == right) {
                return;
            }
            children.add(cross(left, right));
        }));

        return children;
    }

    public static Gen cross(Gen left, Gen right) {
        int x1 = random() < 0.5 ? left.getX1() : right.getX1();
        int x2 = random() < 0.5 ? left.getX2() : right.getX2();
        int x3 = random() < 0.5 ? left.getX3() : right.getX3();
        int x4 = random() < 0.5 ? left.getX4() : right.getX4();

        return new Gen(x1, x2, x3, x4);
    }

    public static List<Gen> chooseBestGens(List<Solver.Report> reports, int count) {
        if(count == reports.size()) {
            return reports.stream().map(Solver.Report::getGen).collect(Collectors.toList());
        }
        Queue<Solver.Report> gens = new PriorityQueue<>(reports.size(), Comparator.comparingInt(Solver.Report::getMistake));
        gens.addAll(reports);
        List<Gen> res = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            res.add(gens.poll().getGen());
        }
        return res;
    }

    public static int partitioning(int n, int m) {
        int res = n;
        for (int i = n - m + 1; i < n ; i++) {
            res *= i;
        }

        return res;
    }
}
