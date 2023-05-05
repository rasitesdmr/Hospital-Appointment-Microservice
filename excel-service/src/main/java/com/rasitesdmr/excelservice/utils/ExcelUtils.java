package com.rasitesdmr.excelservice.utils;

import com.rasitesdmr.excelservice.exception.RegistrationException;
import kafka.model.FileInfo;
import lombok.experimental.UtilityClass;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@UtilityClass
public class ExcelUtils {


    public static boolean hasExcelFormat(FileInfo fileInfo) {
        return fileInfo.getType().equals("multipart/form-data") ||
                fileInfo.getType().equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public static <T> List<T> connectExcelColumnsToModelFields(InputStream inputStream, Class<T> entityClass, Map<Integer, String> columnSetterMapping) {
        List<T> entities = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheet("Sheet1");
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) {
                rows.next();
            }

            while (rows.hasNext()) {
                Row row = rows.next();
                Iterator<Cell> cellsInRow = row.iterator();
                T entity = entityClass.getDeclaredConstructor().newInstance();
                int columnIndex = 0;

                while (cellsInRow.hasNext()) {
                    Cell cell = cellsInRow.next();
                    Method[] methods = entityClass.getMethods();
                    String targetSetter = columnSetterMapping.get(columnIndex);

                    for (Method method : methods) {
                        if (method.getName().equals(targetSetter)) {
                            // Hücre türünü kontrol et ve doğru türde değeri al
                            switch (cell.getCellType()) {
                                case STRING:
                                    method.invoke(entity, cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    if (method.getParameterTypes()[0] == int.class || method.getParameterTypes()[0] == Integer.class) {
                                        method.invoke(entity, (int) cell.getNumericCellValue());
                                    } else if (method.getParameterTypes()[0] == long.class || method.getParameterTypes()[0] == Long.class) {
                                        method.invoke(entity, (long) cell.getNumericCellValue());
                                    } else if (method.getParameterTypes()[0] == double.class || method.getParameterTypes()[0] == Double.class) {
                                        method.invoke(entity, cell.getNumericCellValue());
                                    } else {
                                        throw new IllegalStateException("Desteklenmeyen sayısal tür: " + method.getParameterTypes()[0]);
                                    }
                                    break;
                                default:
                                    throw new IllegalStateException("Desteklenmeyen hücre türü: " + cell.getCellType());
                            }
                            break;
                        }
                    }
                    columnIndex++;
                }
                entities.add(entity);
            }
        } catch (IOException | IllegalAccessException | InstantiationException | InvocationTargetException |
                 NoSuchMethodException exception) {
            throw new RegistrationException("Excel dosyası ayrıştırılamadı: " + exception.getMessage());
        }
        return entities;
    }



}
