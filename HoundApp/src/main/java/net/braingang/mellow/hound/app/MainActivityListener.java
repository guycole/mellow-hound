package net.braingang.mellow.hound.app;

import android.os.Bundle;

/**
 *
 */
public interface MainActivityListener {

    void fragmentSelect(MainActivityFragmentEnum selected, Bundle args);

    void navDrawerOpen(boolean arg);
}
