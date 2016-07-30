package net.braingang.houndlib.model;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;

import java.io.Serializable;

/**
 *
 */
public class Cellular implements Serializable {

    private String cellType = "unknown";

    //strength
    private Integer asuLevel = 0;
    private Integer cdmaDbm = 0;
    private Integer cdmaEcio = 0;
    private Integer cdmaLevel = 0;
    private Integer dbm = 0;
    private Integer evdoDbm = 0;
    private Integer evdoEcio = 0;
    private Integer evdoLevel = 0;
    private Integer evdoSnr = 0;
    private Integer signalLevel = 0;
    private Integer timeAdvance = 0;

    //identity
    private Integer cellId = 0;       // CID
    private Integer countryCode = 0;  // MCC
    private Integer latitude = 0;
    private Integer longitude = 0;
    private Integer locationCode = 0; // LAC
    private Integer networkCode = 0;  // MNC
    private Integer physicalCellId = 0;
    private Integer rfChannel = 0;    // UARFCN
    private Integer scrambleCode = 0; // PSC
    private Integer stationId = 0;
    private Integer trackCode = 0;
    private Integer systemCode = 0;

    public Cellular(CellInfo cellInfo) {
        if (cellInfo instanceof CellInfoCdma) {
            System.out.println("cdma");
            parseCdma((CellInfoCdma) cellInfo);
        } else if (cellInfo instanceof CellInfoGsm) {
            System.out.println("gsm");
            parseGsm((CellInfoGsm) cellInfo);
        } else if (cellInfo instanceof CellInfoLte) {
            System.out.println("lte");
            parseLte((CellInfoLte) cellInfo);
        } else if (cellInfo instanceof CellInfoWcdma) {
            System.out.println("wcdma");
            parseWcdma((CellInfoWcdma) cellInfo);
        }
    }

    private void parseCdma(CellInfoCdma arg) {
        cellType = "CDMA";

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        cdmaDbm = arg.getCellSignalStrength().getCdmaDbm();
        cdmaEcio = arg.getCellSignalStrength().getCdmaEcio();
        cdmaLevel = arg.getCellSignalStrength().getCdmaLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        evdoDbm = arg.getCellSignalStrength().getEvdoDbm();
        evdoEcio = arg.getCellSignalStrength().getEvdoEcio();
        evdoLevel = arg.getCellSignalStrength().getEvdoLevel();
        evdoSnr = arg.getCellSignalStrength().getEvdoSnr();
        signalLevel = arg.getCellSignalStrength().getLevel();

        stationId = arg.getCellIdentity().getBasestationId();
        latitude = arg.getCellIdentity().getLatitude();
        longitude = arg.getCellIdentity().getLongitude();
        networkCode = arg.getCellIdentity().getNetworkId();
        systemCode = arg.getCellIdentity().getSystemId();
    }

    private void parseGsm(CellInfoGsm arg) {
        cellType = "GSM";

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        signalLevel = arg.getCellSignalStrength().getLevel();

        //      rfChannel = arg.getCellIdentity();
        //stationId = arg.getCellIdentity().getBsic();
        cellId = arg.getCellIdentity().getCid();
        locationCode = arg.getCellIdentity().getLac();
        countryCode = arg.getCellIdentity().getMcc();
        networkCode = arg.getCellIdentity().getMnc();
    }

    private void parseLte(CellInfoLte arg) {
        cellType = "LTE";

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        signalLevel = arg.getCellSignalStrength().getLevel();
        timeAdvance = arg.getCellSignalStrength().getTimingAdvance();

        cellId = arg.getCellIdentity().getCi();
        countryCode = arg.getCellIdentity().getMcc();
        physicalCellId = arg.getCellIdentity().getPci();
        //      rfChannel = arg.getCellIdentity().getEarfcn();
        trackCode = arg.getCellIdentity().getTac();
    }

    private void parseWcdma(CellInfoWcdma arg) {
        cellType = "WCDMA";

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        signalLevel = arg.getCellSignalStrength().getLevel();

        cellId = arg.getCellIdentity().getCid();
        countryCode = arg.getCellIdentity().getMcc();
        locationCode = arg.getCellIdentity().getLac();
        networkCode = arg.getCellIdentity().getMnc();
        //      rfChannel = arg.getCellIdentity();
        scrambleCode = arg.getCellIdentity().getPsc();
    }
}
