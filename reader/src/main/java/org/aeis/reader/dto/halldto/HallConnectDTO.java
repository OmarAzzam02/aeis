package org.aeis.reader.dto.halldto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class HallConnectDTO {


    @JsonProperty("hall_name")
    private  String hallName;

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public HallConnectDTO(String hallName) {
        this.hallName = hallName;
    }
}
