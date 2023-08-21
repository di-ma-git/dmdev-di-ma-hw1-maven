package com.dima.integration.repository;

import com.dima.dto.filters.ActiveSubstanceFilter;
import com.dima.entity.ActiveSubstance;
import com.dima.repository.ActiveSubstanceRepository;
import com.dima.testdata.TestSimpleData;
import com.dima.util.TestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class ActiveSubstanceRepositoryIT extends TestBase {

    private final ActiveSubstanceRepository activeSubstanceRepository;

    @Test
    void findActiveSubstancesByFilterWithProductAndManufacturer() {
        var filter = ActiveSubstanceFilter.builder()
                .productName("Aspirin")
                .productManufacturerName("Pfizer")
                .build();

        var result = activeSubstanceRepository.findAllByFilter(filter);

        assertThat(result).hasSize(2);

        var actualResult = result.stream().map(ActiveSubstance::getName).toList();

        assertThat(actualResult).containsExactlyInAnyOrder("Acetylsalicylic acid", "Ascorbic acid");

    }

}
