package org.aeis.reader.dto.otpdto;


import com.fasterxml.jackson.annotation.JsonProperty;

public class OtpRequest {

    @JsonProperty("email")
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
