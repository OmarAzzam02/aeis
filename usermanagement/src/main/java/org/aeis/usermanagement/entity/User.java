    package org.aeis.usermanagement.entity;


    import jakarta.persistence.*;
    import lombok.*;

    import java.io.Serializable;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @Table(name = "USERS")
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    @DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING) // Custom column name

    public class User implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @SequenceGenerator(name = "users_user_id_seq", sequenceName = "users_user_id_seq")
        @Column(name = "USER_ID")
        private long id;
        @Column(name = "first_name" , nullable = false)
        private String firstName;
        @Column(name = "last_name" , nullable = false)
        private String lastName;
        @Column( name = "email", unique = true )
        private String email;
        @Column( name = "password" , nullable = false )
        private String password;

        @Enumerated(EnumType.STRING)
        @Column(insertable=false, updatable=false)
        private Role role;
    }
