package app.entities;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void verifyPassword() {
        String realPassword = "DerErEtYndightLand";
        User actualUser = new User("Frederik", realPassword,"Fred@dk.dk");
        assertTrue(BCrypt.checkpw(realPassword,actualUser.getPassword()));
    }

    @Test
    void addRole() {
        Set<Roles> rolesBeforeAdd = new HashSet<>(Set.of(Roles.USER));
        Set<Roles> rolesAfterAdd = new HashSet<>(Set.of(Roles.USER,Roles.ADMIN));

        User actualUser = new User("Frederik", "realPassword","Fred@dk.dk");
        assertEquals(rolesBeforeAdd,actualUser.getRoles());
        actualUser.addRole(Roles.ADMIN);
        assertEquals(rolesAfterAdd,actualUser.getRoles());
    }
}