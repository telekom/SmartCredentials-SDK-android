package de.telekom.smartcredentials.eid.commands;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SimulatorFile {

    @SerializedName("fileId")
    @Expose
    private String mFileId;
    @SerializedName("shortFileId")
    @Expose
    private String mShortFileId;
    @SerializedName("content")
    @Expose
    private String mContent;

    public SimulatorFile(String fileId, String shortFileId, String content) {
        mFileId = fileId;
        mShortFileId = shortFileId;
        mContent = content;
    }

    public String getFileId() {
        return mFileId;
    }

    public String getShortFileId() {
        return mShortFileId;
    }

    public String getContent() {
        return mContent;
    }

    public void setFileId(String mFileId) {
        this.mFileId = mFileId;
    }

    public void setShortFileId(String mShortFileId) {
        this.mShortFileId = mShortFileId;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
}
