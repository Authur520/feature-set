package com.authur.funccollect.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 根据sql生成excel
 * @Author: jibing.Li
 * @Date: 2021/11/8 12:04
 */
public class CreateExcel {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/excel")
    @ResponseBody
    public String excel(HttpServletRequest request) throws IOException {
        String sql1 = request.getParameter("sql1");
        String sql2 = request.getParameter("sql2");
        String sql3 = request.getParameter("sql3");
        String xlsName = request.getParameter("xlsName");
        if (StringUtils.isNotBlank(xlsName)){
            HSSFWorkbook workbook=new HSSFWorkbook();//这里也可以设置sheet的Name
            List<String> sqlList = new ArrayList<>();
            sqlList.add(sql1);
            sqlList.add(sql2);
            sqlList.add(sql3);
            for (int i = 0; i < sqlList.size(); i++) {
                HSSFSheet sheet = workbook.createSheet("sql" + i);
                String sql = sqlList.get(i);
                if (StringUtils.isNotBlank(sql)){
                    List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
                    if (list != null && list.size() > 0){
                        createSheet(sheet, list);
                    }
                }
            }

            //文档输出
            FileOutputStream out = new FileOutputStream("/Users/chuan/Desktop/" + xlsName +new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()).toString() +".xls");
            workbook.write(out);
            out.close();
        }else {
            return "请检查文件名称";
        }
        return "200";
    }

    public void createSheet(HSSFSheet sheet,List<Map<String, Object>> list){
        Set<String> keyList = list.get(0).keySet();
        //创建工作表的行
        int k = 0;
        HSSFRow hssfRow = sheet.createRow(0);
        for (String key : keyList) {
            hssfRow.createCell(k++).setCellValue(key);
        }

        for (int i = 1; i <= list.size(); i++) {
            Map<String, Object> map = list.get(i-1);
            int j = 0;
            HSSFRow row = sheet.createRow(i);
            for (Map.Entry<String, Object> v : map.entrySet()){
                row.createCell(j++).setCellValue(v.getValue().toString());//列
            }
        }
    }

}
