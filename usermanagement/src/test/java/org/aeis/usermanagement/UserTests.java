package org.aeis.usermanagement;

import org.aeis.usermanagement.entity.Role;
import org.aeis.usermanagement.entity.User;
import org.aeis.usermanagement.service.jwt.AeisUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

// Corrected import for JUnit 5's assertThrows
import static org.junit.jupiter.api.Assertions.*;

class UserTests {

    private User sampleUser;

    @BeforeEach
    void setUp() {
        // Initialize a common User object for tests
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setFirstName("Test");
        sampleUser.setLastName("User");
        sampleUser.setEmail("test.user@example.com");
        sampleUser.setPassword("password123");
        sampleUser.setRole(Role.STUDENT);
    }

    // --- User Entity Tests ---

    @Test
    void testUserEntity_DefaultConstructorAndSetters() {
        User user = new User();
        user.setId(10L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("securePassword");
        user.setRole(Role.ADMIN);

        assertEquals(10L, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("securePassword", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void testUserEntity_AllArgsConstructor() {
        // Assuming User has an AllArgsConstructor from Lombok
        User user = new User(20L, "Jane", "Smith", "jane.smith@example.com", "anotherPassword", Role.INSTRUCTOR);

        assertEquals(20L, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("anotherPassword", user.getPassword());
        assertEquals(Role.INSTRUCTOR, user.getRole());
    }

    // --- AeisUserDetails Tests ---

    @Test
    void testAeisUserDetails_ConstructorAndCoreProperties() {
        AeisUserDetails userDetails = new AeisUserDetails(sampleUser);

        assertNotNull(userDetails);
        assertEquals(sampleUser.getEmail(), userDetails.getUsername(), "Username should be user's email");
        assertEquals(sampleUser.getPassword(), userDetails.getPassword(), "Password should match user's password");
        assertSame(sampleUser, userDetails.getUser(), "Should return the same user instance");
    }

    @Test
    void testAeisUserDetails_AuthoritiesCorrectlyAssigned() {
        // Test for STUDENT role
        sampleUser.setRole(Role.STUDENT); // Ensure sampleUser has STUDENT role for this part
        AeisUserDetails studentDetails = new AeisUserDetails(sampleUser);
        Collection<? extends GrantedAuthority> studentAuthorities = studentDetails.getAuthorities();
        assertNotNull(studentAuthorities);
        assertEquals(1, studentAuthorities.size(), "Should have one authority for STUDENT");
        assertTrue(studentAuthorities.contains(new SimpleGrantedAuthority("ROLE_STUDENT")), "Authority should be ROLE_STUDENT");

        // Test for INSTRUCTOR role
        User instructorUser = new User(2L, "Instructor", "Test", "instructor@example.com", "pass", Role.INSTRUCTOR);
        AeisUserDetails instructorDetails = new AeisUserDetails(instructorUser);
        Collection<? extends GrantedAuthority> instructorAuthorities = instructorDetails.getAuthorities();
        assertNotNull(instructorAuthorities);
        assertEquals(1, instructorAuthorities.size(), "Should have one authority for INSTRUCTOR");
        assertTrue(instructorAuthorities.contains(new SimpleGrantedAuthority("ROLE_INSTRUCTOR")), "Authority should be ROLE_INSTRUCTOR");

        // Test for ADMIN role
        User adminUser = new User(3L, "Admin", "Test", "admin@example.com", "pass", Role.ADMIN);
        AeisUserDetails adminDetails = new AeisUserDetails(adminUser);
        Collection<? extends GrantedAuthority> adminAuthorities = adminDetails.getAuthorities();
        assertNotNull(adminAuthorities);
        assertEquals(1, adminAuthorities.size(), "Should have one authority for ADMIN");
        assertTrue(adminAuthorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")), "Authority should be ROLE_ADMIN");
    }

    @Test
    void testAeisUserDetails_Authorities_NullRoleInUser() {
        User userWithNullRole = new User(4L, "Null", "RoleUser", "null.role@example.com", "password", null);
        // Expect NullPointerException when AeisUserDetails tries to access user.getRole().name()
        // Using JUnit 5's assertThrows
        assertThrows(NullPointerException.class, () -> {
            new AeisUserDetails(userWithNullRole);
        }, "Constructor should throw NullPointerException if user's role is null");
    }


    @Test
    void testAeisUserDetails_DefaultBooleanFlags() {
        AeisUserDetails userDetails = new AeisUserDetails(sampleUser);

        assertTrue(userDetails.isAccountNonExpired(), "Account should be non-expired by default");
        assertTrue(userDetails.isAccountNonLocked(), "Account should be non-locked by default");
        assertTrue(userDetails.isCredentialsNonExpired(), "Credentials should be non-expired by default");
        assertTrue(userDetails.isEnabled(), "Account should be enabled by default");
    }
}
