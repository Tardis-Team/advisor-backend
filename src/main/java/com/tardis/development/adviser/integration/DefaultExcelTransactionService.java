package com.tardis.development.adviser.integration;

import com.tardis.development.adviser.domain.transaction.ExcelTransactionDTO;
import com.tardis.development.adviser.domain.transaction.Type;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.ZoneId;

@Service
public class DefaultExcelTransactionService implements ExcelTransactionService {
    private static final String TRANSACTIONS_SUFFIX = "-transactions";
    private static final String SHEETS_NAME_SEPARATOR = "-";

    @Override
    @SneakyThrows
    public Flux<ExcelTransactionDTO> list(File file) {
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);

        return Flux.fromIterable(workbook)
                .filter(sheet -> sheet.getSheetName().contains(TRANSACTIONS_SUFFIX))
                .flatMap(sheet -> Flux
                        .range(2, sheet.getLastRowNum() + 1)
                        .map(sheet::getRow)
                        .map(row -> transformToTransaction(sheet.getSheetName().split(SHEETS_NAME_SEPARATOR)[0], row))
                );
    }

    private ExcelTransactionDTO transformToTransaction(String user, Row row) {
        double debitAmount = row.getCell(3).getNumericCellValue();
        double creditAmount = row.getCell(4).getNumericCellValue();
        boolean isCredit = debitAmount == 0d;
        BigDecimal amount = BigDecimal.valueOf(isCredit ? creditAmount : debitAmount);
        Type type = isCredit ? Type.CREDIT : Type.DEBIT;
        Cell cell = row.getCell(5);
        int categoryCode = cell.getCellTypeEnum() == CellType.STRING
                ? Integer.parseInt(cell.getStringCellValue())
                : (int) cell.getNumericCellValue();

        return ExcelTransactionDTO.of(
                user,
                row.getCell(0).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                type,
                amount,
                categoryCode
        );
    }
}
