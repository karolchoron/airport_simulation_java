package LotniskoEvents;

import dissimlab.random.RNGenerator;
import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class ZdarzenieRozpocznijObsluge extends BasicSimEvent<OkienkoObslugi, Pasazer> {
    public OkienkoObslugi okienko;
    private RNGenerator generator;

    //DO OKIENKA 1
    public static double sumaCzasowOczekiwaniaOkienko1 = 0;
    public static double sumaCzasowObslugiwaniaOkienko1 = 0;
    public static int liczbaOsobOkienko1 = 0;

    public static double getSredniaCzasowOczekiwaniaOkienko1(){
        return sumaCzasowOczekiwaniaOkienko1/liczbaOsobOkienko1;
    }

    public static double getSredniaCzasowObslugiwaniaOkienko1(){
        return sumaCzasowObslugiwaniaOkienko1/liczbaOsobOkienko1;
    }

    //DO OKIENKA 2
    public static double sumaCzasowOczekiwaniaOkienko2 = 0;
    public static double sumaCzasowObslugiwaniaOkienko2 = 0;
    public static int liczbaOsobOkienko2 = 0;

    public static double getSredniaCzasowOczekiwaniaOkienko2(){
        return sumaCzasowOczekiwaniaOkienko2/liczbaOsobOkienko2;
    }

    public static double getSredniaCzasowObslugiwaniaOkienko2(){
        return sumaCzasowObslugiwaniaOkienko2/liczbaOsobOkienko2;
    }
    //konstruktor
    public ZdarzenieRozpocznijObsluge(OkienkoObslugi okienko, double delay) throws SimControlException{
        super(okienko, delay);
        generator = new RNGenerator();
        this.okienko = okienko;
    }

    @Override
    protected void stateChange() throws SimControlException {
        if(okienko.liczbaPasazerow() > 0) {
            //zablokuj okienko
            okienko.setWolneOkienko(false);
            //Pobierz pasazera
            Pasazer pasazer = okienko.usun();
            //Wygeneruj czas obslugi   - rozklad wykladniczy z parametrem lambda
            double czasObslugi;
            do{
                czasObslugi = generator.exponential(2.0);
            } while (czasObslugi <=0.0);

            //zapamietaj dane monitorowane
            okienko.MVczasy_obslugi.setValue(czasObslugi);
            okienko.MVczasy_oczekiwania.setValue(simTime() - pasazer.getCzasPrzybycia(), simTime());


            //jezeli nr pasazera jest parzysty to pasazer idzie do okienka 2
            //jezeli jest nieparzysty, to idzie do okienka 1
            // dzieki temu ludzie idą po rowno do okienek
            //ORAZ obliczenie sredniego czasu oczekiwania oraz obslugiwania
				if(pasazer.getKolejnyNr()%2==0){
                    okienko.numerOkienka = 2;
                    //do obliczenia sredniego czasu w kolejce przy okienku 2
                    sumaCzasowOczekiwaniaOkienko2 = sumaCzasowOczekiwaniaOkienko2 + okienko.MVczasy_obslugi.getValue();
                    sumaCzasowObslugiwaniaOkienko2 = sumaCzasowObslugiwaniaOkienko2 + okienko.MVczasy_oczekiwania.getValue();
                    liczbaOsobOkienko2++;
				}
				else{
                    okienko.numerOkienka = 1;
                    //do obliczenia sredniego czasu w kolejce przy okienku 1
                    sumaCzasowOczekiwaniaOkienko1 = sumaCzasowOczekiwaniaOkienko1 + okienko.MVczasy_obslugi.getValue();
                    sumaCzasowObslugiwaniaOkienko1 = sumaCzasowObslugiwaniaOkienko1 + okienko.MVczasy_oczekiwania.getValue();
                    liczbaOsobOkienko1++;
				}

            System.out.println(simTimeFormatted()+": Poczatek obslugi pasazera nr: "+pasazer.getKolejnyNr() + " Okienko-"+ okienko.numerOkienka);
            // Zaplanuj koniec obslugi
            okienko.zakonczObsluge = new ZdarzenieZakonczObsluge(okienko, czasObslugi, pasazer);
        }
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    public Pasazer getEventParams() {
        return null;
    }
}
