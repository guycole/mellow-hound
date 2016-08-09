package net.braingang.houndlib.model;

import android.telephony.CellInfoCdma;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class CellularCdma extends AbstractCellular {
    private int baseStation;
    private int latitude;
    private int longitude;
    private int network;
    private int system;
    private int asuLevel;
    private int cdmaDbm;
    private int cdmaEcio;
    private int cdmaLevel;
    private int dbm;
    private int evdoDbm;
    private int evdoEcio;
    private int evdoLevel;
    private int evdoSnr;
    private int level;

    public CellularCdma(CellInfoCdma arg) {
        setCellType("cdma");
        setRegisterFlag(arg.isRegistered());

        baseStation = arg.getCellIdentity().getBasestationId();
        latitude = arg.getCellIdentity().getLatitude();
        longitude = arg.getCellIdentity().getLongitude();
        network = arg.getCellIdentity().getNetworkId();
        system = arg.getCellIdentity().getSystemId();

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        cdmaDbm = arg.getCellSignalStrength().getCdmaDbm();
        cdmaEcio = arg.getCellSignalStrength().getCdmaEcio();
        cdmaLevel = arg.getCellSignalStrength().getCdmaLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        evdoDbm = arg.getCellSignalStrength().getEvdoDbm();
        evdoEcio = arg.getCellSignalStrength().getEvdoEcio();
        evdoLevel = arg.getCellSignalStrength().getEvdoLevel();
        evdoSnr = arg.getCellSignalStrength().getEvdoSnr();
        level = arg.getCellSignalStrength().getLevel();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cellType", getCellType());
        jsonObject.put("registerFlag", isRegisterFlag());
        jsonObject.put("baseStation", baseStation);
        jsonObject.put("latitude", latitude);
        jsonObject.put("longitude", longitude);
        jsonObject.put("network", network);
        jsonObject.put("system", system);

        jsonObject.put("asuLevel", asuLevel);
        jsonObject.put("cdmaDbm", cdmaDbm);
        jsonObject.put("cdmaEcio", cdmaEcio);
        jsonObject.put("cdmaLevel", cdmaLevel);
        jsonObject.put("dbm", dbm);
        jsonObject.put("evdoDbm", evdoDbm);
        jsonObject.put("evdoEcio", evdoEcio);
        jsonObject.put("evdoLevel", evdoLevel);
        jsonObject.put("evdoSnr", evdoSnr);
        jsonObject.put("level", level);

        return jsonObject;
    }
}
