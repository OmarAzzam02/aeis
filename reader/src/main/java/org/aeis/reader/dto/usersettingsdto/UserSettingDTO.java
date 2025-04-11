package org.aeis.reader.dto.usersettingsdto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserSettingDTO {


    @JsonProperty("tts")
    private boolean ttsRequestEnabled;
    @JsonProperty("stt")
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
