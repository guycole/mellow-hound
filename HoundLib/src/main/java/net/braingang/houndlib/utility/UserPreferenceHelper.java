package net.braingang.houndlib.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;
import java.util.UUID;

/**
 * SharedPreference wrapper.
 */
public class UserPreferenceHelper {
    public static final String BLE_COLLECTION = "bleCollection";
    public static final String CELLULAR_COLLECTION = "cellularCollection";
    public static final String WIFI_COLLECTION = "wifiCollection";

    public static final String INSTALLATION_UUID = "installationUuid";

    /**
     * @param context
     */
    public void writeDefaults(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putString(INSTALLATION_UUID, UUID.randomUUID().toString());

        editor.commit();
    }

    /**
     * Could only be true on a fresh install
     *
     * @param context
     * @return true if user preferences are empty
     */
    public boolean isEmptyPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, ?> map = sp.getAll();
        return map.isEmpty();
    }

    /**
     * determine if bluetooth low energy (BLE) collection is enabled
     *
     * @param context
     * @return true, bluetooth low energy (BLE) collection is enabled
     */
    public boolean isBleCollection(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(BLE_COLLECTION, false);
    }

    /**
     * enable/disable bluetooth low energy (BLE) collection
     *
     * @param context
     * @param flag true, bluetooth low energy (BLE) collection is enabled
     */
    public void setBleCollection(Context context, boolean flag) {
        setBoolean(context, BLE_COLLECTION, flag);
    }

    /**
     * determine if cellular collection is enabled
     *
     * @param context
     * @return true, cellular collection is enabled
     */
    public boolean isCellularCollection(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(CELLULAR_COLLECTION, false);
    }

    /**
     * enable/disable cellular collection
     *
     * @param context
     * @param flag true, cellular collection is enabled
     */
    public void setCellularCollection(Context context, boolean flag) {
        setBoolean(context, CELLULAR_COLLECTION, flag);
    }

    /**
     * determine if wifi collection is enabled
     *
     * @param context
     * @return true, wifi collection is enabled
     */
    public boolean isWiFiCollection(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean(WIFI_COLLECTION, false);
    }

    /**
     * enable/disable wifi collection
     *
     * @param context
     * @param flag true, wifi collection is enabled
     */
    public void setWiFiCollection(Context context, boolean flag) {
        setBoolean(context, WIFI_COLLECTION, flag);
    }

    /**
     * return installation identifier
     * @param context
     * @return installation identifier
     */
    public UUID getInstallationUuid(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        return UUID.fromString(sp.getString(INSTALLATION_UUID, "8D1C6FA5-0FE6-4E81-BCE0-4AABCD74E267"));
    }

    /**
     * define installation uuid
     * @param context
     * @param uuid installation uuid
     */
    public void setInstallationUuid(Context context, UUID uuid) {
        setString(context, INSTALLATION_UUID, uuid.toString());
    }

    /**
     * Update user preferences file
     *
     * @param context
     * @param key
     * @param flag
     */
    private void setBoolean(Context context, String key, boolean flag) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, flag);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param flag
     */
    private void setLong(Context context, String key, long arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, arg);
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @param arg
     */
    private void setString(Context context, String key, String arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, arg);
        editor.commit();
    }

    /**
     *
     * @param context
     * @param key
     * @param arg
     */
    private void setFloat(Context context, String key, float arg) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, arg);
        editor.commit();
    }
}
