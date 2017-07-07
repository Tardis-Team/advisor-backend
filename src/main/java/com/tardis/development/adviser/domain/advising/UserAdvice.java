package com.tardis.development.adviser.domain.advising;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@AllArgsConstructor(staticName = "of")
public class UserAdvice {
    private final BigDecimal averageReminder;

    private final BigDecimal lastReminder;

    private final BigDecimal plannedReminder;

    private final BigDecimal saveToSpendAmount;
}
