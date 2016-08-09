package net.braingang.houndlib.model;

import android.telephony.CellInfoGsm;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 */
public class CellularGsm extends AbstractCellular {
    private int arfcn;
    private int bsic;
    private int cid;
    private int lac;
    private int mcc;
    private int mnc;
    private int psc;
    private int asuLevel;
    private int dbm;
    private int level;

    public CellularGsm(CellInfoGsm arg) {
        setCellType("gsm");
        setRegisterFlag(arg.isRegistered());

        //arfcn = arg.getCellIdentity().getArfcn();
        //bsic = arg.getCellIdentity().getBsic();
        cid = arg.getCellIdentity().getCid();
        lac = arg.getCellIdentity().getLac();
        mcc = arg.getCellIdentity().getMcc();
        mnc = arg.getCellIdentity().getMnc();
        psc = arg.getCellIdentity().getPsc();

        asuLevel = arg.getCellSignalStrength().getAsuLevel();
        dbm = arg.getCellSignalStrength().getDbm();
        level = arg.getCellSignalStrength().getLevel();
    }

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("cellType", getCellType());
        jsonObject.put("registerFlag", isRegisterFlag());
        jsonObject.put("arfcn", arfcn);
        jsonObject.put("bsic", bsic);
        jsonObject.put("cid", cid);
        jsonObject.put("lac", lac);
        jsonObject.put("mcc", mcc);
        jsonObject.put("mnc", mnc);

        jsonObject.put("asuLevel", asuLevel);
        jsonObject.put("dbm", dbm);
        jsonObject.put("level", level);

        return jsonObject;
    }
}
