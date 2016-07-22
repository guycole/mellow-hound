package net.braingang.houndlib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import net.braingang.houndlib.Constant;

/**
 *
 */
public class CellularModel implements DataBaseModel {
    private Long id;
    private Boolean activeFlag;
    private String note;

    private Long geoLocId;
    private String cellType;

    //strength
    private Integer asuLevel;
    private Integer cdmaDbm;
    private Integer cdmaEcio;
    private Integer cdmaLevel;
    private Integer dbm;
    private Integer evdoDbm;
    private Integer evdoEcio;
    private Integer evdoLevel;
    private Integer evdoSnr;
    private Integer signalLevel;
    private Integer timeAdvance;

    //identity
    private Integer cellId;       // CID
    private Integer countryCode;  // MCC
    private Integer latitude;
    private Integer longitude;
    private Integer locationCode; // LAC
    private Integer networkCode;  // MNC
    private Integer physicalCellId;
    private Integer rfChannel;    // UARFCN
    private Integer scrambleCode; // PSC
    private Integer stationId;
    private Integer trackCode;
    private Integer systemCode;

    @Override
    public void setDefault() {
        id = 0L;
        activeFlag = true;
        note = "No Note";

        geoLocId = 0L;
        cellType = "Unknown";

        asuLevel = -1;
        cdmaDbm = 0;
        cdmaEcio = 0;
        cdmaLevel = 0;
        dbm = 0;
        evdoDbm = 0;
        evdoEcio = 0;
        evdoLevel = 0;
        evdoSnr = 0;
        signalLevel = -1;
        timeAdvance = 0;

        cellId = 0;
        countryCode = 0;
        latitude = 0;
        longitude = 0;
        locationCode = 0;
        networkCode = 0;
        physicalCellId = 0;
        rfChannel = 0;
        scrambleCode = 0;
        stationId = 0;
        trackCode = 0;
        systemCode = 0;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();

        if (activeFlag) {
            cv.put(CellularTable.Columns.ACTIVE_FLAG, Constant.SQL_TRUE);
        } else {
            cv.put(CellularTable.Columns.ACTIVE_FLAG, Constant.SQL_FALSE);
        }

        cv.put(CellularTable.Columns.NOTE, note);

        cv.put(CellularTable.Columns.GEOLOC_ID, geoLocId);
        cv.put(CellularTable.Columns.CELL_TYPE, cellType);

        cv.put(CellularTable.Columns.ASU_LEVEL, asuLevel);
        cv.put(CellularTable.Columns.CDMA_DBM, cdmaDbm);
        cv.put(CellularTable.Columns.CDMA_ECIO, cdmaEcio);
        cv.put(CellularTable.Columns.CDMA_LEVEL, cdmaLevel);
        cv.put(CellularTable.Columns.DBM, dbm);
        cv.put(CellularTable.Columns.EVDO_DBM, evdoDbm);
        cv.put(CellularTable.Columns.EVDO_ECIO, evdoEcio);
        cv.put(CellularTable.Columns.EVDO_LEVEL, evdoLevel);
        cv.put(CellularTable.Columns.EVDO_SNR, evdoSnr);
        cv.put(CellularTable.Columns.SIGNAL_LEVEL, signalLevel);
        cv.put(CellularTable.Columns.TIME_ADVANCE, timeAdvance);

        cv.put(CellularTable.Columns.CELL_ID, cellId);
        cv.put(CellularTable.Columns.COUNTRY_CODE, countryCode);
        cv.put(CellularTable.Columns.LATITUDE, latitude);
        cv.put(CellularTable.Columns.LONGITUDE, longitude);
        cv.put(CellularTable.Columns.LOCATION_CODE, locationCode);
        cv.put(CellularTable.Columns.NETWORK_CODE, networkCode);
        cv.put(CellularTable.Columns.PHYSICAL_CELL_ID, physicalCellId);
        cv.put(CellularTable.Columns.RF_CHANNEL, rfChannel);
        cv.put(CellularTable.Columns.SCRAMBLE_CODE, scrambleCode);
        cv.put(CellularTable.Columns.STATION_ID, stationId);
        cv.put(CellularTable.Columns.SYSTEM_CODE, systemCode);
        cv.put(CellularTable.Columns.TRACK_CODE, trackCode);

        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(CellularTable.Columns._ID));

