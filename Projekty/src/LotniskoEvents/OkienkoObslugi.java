package LotniskoEvents;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

import java.util.LinkedList;

public class OkienkoObslugi extends BasicSimObj {

    private LinkedList <Pasazer> kolejka;
    // domyslnie pasazer idzie do okienka 1
    public int numerOkienka = 1;
    private boolean wolne = true;
    public ZdarzenieRozpocznijObsluge rozpocznijObsluge;
    public ZdarzenieZakonczObsluge zakonczObsluge;
    public MonitoredVar MVczasy_obslugi;
    public MonitoredVar MVczasy_oczekiwania;
    public MonitoredVar MVdlKolejki;

    public OkienkoObslugi() throws SimControlException {
        kolejka = new LinkedList <Pasazer>();
        MVdlKolejki = new MonitoredVar();
        MVczasy_obslugi = new MonitoredVar();
        MVczasy_oczekiwania = new MonitoredVar();
    }

    //Wstawianie pazazera do kolejki
    public int dodaj(Pasazer zgl) {
        kolejka.add(zgl);
        MVdlKolejki.setValue(kolejka.size());
        return kolejka.size();
    }

    //Pobranie pasazera z oklejki
    public Pasazer usun(){
        Pasazer zgl = (Pasazer) kolejka.removeFirst();
        MVdlKolejki.setValue(kolejka.size());
        return zgl;
    }

    public int liczbaPasazerow(){
        return kolejka.size();
    }

    public boolean isWolneOkienko(){
        return wolne;
    }

    public void setWolneOkienko(boolean wolne){
        this.wolne = wolne;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
