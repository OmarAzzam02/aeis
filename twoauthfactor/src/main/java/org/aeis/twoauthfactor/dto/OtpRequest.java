package org.aeis.twoauthfactor.dto;


import com.fasterxml.jackson.annotation.JsonProperty;


public class OtpRequest {

     @JsonProperty("email")
     private String email;

     @JsonProperty("first_name")
     private String firstName;


     public String getFirstName() {
          return firstName;
     }


     public String getEmail() {
          return email;
     }
}
