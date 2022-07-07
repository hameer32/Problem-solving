/**
 * @author UCSD MOOC development team and YOU
 * <p>
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
package graphndtree.geolocationgraph;


import java.util.*;
import java.util.function.Consumer;

/**
 * @author UCSD MOOC development team and YOU
 * <p>
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between
 */
class MapEdge implements Comparator {
    private static final Comparator<MapEdge> MAP_EDGE_COMPARATOR = Comparator.comparing((MapEdge mp) -> mp.getTo().toString())
            .thenComparing((MapEdge mp) -> mp.getFrom().toString())
            .thenComparing(MapEdge::getLength)
            .thenComparing(MapEdge::getRoadName)
            .thenComparing(MapEdge::getRoadType);
    private GeographicPoint from;
    private GeographicPoint to;
    private String roadName;
    private String roadType;
    private double length;
    int hash;

    @Override
    public int hashCode() {
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        MapEdge obb = (MapEdge) obj;
        return MAP_EDGE_COMPARATOR.compare(this, obb) == 0;
    }


    @Override
    public String toString() {
        return "MapEdge{" +
                "from=" + from +
                ", to=" + to +
                ", roadName='" + roadName + '\'' +
                ", roadType='" + roadType + '\'' +
                ", length=" + length +
                '}';
    }


    public void setFrom(GeographicPoint from) {
        this.from = from;
    }

