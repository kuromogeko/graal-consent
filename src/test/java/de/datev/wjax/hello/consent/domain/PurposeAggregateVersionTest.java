package de.datev.wjax.hello.consent.domain;

import de.datev.wjax.hello.consent.domain.purpose.PurposeVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PurposeAggregateVersionTest {
    @ParameterizedTest
    @CsvSource(value = {"1,1,0", "2,1,1", "1,2,-1"})
    void purposeVersionsAreComparable(Integer versionA, Integer versionB, int expected) {
        var a = new PurposeVersion(versionA);
        var b = new PurposeVersion(versionB);
        assertEquals(expected, a.compareTo(b));
    }
}