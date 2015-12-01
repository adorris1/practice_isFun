package jUnitTests;

import static org.junit.Assert.*;

import org.junit.Test;
import code.LatLng;
import code.computeLatLng;
import code.generateCoordinatesList;
import java.util.LinkedList;
import code.DestinationRouter;
import static org.hamcrest.CoreMatchers.is;


public class LatLngTester {
	DestinationRouter router = new DestinationRouter();

	@Test
	public void test() {
		LatLng pair1 = new LatLng(0,32.808850,-117.225517 );
		LatLng pair2 = new LatLng(1,32.808509,-117.242921);
		LatLng pair3 = new LatLng(1,32.830877,-117.280677);
		LinkedList<LatLng> list = new LinkedList<LatLng>();
		LinkedList<LatLng> sortedList = new LinkedList<LatLng>();
		LinkedList<LatLng> testList = new LinkedList<LatLng>();

		list.add(pair2);
		list.add(pair1);
		list.add(pair3);
		sortedList = router.distanceSort(list);
		
		testList.add(pair1);
		testList.add(pair2);
		testList.add(pair3);
		assertThat(sortedList, is(testList));

		

		
		
		
		fail("Not yet implemented");
	}

}
