package com.tardis.development.adviser.domain.statistic;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor(staticName = "of")
public class StatisticView {
    private final BigDecimal averageReminder;

    private final BigDecimal lastReminder;

    private final BigDecimal plannedReminder;

    private final BigDecimal saveToSpendAmount;
}
