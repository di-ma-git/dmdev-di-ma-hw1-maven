package com.dima.integration.repository;

import com.dima.dto.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class ActiveSubstanceRepositoryIT extends TestBase {

    private final ActiveSubstanceRepository activeSubstanceRepository;
    @Autowired
    public ActiveSubstanceRepositoryIT(ActiveSubstanceRepository activeSubstanceRepository) {
        this.activeSubstanceRepository = activeSubstanceRepository;
    }

    @Test
    void saveUserSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        entityManager.flush();
        entityManager.detach(activeSubstance);

        assertThat(activeSubstance.getId()).isNotNull();
    }

    @Test
    void findActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        entityManager.flush();
        entityManager.detach(activeSubstance);

        var actualResult = activeSubstanceRepository.findById(activeSubstance.getId()).get();

        assertThat(actualResult).isEqualTo(activeSubstance);
        assertThat(actualResult.getId()).isEqualTo(activeSubstance.getId());
    }


    @Test
    void updateActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        entityManager.flush();
        entityManager.detach(activeSubstance);
        activeSubstance.setDescription("Another description");
        activeSubstanceRepository.update(activeSubstance);
        entityManager.flush();
        entityManager.detach(activeSubstance);

        var actualResult = activeSubstanceRepository.findById(activeSubstance.getId()).get();

        assertThat(actualResult.getDescription()).isEqualTo("Another description");
    }

    @Test
    void deleteActiveSubstanceSuccessful() {
        var activeSubstance = TestSimpleData.getSimpleTestActiveSubstance();

        activeSubstanceRepository.save(activeSubstance);
        entityManager.flush();
        entityManager.detach(activeSubstance);
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
