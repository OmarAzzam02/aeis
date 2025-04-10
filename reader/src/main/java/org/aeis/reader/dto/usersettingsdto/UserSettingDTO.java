package org.aeis.reader.dto.usersettingsdto;

public class UserSettingDTO {

    private boolean ttsRequestEnabled;
    private boolean sttRequestEnabled;


    public boolean isTtsRequestEnabled() {
        return ttsRequestEnabled;
    }

    public void setTtsRequestEnabled(boolean ttsRequestEnabled) {
        this.ttsRequestEnabled = ttsRequestEnabled;
    }

    public boolean isSttRequestEnabled() {
        return sttRequestEnabled;
    }

    public void setSttRequestEnabled(boolean sttRequestEnabled) {
        this.sttRequestEnabled = sttRequestEnabled;
    }
}
