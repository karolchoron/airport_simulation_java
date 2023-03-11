package LotniskoEvents;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

//NIC W TYM PLIKU NIE RUSZALEM, NIE ZMIENIALEM

public class DepartureEvent extends BasicSimEvent<Lotnisko, Object> {

	public DepartureEvent(Lotnisko lotnisko, double delay, int priority) throws SimControlException {
		super(lotnisko, delay, priority);
	}

	@Override
	protected void stateChange() throws SimControlException {
		Lotnisko lotnisko = getSimObj();
		
		
		if (lotnisko.onTheGround > 0) {
			lotnisko.onTheGround--;
			lotnisko.mvOnTheGround.setValue(lotnisko.onTheGround);
			System.out.println(
					simTime() + " - Odlecial samolot. Na plycie aktualnie jest: " + lotnisko.onTheGround + " samolot(ow)");
			if (lotnisko.onTheGround>0)
				lotnisko.departureEvent = new DepartureEvent(lotnisko, lotnisko.departureInterval, lotnisko.priorityDI);
		}		
	}

	@Override
	protected void onTermination() throws SimControlException {
	}

	@Override
	public Object getEventParams() {
		return null;
	}
}
