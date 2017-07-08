package com.tardis.development.adviser.domain.transaction;

final class Operators {

    private Operators() {
    }


    static TransactionDTO transformToDTO(Transaction transaction) {
        return TransactionDTO.of(
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategoryCode()
        );
    }

    static TransactionDTO transformToDTO(CappedTransaction transaction) {
        return TransactionDTO.of(
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategoryCode()
        );
    }

    static Transaction transformToDomain(String user, TransactionDTO transaction) {
        return new Transaction(
                user,
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategoryCode()
        );
    }

    static Transaction transformToDomain(ExcelTransactionDTO transaction) {
        return new Transaction(
                transaction.getUser(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategoryCode()
        );
    }


    static CappedTransaction transformToCapped(Transaction transaction) {
        return new CappedTransaction(
                transaction.getUser(),
                transaction.getDate(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCategoryCode()
        );
    }

}
