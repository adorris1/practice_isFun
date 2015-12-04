package code;

import java.util.LinkedList;

public class DestinationRouter {

	private static LatLng start;

	public static LinkedList<LatLng> distanceSort(LinkedList<LatLng> list) {

		start = list.getFirst();

		LinkedList<LatLng> sortedList = new LinkedList<LatLng>();
		sortedList.addFirst(start);
		list.removeFirst();
		double lowDist = 0.0;
		LatLng shortest;
		int SIZE = list.size();
		for (int i = 0; i < SIZE; i++) {

			shortest = list.peekFirst();
			lowDist = CoordDistance(start, shortest);

			for (int j = 0; j < list.size(); j++) {
				LatLng pair1 = list.get(j);
				double dist = CoordDistance(start, pair1);
				if (dist < lowDist) {
					lowDist = dist;
					shortest = pair1;

				}
			}
			sortedList.addLast(shortest);
			list.remove(shortest);
			start = shortest;
		}
		return sortedList;
	}

	/**
	 * Distance between two points on a coordinate plane.
	 * 
	 * @param a
	 *            first LatLng
	 * @param b
	 *            second LatLng
	 * @return distance
	 */
	private static double CoordDistance(LatLng a, LatLng b) {
		double x1 = a.lat;
		double x2 = b.lat;

		double y1 = a.lng;
		double y2 = b.lng;

		double dist = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);

		return Math.sqrt(dist);
	}
}
