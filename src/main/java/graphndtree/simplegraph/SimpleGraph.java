package graphndtree.simplegraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class SimpleGraph {

    public static void main(String[] args) {
        File file = new File("src/main/resources/testdata/undirected_data.data");
        AdjacencyListGraph.findShortestPathUsingAdjacencyList(file, 1, 4);
        AdjacencyMatrixGraph matrix=new AdjacencyMatrixGraph(9);
        matrix.findShortestPathUsingAdjacencyMatrix(file,1,8);
    }


}

class AdjacencyListGraph {
    static Map<Integer, Set<Integer>> adjList = new HashMap<>();

    static void findShortestPathUsingAdjacencyList(File file, int start, int end) {
        createAdjListGraph(file);
        System.out.println(adjList);
        pathUsingDFS(start, end);
        shortPathUsingBFS(start, end);
    }

    private static void shortPathUsingBFS(int start, int end) {

    }

    private static void pathUsingDFS(int start, int end) {
        Stack<Integer> stack = new Stack<>();
        Set<Integer> visited = new HashSet<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            if (start != end) {
                if (!visited.contains(start)) {
                    visited.add(start);
                }
                Optional<Integer> temp = adjList.get(start).stream().filter(ele -> !visited.contains(ele)).findFirst();
                if (temp.isPresent()) {
                    start = temp.get();
                    stack.push(start);
                } else {
                    stack.pop();
                    start = stack.peek();
                }

            } else {
                System.out.println("Found the path from source to destination ");
                break;
            }
        }
    }

    private static void createAdjListGraph(File file) {
        BufferedReader br = new BufferedReader(getFileReader(file));
        br.lines().forEach(line -> {
            String[] split = line.split(" ");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            if (adjList.containsKey(start)) adjList.get(start).add(end);
            else adjList.put(start, new HashSet<>(Arrays.asList(end)));
            if (adjList.containsKey(end)) adjList.get(end).add(start);
            else adjList.put(end, new HashSet<>(Arrays.asList(start)));
        });
    }

    private static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
class AdjacencyMatrixGraph{
    int[][] arr;

    public AdjacencyMatrixGraph(int nodes) {
        arr=new int[nodes][nodes];
    }

    void findShortestPathUsingAdjacencyMatrix(File file, int start, int end) {
        createAdjMatrixGraph(file);
        System.out.println(arr);
        pathUsingDfs(start, end);
        shortPathUsingBFS(start, end);
    }

    private void pathUsingDfs(int start, int end) {

    }

    private static void shortPathUsingBFS(int start, int end) {

    }


    private  void createAdjMatrixGraph(File file) {
        BufferedReader br = new BufferedReader(getFileReader(file));
        br.lines().forEach(line -> {
            String[] split = line.split(" ");
            int start = Integer.parseInt(split[0]);
            int end = Integer.parseInt(split[1]);
            arr[start-1][end-1]=1;
            arr[end-1][start-1]=1;
        });
    }

    private static FileReader getFileReader(File file) {
        try {
            return new FileReader(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
