package com.graphhopper.reader.gtfs;

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.routing.weighting.AbstractWeighting;
import com.graphhopper.util.EdgeIteratorState;

class PtTravelTimeWeighting extends AbstractWeighting {

	private final GtfsStorage gtfsStorage;

	PtTravelTimeWeighting(FlagEncoder encoder, GtfsStorage gtfsStorage) {
		super(encoder);
		this.gtfsStorage = gtfsStorage;
	}

	@Override
	public double getMinWeight(double distance) {
		return Double.NEGATIVE_INFINITY;
	}

	@Override
	public double calcWeight(EdgeIteratorState edgeState, boolean reverse, int prevOrNextEdgeId) {
		if (edgeState.getEdge() > gtfsStorage.getRealEdgesSize()-1) {
			return 0.0;
		}
		PatternHopEdge edge = gtfsStorage.getEdges().get(edgeState.getEdge());
		int scheduledTravelTime = edge.getTo().arrival_time - edge.getFrom().departure_time;
		System.out.println(scheduledTravelTime);
		return scheduledTravelTime;
	}

	@Override
	public String getName() {
		return "pttraveltime";
	}
}