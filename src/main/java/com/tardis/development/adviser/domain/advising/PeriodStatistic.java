package com.tardis.development.adviser.domain.advising;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;


@RequiredArgsConstructor(staticName = "of")
class PeriodStatistic {

    private final int monthCount;
    private final @NonNull BigDecimal totalReminder;
    private final @NonNull BigDecimal totalSpending;


    public BigDecimal getTotalAverageReminder() {
        return totalReminder.divide(BigDecimal.valueOf(monthCount), BigDecimal.ROUND_CEILING);
    }

    public BigDecimal getTotalAverageSpending() {
        return totalSpending.divide(BigDecimal.valueOf(monthCount), BigDecimal.ROUND_CEILING);
    }
}
