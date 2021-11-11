package com.wingsiwoo.www.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.RowWriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
public class ExcelUtil {

    public static <T> List<T> simpleReadFromExcel(InputStream inputStream, int sheetNo, Class<T> clazz) {
        return simpleReadFromExcel(inputStream, sheetNo, 1, clazz);
    }

    public static <T> List<T> simpleReadFromExcel(InputStream inputStream, int sheetNo, int headRowNumber, Class<T> clazz) {
        return EasyExcel.read(inputStream).sheet(sheetNo).headRowNumber(headRowNumber).head(clazz).doReadSync();
    }

    public static <T> void writeToExcel(String sheetName, OutputStream outputStream, Class<T> modelClass, List<List<String>> headList, List<T> dataList, int sheetNo, int headLineMun) {
        writeToExcel(sheetName, outputStream, modelClass, headList, dataList, sheetNo, headLineMun, true);
    }

    public static <T> void writeToExcel(String sheetName, OutputStream outputStream, Class<T> modelClass, List<List<String>> headList, List<T> dataList, int sheetNo, int headLineMun, boolean autoClose) {
        WriteCellStyle headCellStyle = new WriteCellStyle();
        WriteFont headFont = new WriteFont();
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints(Short.valueOf((short)12));
        headFont.setBold(false);
        headFont.setColor((short)32767);
        headCellStyle.setWriteFont(headFont);
        headCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        headCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        WriteFont contentFont = new WriteFont();
        contentFont.setFontName("宋体");
        WriteCellStyle contentCellStyle = new WriteCellStyle();
        contentFont.setColor((short)32767);
        contentCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentCellStyle.setWriteFont(contentFont);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headCellStyle, contentCellStyle);
        ExcelWriterBuilder excelWriterBuilder = new ExcelWriterBuilder();
        excelWriterBuilder.file(outputStream).excelType(ExcelTypeEnum.XLSX).needHead(true).autoCloseStream(autoClose).registerWriteHandler(horizontalCellStyleStrategy).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).registerWriteHandler(new RowWriteHandler() {
            @Override
            public void beforeRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Integer rowIndex, Integer relativeRowIndex, Boolean isHead) {
            }

            @Override
            public void afterRowCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
            }

            @Override
            public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
                if (isHead) {
                    row.setHeightInPoints(21.5F);
                } else {
                    row.setHeightInPoints(18.0F);
                }

            }
        });
        ExcelWriter writer = excelWriterBuilder.build();
        WriteSheet sheet = new WriteSheet();
        sheet.setSheetNo(sheetNo);
        if (modelClass != null) {
            sheet.setClazz(modelClass);
        } else {
            sheet.setHead(headList);
        }

        sheet.setSheetName(sheetName);
        writer.write(dataList, sheet);
        writer.finish();
    }
}
