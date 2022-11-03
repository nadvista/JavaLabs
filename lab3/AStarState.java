import java.util.HashMap;

/**
 * This class stores the basic state necessary for the A* algorithm to compute a
 * path across a map. This state includes a collection of "open waypoints" and
 * another collection of "closed waypoints." In addition, this class provides
 * the basic operations that the A* pathfinding algorithm needs to perform its
 * processing.
 **/
public class AStarState {
    /** This is a reference to the map that the A* algorithm is navigating. **/
    private Map2D map;

    private HashMap<Location, Waypoint> closedVertexes;
    private HashMap<Location, Waypoint> openedVertexes;

    /**
     * Initialize a new state object for the A* pathfinding algorithm to use.
     **/
    public AStarState(Map2D map) {
        if (map == null)
            throw new NullPointerException("map cannot be null");

        closedVertexes = new HashMap<Location, Waypoint>();
        openedVertexes = new HashMap<Location, Waypoint>();

        this.map = map;
    }

    /** Returns the map that the A* pathfinder is navigating. **/
    public Map2D getMap() {
        return map;
    }

    /**
     * This method scans through all open waypoints, and returns the waypoint
     * with the minimum total cost. If there are no open waypoints, this method
     * returns <code>null</code>.
     **/
    public Waypoint getMinOpenWaypoint() {
        var minCost = Float.MAX_VALUE;
        Waypoint minWaypoint = null;

        for (var waypointEntrySet : openedVertexes.entrySet()) {
            var cost = waypointEntrySet.getValue().getTotalCost();
            if (cost <= minCost) {
                minCost = cost;
                minWaypoint = waypointEntrySet.getValue();
            }
        }

        return minWaypoint;
    }

    /**
     * This method adds a waypoint to (or potentially updates a waypoint already
     * in) the "open waypoints" collection. If there is not already an open
     * waypoint at the new waypoint's location then the new waypoint is simply
     * added to the collection. However, if there is already a waypoint at the
     * new waypoint's location, the new waypoint replaces the old one <em>only
     * if</em> the new waypoint's "previous cost" value is less than the current
     * waypoint's "previous cost" value.
     **/
    public boolean addOpenWaypoint(Waypoint newWP) {

        if (!openedVertexes.containsKey(newWP.getLocation())
                || getPathCost(openedVertexes.get(newWP.getLocation())) > getPathCost(newWP)) {
            openedVertexes.put(newWP.getLocation(), newWP);
            return true;
        }

        return false;
    }

    /** Returns the current number of open waypoints. **/
    public int numOpenWaypoints() {
        return openedVertexes.size();
    }

    /**
     * This method moves the waypoint at the specified location from the
     * open list to the closed list.
     **/
    public void closeWaypoint(Location loc) {
        closedVertexes.put(loc, openedVertexes.remove(loc));
    }

    /**
     * Returns true if the collection of closed waypoints contains a waypoint
     * for the specified location.
     **/
    public boolean isLocationClosed(Location loc) {
        return closedVertexes.containsKey(loc);
    }

    // ------------------------------------------------------------------------->
    private float getPathCost(Waypoint wp) {
        if (wp == null)
            return 0;
        return wp.getPreviousCost() + getPathCost(wp.getPrevious());
    }
}
