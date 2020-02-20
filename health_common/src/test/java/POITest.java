import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class POITest {

    @Test
    public void test1() throws IOException {
        //根据路径 创建工作簿对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("D:\\1111.xlsx")));
        //获取到excel表格中第一个sheet页
        XSSFSheet sheetAt = excel.getSheetAt(0);
        //遍历页 获取每行的数据
        for (Row row : sheetAt) {
            for (Cell cell : row) {
                //打印每个单元格的数据
                System.out.println(cell.getStringCellValue());
            }
        }
        excel.close();
    }

    @Test
    public void test2() throws IOException {
        //1:创建工作簿对象
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File("D:\\1111.xlsx")));
        //2:获取excel中第一个sheet页
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        //3:通过工作表对象获取到最后一行
        int lastRowNum = sheetAt.getLastRowNum();
        //4:遍历最后一行;获取行的最后单元格对象 :行号从0开始  lastRowNum =1;
        for (int i = 0; i <= lastRowNum; i++) {
            //5:根据工作表对象  获取到当前行
            XSSFRow row = sheetAt.getRow(i);
            short lastCellNum = row.getLastCellNum();//获取到最后一个单元格的索引
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }
        }
        xssfWorkbook.close();
    }


    @Test
    public void test3(){
        //1:在内存中创建一个EXCEL文件;
        XSSFWorkbook excel = new XSSFWorkbook();
        //2:创建一个工作簿
        XSSFSheet sheet = excel.createSheet("表格");

        //3:在工作表中创建行的对象
        XSSFRow row = sheet.createRow(0);

        //4:在行中创建单元格对象:
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");
        row.createCell(2).setCellValue("年龄");
        row.createCell(3).setCellValue("性别");


    }
}
