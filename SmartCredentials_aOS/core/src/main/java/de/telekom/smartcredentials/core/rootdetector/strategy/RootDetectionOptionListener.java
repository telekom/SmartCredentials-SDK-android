package de.telekom.smartcredentials.core.rootdetector.strategy;

import de.telekom.smartcredentials.core.rootdetector.RootDetectionOption;

public interface RootDetectionOptionListener {

    void onOptionChecked(RootDetectionOption option, boolean isPresent);
}