    public void setTo(GeographicPoint to) {
        this.to = to;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public GeographicPoint getFrom() {
        return from;
    }

    public GeographicPoint getTo() {
        return to;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getRoadType() {
        return roadType;
    }

    public double getLength() {
        return length;
    }

    public MapEdge() {
    }

    public MapEdge(GeographicPoint from, GeographicPoint to, String roadName, String roadType, double length) {
        this.from = from;
        this.to = to;
        this.roadName = roadName;
        this.roadType = roadType;
        this.length = length;
        this.hash = Objects.hash(this.getLength(), this.getRoadType(), this.getRoadName(), this.getTo().toString(), this.getFrom().toString());
    }

    @Override
    public int compare(Object o1, Object o2) {
        MapEdge a1 = (MapEdge) o1;
        MapEdge a2 = (MapEdge) o2;
        return ((int) a1.getLength()) - ((int) a2.getLength());
    }
}

class HeadNode implements Comparable{
    @Override
    public String toString() {
        return "HeadNode{" +
                "location=" + location +
                ", distance=" + distance +
                '}';
    }

    public final int HASH = Objects.hash(this.getLocation(), this.getDistance());

    @Override
    public int hashCode() {
        return HASH;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        HeadNode obb = (HeadNode) obj;
        return Comparator.comparing(HeadNode::getDistance)
                .thenComparing((HeadNode a) -> a.getLocation().toString())
                .compare(this, obb) == 0;
    }

        @Override
        public int compareTo(Object o) {
            HeadNode a1 = this;
            HeadNode a2 = (HeadNode) o;
            return (int) (a1.getDistance() - a2.getDistance());
        }

    private GeographicPoint location;
    private double distance;

    public HeadNode(GeographicPoint location, double distance) {
        this.location = location;
        this.distance = distance;
    }

    public GeographicPoint getLocation() {
        return location;
    }

    public void setLocation(GeographicPoint location) {
        this.location = location;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

}

class MapNode {
    private Set<MapEdge> edgeSet;

    public MapNode() {
        edgeSet = new HashSet<>();
    }

    @Override
    public String toString() {
        return "MapNode{" +
                ", edgeSet=" + edgeSet +
                '}';
    }


    public void setEdgeSet(Set<MapEdge> edgeSet) {
        this.edgeSet = edgeSet;
    }


    public Set<MapEdge> getEdgeSet() {
        return edgeSet;
    }


    public MapNode(Set<MapEdge> edgeSet) {
        this.edgeSet = edgeSet;
    }
}

public class MapGraph {
    //TODO: Add your member variables here in WEEK 3
    private final HashMap<GeographicPoint, MapNode> mapGraphHashMap;


    /**
     * Create a new empty MapGraph
     */
    public MapGraph() {
        // TODO: Implement in this constructor in WEEK 3
        mapGraphHashMap = new HashMap<>();
    }

    /**
     * Get the number of vertices (road intersections) in the graph
     *
     * @return The number of vertices in the graph.
     */
    public int getNumVertices() {
        //TODO: Implement this method in WEEK 3
        return mapGraphHashMap.keySet().size();
    }

    /**
     * Return the intersections, which are the vertices in this graph.
     *
     * @return The vertices in this graph as GeographicPoints
     */
    public Set<GeographicPoint> getVertices() {
        //TODO: Implement this method in WEEK 3
        return mapGraphHashMap.keySet();
    }

    /**
     * Get the number of road segments in the graph
     *
     * @return The number of edges in the graph.
     */
    public int getNumEdges() {
        //TODO: Implement this method in WEEK 3
        return mapGraphHashMap.keySet().stream().map(key -> mapGraphHashMap.get(key).getEdgeSet().size()).reduce(0, Integer::sum) / 2;
    }


    /**
     * Add a node corresponding to an intersection at a Geographic Point
     * If the location is already in the graph or null, this method does
     * not change the graph.
     *
     * @param location The location of the intersection
     * @return true if a node was added, false if it was not (the node
     * was already in the graph, or the parameter is null).
     */
    public boolean addVertex(GeographicPoint location) {
        // TODO: Implement this method in WEEK 3
        boolean check = false;
        if (location != null && !mapGraphHashMap.containsKey(location)) {
            mapGraphHashMap.put(location, new MapNode());
            check = true;
        }
        return check;
    }

    /**
     * Adds a directed edge to the graph from pt1 to pt2.
     * Precondition: Both GeographicPoints have already been added to the graph
     *
     * @param from     The starting point of the edge
     * @param to       The ending point of the edge
     * @param roadName The name of the road
     * @param roadType The type of the road
     * @param length   The length of the road, in km
     * @throws IllegalArgumentException If the points have not already been
     *                                  added as nodes to the graph, if any of the arguments is null,
     *                                  or if the length is less than 0.
     */
    public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
                        String roadType, double length) throws IllegalArgumentException {

        //TODO: Implement this method in WEEK 3
        if (from == null
                || to == null
                || roadName == null
                || roadType == null
                || !mapGraphHashMap.containsKey(from)
                || !mapGraphHashMap.containsKey(to)
                || length < 0)
            throw new IllegalArgumentException();
        Set<MapEdge> edgeSetFrom = mapGraphHashMap.get(from).getEdgeSet();
        edgeSetFrom.add(new MapEdge(from, to, roadName, roadType, length));
        mapGraphHashMap.get(from).setEdgeSet(edgeSetFrom);

        Set<MapEdge> edgeSetTo = mapGraphHashMap.get(to).getEdgeSet();
        edgeSetTo.add(new MapEdge(to, from, roadName, roadType, length));
        mapGraphHashMap.get(to).setEdgeSet(edgeSetTo);


    }


    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return bfs(start, goal, temp);
    }

    /**
     * Find the path from start to goal using breadth first search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest (unweighted)
     * path from start to goal (including both start and goal).
     */
    public List<GeographicPoint> bfs(GeographicPoint start,
                                     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 3
        Queue<GeographicPoint> queue = new LinkedList<>();
        Set<GeographicPoint> geographicPointSet = new HashSet();
        Map<GeographicPoint, GeographicPoint> pointers = new HashMap<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            GeographicPoint head = queue.peek();
            if (!head.equals(goal)) {
                if (!geographicPointSet.contains(head)) {
                    mapGraphHashMap.get(head).getEdgeSet().forEach(edge -> {
                        queue.add(edge.getTo());
                        if (!pointers.containsKey(edge.getTo())) {
                            pointers.put(edge.getTo(), head);
                        }
                    });
                }
                geographicPointSet.add(queue.remove());
            } else break;
        }
        List<GeographicPoint> resultPath = new ArrayList<>();
        GeographicPoint head = queue.peek();
        while (head != start) {
            resultPath.add(head);
            head = pointers.get(head);
        }
        resultPath.add(head);
        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return resultPath;
    }


    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        // You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return dijkstra(start, goal, temp);
    }

    /**
     * Find the path from start to goal using Dijkstra's algorithm
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */


    public List<GeographicPoint> dijkstra(GeographicPoint start,
                                          GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 4
        Set<GeographicPoint> visited = new HashSet<>();
        Map<GeographicPoint, HeadNode> routing = new HashMap<>();
//        PriorityQueue<HeadNode> queue = new PriorityQueue<>(Comparator.comparing(HeadNode::getDistance));
        PriorityQueue<HeadNode> queue = new PriorityQueue<>();
        queue.add(new HeadNode(start, 0));
        while (!queue.isEmpty()) {
            HeadNode node = queue.poll();
            if (!node.getLocation().equals(goal)) {
                if (!visited.contains(node.getLocation())) {
                    mapGraphHashMap.get(node.getLocation()).getEdgeSet().forEach(ele -> {

                                if (!visited.contains(ele.getTo())) {
                                    queue.add(new HeadNode(ele.getTo(), ele.getLength() + node.getDistance()));
                                    if (!routing.containsKey(ele.getTo()) ||
                                            (routing.containsKey(ele.getTo())
                                                    && routing.get(ele.getTo()).getDistance() > (node.getDistance() + ele.getLength()))) {
                                        routing.put(ele.getTo(), new HeadNode(node.getLocation(), node.getDistance() + ele.getLength()));
                                    }
                                }
                            }
                    );
                }
                visited.add(node.getLocation());

            } else break;
        }
        List<GeographicPoint> resultPath = new ArrayList<>();
        GeographicPoint head = queue.peek().getLocation();
        while (head != start) {
            resultPath.add(head);
            head = routing.get(head).getLocation();
        }
        resultPath.add(head);
        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return resultPath;

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start The starting location
     * @param goal  The goal location
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
        // Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {
        };
        return aStarSearch(start, goal, temp);
    }

    /**
     * Find the path from start to goal using A-Star search
     *
     * @param start        The starting location
     * @param goal         The goal location
     * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
     * @return The list of intersections that form the shortest path from
     * start to goal (including both start and goal).
     */
    public List<GeographicPoint> aStarSearch(GeographicPoint start,
                                             GeographicPoint goal, Consumer<GeographicPoint> nodeSearched) {
        // TODO: Implement this method in WEEK 4

        // Hook for visualization.  See writeup.
        //nodeSearched.accept(next.getLocation());

        return null;
    }


    public static void main(String[] args) {
        System.out.print("Making a new map...");
        MapGraph firstMap = new MapGraph();
        System.out.print("DONE. \nLoading the map...");
        GraphLoader.loadRoadMap("src/main/resources/testdata/simpletest.map",
                firstMap);
        System.out.println("DONE.");
        System.out.println(firstMap);
        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);

