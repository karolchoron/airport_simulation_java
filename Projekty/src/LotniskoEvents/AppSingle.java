package LotniskoEvents;

import dissimlab.monitors.Diagram;
import dissimlab.monitors.Diagram.DiagramType;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;


public class AppSingle {


	public static void main(String[] args) {

		ZdarzenieRozpocznijObsluge zdarzenie;

		try{
			SimManager simMgr = SimManager.getInstance();
			OkienkoObslugi okienko = new OkienkoObslugi();
			Lotnisko lotnisko = new Lotnisko(3, 3.5, 2, 7, 6, 5, okienko);
			
			// Dwa sposoby planowanego końca symulacji
			simMgr.setEndSimTime(20);
			// lub
			//SimControlEvent stopEvent = new SimControlEvent(20.0, SimControlStatus.STOPSIMULATION);
		
			// Uruchomienie symulacji metodą "start"
			simMgr.startSimulation();


			//WYSWIETLANIE SREDNIEJ CZASOW OCZEKIWANIA I OBSLUGIWANIA
			System.out.println("-----------------------------------------------------------------");
			System.out.println("DANE ODNOSNIE CZASOW PRZY OKIENKU 1: ");
			System.out.println("Srednia czasow oczekiwania przy okienku1: " + ZdarzenieRozpocznijObsluge.getSredniaCzasowOczekiwaniaOkienko1());
			System.out.println("Srednia czasowo obslugiwania przy okienku1: " + ZdarzenieRozpocznijObsluge.getSredniaCzasowObslugiwaniaOkienko1());
			System.out.println("-----------------------------------------------------------------");
			System.out.println("DANE ODNOSNIE CZASOW PRZY OKIENKU 2: ");
			System.out.println("Srednia czasow oczekiwania przy okienku2: " + ZdarzenieRozpocznijObsluge.getSredniaCzasowOczekiwaniaOkienko2());
			System.out.println("Srednia czasowo obslugiwania przy okienku2: " + ZdarzenieRozpocznijObsluge.getSredniaCzasowObslugiwaniaOkienko2());
			System.out.println("-----------------------------------------------------------------");
			System.out.println("KONIEC");

			Diagram d1 = new Diagram(DiagramType.TIME, "B-kolejka R-inTheAir G-onTheGround B-runwayFree");
			d1.add(lotnisko.mvInTheAir, java.awt.Color.RED);
			d1.add(lotnisko.mvOnTheGround, java.awt.Color.GREEN);
			d1.add(lotnisko.mvRunwayFree, java.awt.Color.BLUE);
			d1.add(lotnisko.okienkoObslugi.MVdlKolejki, java.awt.Color.BLACK);  //czarny na wykresie to kolejka
			d1.show();

			
		}
		catch (SimControlException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
