package LotniskoEvents;

import dissimlab.random.RNGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class LandingEvent extends BasicSimEvent<Lotnisko, Object> {

	RNGenerator rng = new RNGenerator();

	public LandingEvent(Lotnisko lotnisko, double delay, int priority) throws SimControlException {
		super(lotnisko, delay, priority);

	}

	@Override
	protected void stateChange() throws SimControlException {
		Lotnisko lotnisko = getSimObj();
		if (lotnisko.inTheAir > 0) {
			lotnisko.inTheAir--;
			lotnisko.mvInTheAir.setValue(lotnisko.inTheAir);
			lotnisko.onTheGround++;
			lotnisko.mvOnTheGround.setValue(lotnisko.onTheGround);
			System.out.println(simTime() + " - Wyladowal samolot. Na plycie aktualnie jest: " + lotnisko.onTheGround
					+ " a w powietrzu " + lotnisko.inTheAir + " samolot(ow)");
			if (lotnisko.onTheGround == 1)
				lotnisko.departureEvent = new DepartureEvent(lotnisko, lotnisko.departureInterval, lotnisko.priorityDI); 
			if (lotnisko.inTheAir > 0) {
				lotnisko.landingEvent = new LandingEvent(lotnisko, lotnisko.landingDuration, lotnisko.priorityLD); 
				System.out.println(simTime() + " - Zaplanowano ladowanie");
			} else {
				lotnisko.runwayFree = true;
				lotnisko.mvRunwayFree.setValue(1);
				System.out.println(simTime() + " - Zwolniono pas ladowania");
			}

			//dopisany kod z klasy Wyladowal samolot .pdf strona 23
			int liczbaPasazerow = rng.uniformInt(2, 10);
			for (int i = 0; i<liczbaPasazerow; i ++){
				Pasazer pasazer = new Pasazer(simTime(), lotnisko.okienkoObslugi);
				lotnisko.okienkoObslugi.dodaj(pasazer);
				System.out.println(simTimeFormatted()+": Samolot: Dodano nowego pasazera nr: " + pasazer.getKolejnyNr());
				// aktywuj obsluge, jezeli kolejka byla pusta (okienko "spalo")
				if(lotnisko.okienkoObslugi.liczbaPasazerow()==1 && lotnisko.okienkoObslugi.isWolneOkienko()){
					lotnisko.okienkoObslugi.rozpocznijObsluge = new ZdarzenieRozpocznijObsluge(lotnisko.okienkoObslugi, 0.0);
				}
			}


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
