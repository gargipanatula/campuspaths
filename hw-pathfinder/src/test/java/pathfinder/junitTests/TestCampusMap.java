package pathfinder.junitTests;

import pathfinder.CampusMap;
import pathfinder.CampusPaths;
import graph.Graph;
import graph.Graph.*;
import org.junit.Test;
import pathfinder.CampusPaths;
import pathfinder.datastructures.Point;

import static org.junit.Assert.assertEquals;

public class TestCampusMap {
    CampusMap campusMap = new CampusMap();
    Graph<Point, Double> graph = new Graph<Point, Double>();

    // tests CampusMap.longNameForShort()
    @Test
    public void testLongNameForShort() {
        boolean threw = false;
        try {
            campusMap.longNameForShort("");
        } catch (IllegalArgumentException e) {
            threw = true;
        }
        assertEquals(true, threw);
    }

    // tests CampusMap.findShortestPath()
    @Test
    public void testFindShortestPath() {
        boolean threwNull = false;
        try {
            campusMap.findShortestPath(null, null);
        } catch (IllegalArgumentException e) {
            threwNull = true;
        }
        assertEquals(true, threwNull);

        boolean threwInvalid = false;
        try {
            campusMap.findShortestPath("", "");
        } catch (IllegalArgumentException e) {
            threwInvalid = true;
        }
        assertEquals(true, threwInvalid);
    }
}
