package de.telekom.smartcredentials.eid.messages;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Auxiliary {

    @SerializedName("ageVerificationDate")
    @Expose
    private String mAgeVerificationDate;
    @SerializedName("requiredAge")
    @Expose
    private String mRequiredAge;
    @SerializedName("validityDate")
    @Expose
    private String mValidityDate;
    @SerializedName("communityId")
    @Expose
    private String mCommunityId;

    public Auxiliary() {

    }

    public String getAgeVerificationDate() {
        return mAgeVerificationDate;
    }

    public void setAgeVerificationDate(String ageVerificationDate) {
        this.mAgeVerificationDate = ageVerificationDate;
    }

    public String getRequiredAge() {
        return mRequiredAge;
    }

    public void setRequiredAge(String requiredAge) {
        this.mRequiredAge = requiredAge;
    }

    public String getValidityDate() {
        return mValidityDate;
    }

    public void setValidityDate(String validityDate) {
        this.mValidityDate = validityDate;
    }

    public String getCommunityId() {
        return mCommunityId;
    }

    public void setCommunityId(String communityId) {
        this.mCommunityId = communityId;
    }

    @NonNull
    @Override
    public String toString() {
        return "Auxiliary{" +
                "mAgeVerificationDate='" + mAgeVerificationDate + '\'' +
                ", mRequiredAge='" + mRequiredAge + '\'' +
                ", mValidityDate='" + mValidityDate + '\'' +
                ", mCommunityId='" + mCommunityId + '\'' +
                '}';
    }
}
