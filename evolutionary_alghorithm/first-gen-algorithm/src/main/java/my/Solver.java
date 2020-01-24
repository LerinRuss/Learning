package my;

import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class Solver {

    public static List<Report> solve(List<Gen> gens, final int answer) {
        return gens.stream().map(gen -> new Report(gen, Math.abs(answer - equate(gen)))).collect(toList());
    }

    public static int equate(Gen gen) {
        return equate(gen.getX1(), gen.getX2(), gen.getX3(), gen.getX4());
    }

    public static int equate(int x1, int x2, int x3, int x4) {
        return x1 + 2 * x2 + 3 * x3 + 4 * x4;
    }

    public static class Report {
        private final Gen gen;
        private final int mistake;

        private Report(Gen gen, int mistake) {
            this.gen = gen;
            this.mistake = mistake;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Report)) return false;
            Report report = (Report) o;
            return mistake == report.mistake &&
                    gen.equals(report.gen);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gen, mistake);
        }

        @Override
        public String toString() {
            return "Report{" +
                    "gen=" + gen +
                    ", mistake=" + mistake +
                    '}';
        }


        public Gen getGen() {
            return gen;
        }

        public int getMistake() {
            return mistake;
        }
    }
}
