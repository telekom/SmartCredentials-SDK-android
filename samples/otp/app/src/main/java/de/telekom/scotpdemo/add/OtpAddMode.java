package de.telekom.scotpdemo.add;

import de.telekom.scotpdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 9/2/2020
 */
public enum OtpAddMode {
    PROVIDED_KEY("Enter a provided key", R.drawable.ic_add_otp_manual),
    QR_CODE("Scan your OTP QR Code", R.drawable.ic_add_otp_qr);

    private final String description;
    private final int iconId;

    OtpAddMode(String description, int iconId) {
        this.description = description;
        this.iconId = iconId;
    }

    public String getDescription() {
        return description;
    }

    public int getIconId() {
        return iconId;
    }
}
