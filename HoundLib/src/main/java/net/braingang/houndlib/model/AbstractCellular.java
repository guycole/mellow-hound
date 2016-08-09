package net.braingang.houndlib.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * abstract container parent
 */
public abstract class AbstractCellular {
    private String cellType;
    private boolean registerFlag;

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String arg) {
        cellType = arg;
    }

    public boolean isRegisterFlag() {
        return registerFlag;
    }

    public void setRegisterFlag(boolean arg) {
        registerFlag = arg;
    }

    public abstract JSONObject toJson() throws JSONException;
}