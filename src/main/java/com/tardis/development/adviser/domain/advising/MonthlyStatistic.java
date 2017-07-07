package com.tardis.development.adviser.domain.advising;

import com.tardis.development.adviser.domain.transaction.TransactionDTO;
import com.tardis.development.adviser.domain.transaction.Type;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor(staticName = "of")
class MonthlyStatistic {

    private final @NonNull LocalDate date;
    private final List<TransactionDTO> transactions = new ArrayList<>();

    public BigDecimal remainder() {
        return transactions
                .stream()
                .reduce(BigDecimal.ZERO,
                        (i, n) -> n.getType() == Type.CREDIT
                                ? i.add(n.getAmount())
                                : i.subtract(n.getAmount()),
                        BigDecimal::add);
    }

    public BigDecimal spending() {
        return transactions
                .stream()
                .filter(t-> t.getType() == Type.DEBIT)
                .map(TransactionDTO::getAmount)
                .reduce(BigDecimal.ZERO,
                        BigDecimal::add,
                        BigDecimal::add);
    }

    public MonthlyStatistic put(TransactionDTO transactionDTO) {
        transactions.add(transactionDTO);

        return this;
    }

    public boolean isInCurrentPeriod(TransactionDTO transactionDTO) {
        LocalDate test = transactionDTO.getDate();

        return test.getMonth() == date.getMonth() && test.getYear() == date.getYear();
    }

    public static MonthlyStatistic of(TransactionDTO dto) {
        return new MonthlyStatistic(dto.getDate()).put(dto);
    }

    @Override
    public int hashCode() {
        return date.getYear() + date.getMonth().hashCode();
    }
}
