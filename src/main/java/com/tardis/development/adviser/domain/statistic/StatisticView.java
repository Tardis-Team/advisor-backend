package com.tardis.development.adviser.domain.statistic;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor(staticName = "of")
public class StatisticView {
    private final BigDecimal averageReminder;

    private final BigDecimal averageSpending;

    private final BigDecimal lastReminder;

    private final BigDecimal rawLeftover;

    private final BigDecimal saveToSpendAmount;
}
