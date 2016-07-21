package net.braingang.houndlib.db;

import android.content.ContentUris;
import android.content.Context;
import android.location.Location;
import android.net.Uri;

/**
 *
 */
public class ContentFacade {

    public void insertGeoLoc(GeoLocModel model, Context context) {
        Uri uri = context.getContentResolver().insert(GeoLocTable.CONTENT_URI, model.toContentValues());
        model.setId(ContentUris.parseId(uri));
    }

    public GeoLocModel insertLocation(Location location, Context context) {
        GeoLocModel model = new GeoLocModel();
        model.setDefault();
        model.fromLocation(location);
        insertGeoLoc(model, context);
        return model;
    }
}