        setActive(cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.ACTIVE_FLAG)));
        note = cursor.getString(cursor.getColumnIndex(CellularTable.Columns.NOTE));

        geoLocId = cursor.getLong(cursor.getColumnIndex(CellularTable.Columns.GEOLOC_ID));
        cellType = cursor.getString(cursor.getColumnIndex(CellularTable.Columns.CELL_TYPE));

        asuLevel = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.ASU_LEVEL));
        cdmaDbm = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.CDMA_DBM));
        cdmaEcio = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.CDMA_ECIO));
        cdmaLevel = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.CDMA_LEVEL));
        dbm = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.DBM));
        evdoDbm = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.EVDO_DBM));
        evdoEcio = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.EVDO_ECIO));
        evdoLevel = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.EVDO_LEVEL));
        evdoSnr = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.EVDO_SNR));
        signalLevel = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.SIGNAL_LEVEL));
        timeAdvance = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.TIME_ADVANCE));

        cellId = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.CELL_ID));
        countryCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.COUNTRY_CODE));
        latitude = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.LATITUDE));
        longitude = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.LONGITUDE));
        locationCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.LOCATION_CODE));
        networkCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.NETWORK_CODE));
        physicalCellId = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.PHYSICAL_CELL_ID));
        rfChannel = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.RF_CHANNEL));
        scrambleCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.SCRAMBLE_CODE));
        stationId = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.STATION_ID));
        systemCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.SYSTEM_CODE));
        trackCode = cursor.getInt(cursor.getColumnIndex(CellularTable.Columns.TRACK_CODE));
    }

    @Override
    public String getTableName() {
        return GeoLocTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return GeoLocTable.CONTENT_URI;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long arg) {
        id = arg;
    }

    public boolean isActive() {
        return activeFlag;
    }
    public void setActive(boolean arg) {
        activeFlag = arg;
    }
    public void setActive(int arg) {
        if (arg == Constant.SQL_TRUE) {
            activeFlag = true;
        } else {
            activeFlag = false;
        }
    }

    public String getNote() {
        return note;
    }
    public void setNote(String arg) {
        note = arg;
    }

    public Long getGeoLocId() {
        return geoLocId;
    }
    public void setGeoLocId(Long arg) {
        geoLocId = arg;
    }

    public String getCellType() {
        return cellType;
    }
    public void setCellType(String arg) {
        cellType = arg;
    }

    public Integer getAsuLevel() { return asuLevel; }
    public void setAsuLevel(int arg) { asuLevel = arg; }

    public Integer getDbm() { return dbm; }
    public void setDbm(int arg) { dbm = arg; }

    public Integer getSignalLevel() { return signalLevel; }
    public void setSignalLevel(int arg) { signalLevel = arg; }

    public Integer getTimeAdvance() { return timeAdvance; }
    public void setTimeAdvance(int arg) { timeAdvance = arg; }

    public Integer getCellId() { return cellId; }
    public void setCellId(int arg) { cellId = arg; }

    public Integer getCountryCode() { return countryCode; }
    public void setCountryCode(int arg) { countryCode = arg; }

    public Integer getLocationCode() { return locationCode; }
    public void setLocationCode(int arg) { locationCode = arg; }

    public Integer getNetworkCode() { return networkCode; }
    public void setNetworkCode(int arg) { networkCode = arg; }

    public Integer getPhysicalCellId() { return physicalCellId; }
    public void setPhysicalCellId(int arg) { physicalCellId = arg; }

    public Integer getRfChannel() { return rfChannel; }
    public void setRfChannel(int arg) { rfChannel = arg; }

    public Integer getScrambleCode() { return scrambleCode; }
    public void setScrambleCode(int arg) { scrambleCode = arg; }

    public Integer getStationId() { return stationId; }
    public void setStationId(int arg) { stationId = arg; }

    public Integer getTrackCode() { return trackCode; }
    public void setTrackCode(int arg) { trackCode = arg; }

    public void fromCellInfo(GeoLocModel geoLocModel, CellInfo cellInfo) {
        geoLocId = geoLocModel.getId();

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
