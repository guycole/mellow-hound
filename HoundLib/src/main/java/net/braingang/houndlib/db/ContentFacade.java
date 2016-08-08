package net.braingang.houndlib.db;

import android.content.ContentUris;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.telephony.CellInfo;
import android.util.Log;

/**
 *
 */
public class ContentFacade {
    public static final String LOG_TAG = ContentFacade.class.getName();

    public void insertGeoLoc(GeoLocModel model, Context context) {
        Uri uri = context.getContentResolver().insert(GeoLocTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    public GeoLocModel insertLocation(Long observationId, Location location, Context context) {
        GeoLocModel model = new GeoLocModel();
        model.setDefault();
        model.setObservationId(observationId);
        model.fromLocation(location);
        insertGeoLoc(model, context);
        Log.i(LOG_TAG, "insert geoloc:" + model.getId());
        return model;
    }

    public void insertObservation(ObservationModel model, Context context) {
        Uri uri = context.getContentResolver().insert(ObservationTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    public ObservationModel insertObservation(String networkName, String networkOperator, Context context) {
        ObservationModel model = new ObservationModel();
        model.setDefault();
        model.setNetworkName(networkName);
        model.setNetworOperator(networkOperator);
        insertObservation(model, context);
        Log.i(LOG_TAG, "insert observation:" + model.getId());
        return model;
    }

    ///////////////

    public void insertCellular(CellularModel model, Context context) {
        Uri uri = context.getContentResolver().insert(CellularTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    public CellularModel insertCellular(GeoLocModel geoLocModel, CellInfo cellInfo, Context context) {
        CellularModel model = new CellularModel();
        model.setDefault();
        model.fromCellInfo(geoLocModel, cellInfo);
        insertCellular(model, context);
        Log.i(LOG_TAG, "insert cellular:" + model.getId());
        return model;
    }
}