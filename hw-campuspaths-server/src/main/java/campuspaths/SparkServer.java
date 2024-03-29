/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.*;

import java.util.ArrayList;
import java.util.List;

// Gets requests from the React Component and returns data based on the request
public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        CampusMap map = new CampusMap();

        // Finds the shortest path between two buildings.
        Spark.get("/find-path", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                // Gets start and destination strings
                String start = request.queryParams("start");
                String destination = request.queryParams("destination");
                try {
                    Path<Point> shortestPath = map.findShortestPath(start, destination); // Contains the segments of the shortest path
                    List<double[]> segments = new ArrayList<>(); // Contains the coordinates of the segments of the shortest path

                    if (shortestPath == null) {
                        return segments;
                    }

                    // Puts the coordinates of each segment into a double[], and adds that double[] to segments
                    for (Path<Point>.Segment s : shortestPath) {
                        double[] segment = new double[4];
                        Point firstPoint = s.getStart();
                        segment[0] = firstPoint.getX();
                        segment[1] = firstPoint.getY();

                        Point secondPoint = s.getEnd();
                        segment[2] = secondPoint.getX();
                        segment[3] = secondPoint.getY();

                        segments.add(segment);
                    }

                    Gson gson = new Gson();
                    return gson.toJson(segments);
                } catch(IllegalArgumentException e) {
                    return e.getMessage();
                }
            }
        });
    }
}
