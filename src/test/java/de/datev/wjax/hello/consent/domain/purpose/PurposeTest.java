package de.datev.wjax.hello.consent.domain.purpose;

import de.datev.wjax.hello.consent.domain.actors.user.UserCharacteristic;
import de.datev.wjax.hello.consent.domain.actors.user.UserType;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PurposeTest {
    @Test
    void noUpdateIfArgumentHasEvenVersion() {
        var purpose = new Purpose(UUID.randomUUID(), new PurposeVersion(1), "", new Viability(SubjectType.USER, new UserCharacteristic(UserType.DEFAULT)));
        var result = purpose.updateVersion(new PurposeVersion(1), "a");
        assertTrue(new PurposeVersion(1).equals(purpose.getPurposeVersion()));
        assertEquals("", purpose.getText());
        assertFalse(result);
    }

    @Test
    void noUpdateIfArgumentHasSmallerVersion() {
        var purpose = new Purpose(UUID.randomUUID(), new PurposeVersion(1), "", new Viability(SubjectType.USER, new UserCharacteristic(UserType.DEFAULT)));
        var result = purpose.updateVersion(new PurposeVersion(0), "a");
        assertTrue(new PurposeVersion(1).equals(purpose.getPurposeVersion()));
        assertEquals("", purpose.getText());
        assertFalse(result);

    }

    @Test
    void updateIfArgumentHasGreaterVersion() {
        var purpose = new Purpose(UUID.randomUUID(), new PurposeVersion(1), "", new Viability(SubjectType.USER, new UserCharacteristic(UserType.DEFAULT)));
        var result = purpose.updateVersion(new PurposeVersion(2), "a");
        assertTrue(new PurposeVersion(2).equals(purpose.getPurposeVersion()));
        assertEquals("a", purpose.getText());
        assertTrue(result);
    }

}