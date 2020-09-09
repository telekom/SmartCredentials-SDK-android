package de.telekom.scotpdemo.view;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import de.telekom.scotpdemo.R;
import de.telekom.smartcredentials.core.itemdatamodel.ItemEnvelope;
import de.telekom.smartcredentials.core.model.otp.OTPTokenKeyExtras;
import de.telekom.smartcredentials.core.model.otp.OTPType;
import de.telekom.smartcredentials.core.model.token.TokenResponse;

/**
 * Created by gabriel.blaj@endava.com at 8/31/2020
 */
public class OtpViewHolder extends RecyclerView.ViewHolder {

    public static final int TOTAL_DEGREES = 360;
    private static final int SECONDS_IN_MILLIS = 1000;
    private static final int MILLIS_BETWEEN_TWO_STATES = 250;

    private double degreesPerMillisecond;

    private final Handler handler;
    private final Handler uiHandler;
    private long remainingMillis;
    private OTPType otpType;

    private final TimerView timerView;
    private final ImageView refreshView;
    private final TextView otpCode;
    private final TextView account;
    private final ImageView deleteButton;

    public OtpViewHolder(View itemView) {
        super(itemView);
        timerView = itemView.findViewById(R.id.timer_view);
        refreshView = itemView.findViewById(R.id.refresh_view);
        otpCode = itemView.findViewById(R.id.otp_code);
        account = itemView.findViewById(R.id.otp_account);
        deleteButton = itemView.findViewById(R.id.delete_icon);
        handler = new Handler();
        uiHandler = new Handler(Looper.getMainLooper());
    }

    public void bind(ItemEnvelope itemEnvelope, OTPType otpType, OtpAdapter.OtpClickListener listener) {
        this.otpType = otpType;
        try {
            account.setText(itemEnvelope.getIdentifier().getString(OTPTokenKeyExtras.USER_LABEL));
        } catch (JSONException e) {
            account.setText(R.string.error_retrieving_account_name);
            e.printStackTrace();
        }
        refreshView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOtpAccountClick(itemEnvelope);
            }
        });
        deleteButton.setOnClickListener(v -> {
            if(listener != null) {
                listener.onOptDeleteClick(itemEnvelope);
            }
        });
    }

    public void bind(ItemEnvelope itemEnvelope, OTPType otpType, OtpAdapter.OtpClickListener listener, TokenResponse tokenResponse) {
        bind(itemEnvelope, otpType, listener);
        bindCode(tokenResponse);
    }

    private void bindCode(TokenResponse tokenResponse) {
        if (otpType == OTPType.TOTP) {
            timerView.setVisibility(View.VISIBLE);
            refreshView.setVisibility(View.GONE);
            long currentTime = System.currentTimeMillis();
            if (tokenResponse.getValidFrom() < currentTime) {
                otpCode.setText(tokenResponse.getValue());
                setTimer(tokenResponse.getValidUntil(), tokenResponse.getValidityPeriod(), currentTime);
            } else {
                handler.postDelayed(new ResetOTPRunnable(tokenResponse), tokenResponse.getValidFrom() - currentTime);
            }
        } else if (otpType == OTPType.HOTP) {
            otpCode.setText(tokenResponse.getValue());
            timerView.setVisibility(View.GONE);
            refreshView.setVisibility(View.VISIBLE);
        }
    }

    private void setTimer(long validUntilTimestamp, long validityPeriod, long currentTimestamp) {
        handler.removeCallbacks(updateTimerRunnable);
        float seconds = (validUntilTimestamp - currentTimestamp) / (float) SECONDS_IN_MILLIS;

        remainingMillis = Math.round(seconds) * SECONDS_IN_MILLIS;
        degreesPerMillisecond = validityPeriod /(double) TOTAL_DEGREES;
        handler.post(updateTimerRunnable);
    }

    private void setTimer(long validityPeriod, String value) {
        otpCode.setText(value);

        remainingMillis = validityPeriod;
        degreesPerMillisecond = validityPeriod /(double) TOTAL_DEGREES;
        handler.removeCallbacks(updateTimerRunnable);
        handler.post(updateTimerRunnable);
    }

    private final Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (remainingMillis == 0) return;
            remainingMillis = remainingMillis - MILLIS_BETWEEN_TWO_STATES;
            int degrees = Math.min((int) (remainingMillis / degreesPerMillisecond), TOTAL_DEGREES);
            uiHandler.post(() -> timerView.updateTimer(degrees));
            handler.postDelayed(updateTimerRunnable, MILLIS_BETWEEN_TWO_STATES);
        }
    };

    private class ResetOTPRunnable implements Runnable {
        private final TokenResponse tokenResponse;

        ResetOTPRunnable(TokenResponse tokenResponse) {
            this.tokenResponse = tokenResponse;
        }

        @Override
        public void run() {
            setTimer(tokenResponse.getValidityPeriod(), tokenResponse.getValue());
        }
    }
}