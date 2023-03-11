package LotniskoEvents;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.random.RNGenerator;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Lotnisko extends BasicSimObj {
	OkienkoObslugi okienkoObslugi; //okienko obslugi
	int inTheAir; // liczba samolotów w powietrzu
	int onTheGround; // liczba samolotów czekających na lotnisku
	boolean runwayFree; // dostępność pasa lądowania
	double arrivalInterval; // okres pomiedzy kolejnymi przylotami
	double landingDuration; // czas trwania lądowania
	double departureInterval; // okres pomiędzy odlotami
	ArrivalEvent arrivalEvent;
	LandingEvent landingEvent;
	DepartureEvent departureEvent;
	int priorityAI;
	int priorityLD;
	int priorityDI;
	MonitoredVar mvOnTheGround, mvInTheAir, mvRunwayFree;
	RNGenerator rng;

	public Lotnisko(double arrivalInterval, double landingDuration, double departureInterval, int priorityAI, int priorityLD, int priorityDI, OkienkoObslugi okienkoObslugi) throws SimControlException {
		this.okienkoObslugi = okienkoObslugi;
		this.inTheAir = 0;
		this.onTheGround = 0;
		this.runwayFree = true;
		this.arrivalInterval = arrivalInterval;
		this.landingDuration = landingDuration;
		this.departureInterval = departureInterval;
		arrivalEvent = new ArrivalEvent(this, arrivalInterval, priorityAI);
		mvOnTheGround = new MonitoredVar();
		mvInTheAir = new MonitoredVar(); 
		mvRunwayFree = new MonitoredVar();
		mvRunwayFree.setValue(1);
		mvInTheAir.setValue(0);
		mvOnTheGround.setValue(0);
		this.priorityAI = priorityAI; 
		this.priorityLD = priorityLD;
		this.priorityDI = priorityDI;
		rng=new RNGenerator();
	}

	@Override
	public boolean filter(IPublisher publisher, INotificationEvent event) {
		return false;
	}

	@Override
	public void reflect(IPublisher publisher, INotificationEvent event) {
	}

}
