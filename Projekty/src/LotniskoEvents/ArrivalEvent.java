package LotniskoEvents;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;


//NIC W TYM PLIKU NIE RUSZALEM, NIE ZMIENIALEM

public class ArrivalEvent extends BasicSimEvent<Lotnisko, Object> {

	public ArrivalEvent(Lotnisko lotnisko, double delay, int priority) throws SimControlException {
		super(lotnisko, delay, priority);
	}

	@Override
	protected void stateChange() throws SimControlException {
		Lotnisko lotnisko = getSimObj(); 
		/*
		 * do { arrivalInterval = (int) rng.exponential(0.3); } while
		 * (arrivalInterval<=0);
		 */
		lotnisko.inTheAir++;
		lotnisko.mvInTheAir.setValue(lotnisko.inTheAir);
		System.out
				.println(simTime() + " - Przybyl samolot. Nad lotniskiem aktualnie jest: " + lotnisko.inTheAir + " samolot(ow)");
		lotnisko.arrivalEvent = new ArrivalEvent(lotnisko, lotnisko.arrivalInterval, lotnisko.priorityAI);
		if (lotnisko.runwayFree) {
			lotnisko.runwayFree = false;
			lotnisko.mvRunwayFree.setValue(0);
			lotnisko.landingEvent = new LandingEvent(lotnisko, lotnisko.landingDuration, lotnisko.priorityLD);
			System.out.println(simTime() + " - Zaplanowano ladowanie");
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
