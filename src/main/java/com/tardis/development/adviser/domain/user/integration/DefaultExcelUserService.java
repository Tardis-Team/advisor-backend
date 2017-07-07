package com.tardis.development.adviser.domain.user.integration;

import com.tardis.development.adviser.domain.user.User;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;
import java.io.FileInputStream;

@Service
public class DefaultExcelUserService implements ExcelUserService {
    private static final String USERS_SHEET_NAME = "users";

    @Override
    @SneakyThrows
    public Flux<User> list(File file) {
        FileInputStream excelFile = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet(USERS_SHEET_NAME);

        return Flux.fromIterable(sheet)
                .map(this::transformToUser);
    }

    private User transformToUser(Row row) {
        return new User(row.getCell(0).getStringCellValue());
    }
}
