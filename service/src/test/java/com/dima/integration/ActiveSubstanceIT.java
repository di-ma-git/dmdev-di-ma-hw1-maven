package com.dima.integration;

import com.dima.entity.ActiveSubstance;
import org.junit.jupiter.api.Test;
import com.dima.util.TestBase;

import static org.assertj.core.api.Assertions.assertThat;

public class ActiveSubstanceIT extends TestBase {

    @Test
    void saveUserSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();

        session.beginTransaction();
        session.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        assertThat(activeSubstance.getId()).isNotNull();
    }

    @Test
    void getActiveSubstanceSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();

        session.beginTransaction();
        session.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        var actualResult = session.get(ActiveSubstance.class, activeSubstance.getId());

        assertThat(actualResult).isEqualTo(activeSubstance);
        assertThat(actualResult.getId()).isEqualTo(activeSubstance.getId());
    }

    @Test
    void updateActiveSubstanceSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();

        session.beginTransaction();
        session.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);
        activeSubstance.setDescription("Another description");
        session.update(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        var actualResult = session.get(ActiveSubstance.class, activeSubstance.getId());

        assertThat(actualResult.getDescription()).isEqualTo("Another description");
    }

    @Test
    void deleteActiveSubstanceSuccessful() {
        var activeSubstance = testSimpleData.getSimpleTestActiveSubstance();

        session.beginTransaction();
        session.save(activeSubstance);
        session.delete(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        var actualResult = session.get(ActiveSubstance.class, activeSubstance.getId());

        assertThat(actualResult).isNull();
    }
}
