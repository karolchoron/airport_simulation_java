package LotniskoEvents;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

public class Pasazer extends BasicSimObj {

    double czasPrzybycia;
    static int nr = 0;
    int kolejnyNr;
    public OkienkoObslugi smo;

    public int getKolejnyNr(){
        return kolejnyNr;
    }

    public void setKolejnyNr(){
        this.kolejnyNr = nr++;
    }

    public Pasazer(double Czas, OkienkoObslugi smo) throws SimControlException{
        czasPrzybycia = Czas;
        setKolejnyNr();
        this.smo = smo;
    }
    
    public void setCzasOdniesienia(double t){
        czasPrzybycia = t;
    }

    public double getCzasPrzybycia(){
        return czasPrzybycia;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
