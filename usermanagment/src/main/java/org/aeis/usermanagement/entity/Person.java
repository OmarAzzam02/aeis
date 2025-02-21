package org.aeis.usermanagement.entity;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Person {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
}