//        System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
        List<GeographicPoint> testroute = firstMap.dijkstra(testStart, testEnd);
        Collections.reverse(testroute);
        testroute.forEach(s -> System.out.print("x:" + s.x + ",y:" + s.y + "---"));
        // You can use this method for testing.


        /* Here are some test cases you should try before you attempt
         * the Week 3 End of Week Quiz, EVEN IF you score 100% on the
         * programming assignment.
         */

//        MapGraph simpleTestMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/testdata/simpletest.map", simpleTestMap);
//
//        GeographicPoint testStart = new GeographicPoint(1.0, 1.0);
//        GeographicPoint testEnd = new GeographicPoint(8.0, -1.0);
//
//        System.out.println("Test 1 using simpletest: Dijkstra should be 9 and AStar should be 5");
//        List<GeographicPoint> testroute = simpleTestMap.bfs(testStart, testEnd);
//        List<GeographicPoint> testroute2 = simpleTestMap.aStarSearch(testStart, testEnd);
//
//
//        MapGraph testMap = new MapGraph();
//        GraphLoader.loadRoadMap("data/maps/utc.map", testMap);
//
//        // A very simple test using real data
//        testStart = new GeographicPoint(32.869423, -117.220917);
//        testEnd = new GeographicPoint(32.869255, -117.216927);
//        System.out.println("Test 2 using utc: Dijkstra should be 13 and AStar should be 5");
//        testroute = testMap.dijkstra(testStart, testEnd);
//        testroute2 = testMap.aStarSearch(testStart, testEnd);
//
//
//        // A slightly more complex test using real data
//        testStart = new GeographicPoint(32.8674388, -117.2190213);
//        testEnd = new GeographicPoint(32.8697828, -117.2244506);
//        System.out.println("Test 3 using utc: Dijkstra should be 37 and AStar should be 10");
//        testroute = testMap.dijkstra(testStart, testEnd);
//        testroute2 = testMap.aStarSearch(testStart, testEnd);



        /* Use this code in Week 3 End of Week Quiz */
		/*MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);


		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		*/

    }

}
