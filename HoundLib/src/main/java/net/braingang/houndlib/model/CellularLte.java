package net.braingang.houndlib.model;

import android.telephony.CellInfoLte;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class CellularLte extends AbstractCellular {
    private int ci;
    private int earfcn;
    private int mcc;
    private int mnc;
    private int pci;
    private int tac;
    private int asuLevel;
    private int dbm;
    private int level;
    private int timingAdvance;

    public CellularLte(CellInfoLte arg) {
        setCellType("lte");
        setRegisterFlag(arg.isRegistered());

        ci = arg.getCellIdentity().getCi();
        //earfcn = arg.getCellIdentity().get
        mcc = arg.getCellIdentity().getMcc();
        mnc = arg.getCellIdentity().getMnc();
        pci = arg.getCellIdentity().getPci();
        tac = arg.getCellIdentity().getTac();

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        level = arg.getCellSignalStrength().getLevel();
        timingAdvance = arg.getCellSignalStrength().getTimingAdvance();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cellType", getCellType());
        jsonObject.put("registerFlag", isRegisterFlag());
        jsonObject.put("ci", ci);
        jsonObject.put("earfcn", earfcn);
        jsonObject.put("mcc", mcc);
        jsonObject.put("mnc", mnc);
        jsonObject.put("pci", pci);
        jsonObject.put("tac", tac);

        jsonObject.put("asuLevel", asuLevel);
        jsonObject.put("dbm", dbm);
        jsonObject.put("level", level);
        jsonObject.put("timingAdvance", timingAdvance);

        return jsonObject;
    }
}
