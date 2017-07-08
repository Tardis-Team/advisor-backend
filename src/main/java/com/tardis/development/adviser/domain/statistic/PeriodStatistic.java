package com.tardis.development.adviser.domain.statistic;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class PeriodStatistic {

    private final int monthCount;
    private final @NonNull BigDecimal[] totalReminders;
    private final @NonNull BigDecimal[] totalSpendings;


    public BigDecimal getAverageReminder() {
        return monthCount > 0 ? average(totalReminders, monthCount) : BigDecimal.ZERO;
    }

    public BigDecimal getAverageSpending() {
       return monthCount > 0 ? average(totalSpendings, monthCount) : BigDecimal.ZERO;
    }

    public BigDecimal getReminderDeviation() {
        return monthCount > 0 ? deviation(totalReminders, average(totalReminders, monthCount), monthCount) : BigDecimal.ZERO;
    }

    public BigDecimal getSpendingDeviation() {
        return monthCount > 0 ? deviation(totalSpendings, average(totalSpendings, monthCount), monthCount) : BigDecimal.ZERO;
    }

    private static BigDecimal average(BigDecimal[] input, int count) {
        return Arrays.stream(input)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(count), RoundingMode.CEILING);
    }

    private static BigDecimal deviation(BigDecimal[] input, BigDecimal average, int count) {
        return bigSqrt(Arrays.stream(input)
                .reduce(BigDecimal.ZERO, (l, r) -> l.add(r.subtract(average).pow(2)))
                .divide(BigDecimal.valueOf(count - 1), RoundingMode.CEILING));
    }


    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    /**
     * Private utility method used to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */

    private static BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1) {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    /**
     * Uses Newton Raphson to compute the square root of a BigDecimal.
     *
     * @author Luciano Culacciatti
     * @url http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
     */
    private static BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }

    static class Builder {
        private final List<BigDecimal> totalReminders = new ArrayList<>();
        private final List<BigDecimal> totalSpendings = new ArrayList<>();

        Builder add(@NonNull BigDecimal totalReminder, @NonNull BigDecimal totalSpending) {
            totalReminders.add(totalReminder);
            totalSpendings.add(totalSpending);

            return this;
        }

        Builder add(@NonNull Builder builder) {
            totalReminders.addAll(builder.totalReminders);
            totalSpendings.addAll(builder.totalSpendings);

            return this;
        }

        PeriodStatistic build() {
            int size = totalReminders.size();

            return new PeriodStatistic(
                    size,
                    totalReminders.toArray(new BigDecimal[size]),
                    totalSpendings.toArray(new BigDecimal[size])
            );
        }
    }
}
