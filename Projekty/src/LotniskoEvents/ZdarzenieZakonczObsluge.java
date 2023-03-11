package LotniskoEvents;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

public class ZdarzenieZakonczObsluge extends BasicSimEvent<OkienkoObslugi, Pasazer> {

    private OkienkoObslugi okienkoObslugi;

    public ZdarzenieZakonczObsluge(OkienkoObslugi parent, double delay, Pasazer pasazer) throws  SimControlException{
        super(parent, delay, pasazer);
        this.okienkoObslugi = parent;
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void stateChange() throws SimControlException {
        System.out.println(simTimeFormatted()+": Koniec obslugi pasazera nr: "+eventParams.getKolejnyNr() + " Okienko-"+ okienkoObslugi.numerOkienka);
        okienkoObslugi.setWolneOkienko(true);
        //zaplanuj ponownie obsluge, jesli sa pasazerowie w kolejce

        if (okienkoObslugi.liczbaPasazerow() > 0){
            okienkoObslugi.rozpocznijObsluge = new ZdarzenieRozpocznijObsluge(okienkoObslugi, 0.0);
        }
    }


    @Override
    public Pasazer getEventParams() {
        return null;
    }
}
