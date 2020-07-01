package com.github.wxiaoqi.security.common.util;

import org.apache.poi.hssf.usermodel.*;

/**
 * 导出Excel
 */
public class ExcelUtil {

    public static HSSFWorkbook getHSSFWorkbook(String sheetName,String [] title,String [][] values,HSSFWorkbook wb) {

        if (wb == null) {
            wb = new HSSFWorkbook();
        }

        //创建sheet
        HSSFSheet sheet = wb.createSheet(sheetName);

        HSSFRow row = sheet.createRow(0);

        //创建单元格
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //创建列
        HSSFCell cell = null;

        //创建标题
        for (int i = 0; i < title.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //创建内容
        for(int i=0;i<values.length;i++){
            row = sheet.createRow(i + 1);
            for(int j=0;j<values[i].length;j++){
                //将内容按顺序赋给对应的列对象
                row.createCell(j).setCellValue(values[i][j]);
            }
        }
        return wb;
    }
}
