package my.learning.jf.task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MountainTask {
    public static void main(String[] args) throws IOException {
        int n, m, s, t;
        List<List<Integer>> graph;
        List<Integer> heights;
        try (BufferedReader r = new BufferedReader(new InputStreamReader(System.in))) {
            List<Integer> nmst = Arrays.stream(r.readLine().trim().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            n = nmst.get(0);
            m = nmst.get(1);
            s = nmst.get(2);
            t = nmst.get(3);

            graph = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<Integer>());
            }

            heights = Arrays.stream(r.readLine().trim().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());

            for (int i = 0; i < m; i++) {
                String[] sPair = r.readLine().trim().split(" ");
                graph.get(Integer.parseInt(sPair[0]) - 1).add(Integer.parseInt(sPair[1]) - 1);
            }
        }

        int start = s - 1;
        int end = t - 1;
        List<Path> res = findPaths(graph, start, end)
                .stream()
                .map(path -> new Path(path, computeAttraction(heights, path)))
                .collect(Collectors.toList());

        for (int i = 1; i < n; i++) {
            final int minimum = i;
            System.out.println(res.stream()
                    .filter(path -> path.length() >= minimum)
                    .mapToInt(Path::getAttractive)
                    .max()
                    .orElse(-1));
        }
    }

    private static int computeAttraction(List<Integer> heights, List<Integer> path) {
        int min = Integer.MAX_VALUE;
        for (int i = 1; i < path.size(); i++) {
            min = Math.min(min, Math.abs(heights.get(path.get(i)) - heights.get(path.get(i-1))));
        }

        return min;
    }

    private static List<List<Integer>> findPaths(List<List<Integer>> graph, int start, int end) {
        List<List<Integer>> res = new ArrayList<List<Integer>>(graph.size());
        List<List<Integer>> paths = new ArrayList<List<Integer>>(graph.size()) {{
            add(Arrays.asList(start));
        }};

        while (!paths.isEmpty()) {
            List<Integer> curr = paths.remove(0);

            if (curr.get(curr.size() - 1) == end) res.add(curr);

            if (graph.get(curr.get(curr.size() - 1)).isEmpty()) continue;

            for (Integer neighbor : graph.get(curr.get(curr.size() - 1))) {
                List<Integer> neighborPath = new ArrayList<>(curr);
                neighborPath.add(neighbor);
                paths.add(neighborPath);
            }
        }

        return res;
    }
}

class Path {
    private final List<Integer> path;
    private final int attractive;

    Path(List<Integer> path, int attractive) {
        this.path = path;
        this.attractive = attractive;
    }

    int length() {
        return path.size() - 1;
    }

    int getAttractive() {
        return attractive;
    }
}
