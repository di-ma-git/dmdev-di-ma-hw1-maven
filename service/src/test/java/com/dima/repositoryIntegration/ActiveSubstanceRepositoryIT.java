package com.dima.repositoryIntegration;

import com.dima.dto.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.testData.TestSimpleData;
import com.dima.util.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActiveSubstanceRepositoryIT extends TestBase {

    private final ActiveSubstanceRepository activeSubstanceRepository = context.getBean(ActiveSubstanceRepository.class);

    @Test
    void saveUserSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        assertThat(activeSubstance.getId()).isNotNull();
    }

    @Test
    void findActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        var actualResult = activeSubstanceRepository.findById(activeSubstance.getId()).get();

        assertThat(actualResult).isEqualTo(activeSubstance);
        assertThat(actualResult.getId()).isEqualTo(activeSubstance.getId());
    }


    @Test
    void updateActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);
        activeSubstance.setDescription("Another description");
        activeSubstanceRepository.update(activeSubstance);
        session.flush();
        session.evict(activeSubstance);

        var actualResult = activeSubstanceRepository.findById(activeSubstance.getId()).get();

        assertThat(actualResult.getDescription()).isEqualTo("Another description");
    }

    @Test
    void deleteActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        session.flush();
        session.evict(activeSubstance);
        activeSubstanceRepository.delete(activeSubstance);

        var actualResult = activeSubstanceRepository.findById(activeSubstance.getId());

        assertThat(actualResult).isEmpty();
    }

    @Test
    void findActiveSubstancesByProductAndManufacturer() {
        var filter = ActiveSubstanceFilter.builder()
                .productName("Aspirin")
                .productManufacturerName("Pfizer")
                .build();

        var result = activeSubstanceRepository.findByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream().map(ActiveSubstance::getName).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Acetylsalicylic acid", "Ascorbic acid");

    }
}
