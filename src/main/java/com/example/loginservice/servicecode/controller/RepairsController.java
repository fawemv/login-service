package com.example.loginservice.servicecode.controller;

import com.example.loginservice.servicecode.entity.Repairs;
import com.example.loginservice.servicecode.service.IRepairsService;
import com.example.loginservice.utils.JwtUtils;
import com.example.loginservice.utils.Result;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Lang
 * @Desc
 * @data 2022/4/28 21:42
 **/
@RestController
@RequestMapping("/repairs")
public class RepairsController {
    @Resource
    private IRepairsService iRepairsService;

    @Resource
    private JwtUtils jwtUtils;

    private Map<String, List<Map<String, String>>> map = new HashMap<>();


    // 修改
    @PreAuthorize("hasRole('admin')")
    @PostMapping("/updateRepairsInfo")
    public Result updateRepairsInfo(@RequestBody Repairs data) {
        Result result = null;
        //根据id更新
        try {
            iRepairsService.updateById(data);
            result = Result.succ(200, "修改成功", "");
        } catch (Exception e) {
            result = Result.succ(404, "修改失败", "");
        } finally {
            return result;
        }
    }


    // 添加信息
    @PreAuthorize("hasRole('student')")
    @PostMapping("/addRepairsInfo")
    public Result addRepairsInfo(@RequestBody Repairs data) {

        Result result = null;
        try {
            iRepairsService.save(data);
            result = Result.succ(200, "申请成功", null);
        } catch (Exception e) {
            result = Result.succ(404, "申请失败", null);
        } finally {
            return result;
        }
    }


    //  获取学生的申请记录
    @PreAuthorize("hasRole('student')")
    @GetMapping("/selectRepairsByIdInfo")
    public Result selectRepairsByIdInfo(HttpServletRequest request) {
        Result result = null;
        List<Map<String, Object>> data = null;

        String studentIdStr = jwtUtils.parseTokenToUsername(request.getHeader(jwtUtils.getHeader()));
        data = iRepairsService.selectRepairsByIdInfo(studentIdStr);

        if (data != null) {
            result = Result.succ(200, "查询成功", data);
        } else {
            result = Result.succ(404, "没有满足条件的数据", null);
        }

        return result;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/getInitData")
    public Result getInitData(){
        List<Repairs> initData = iRepairsService.getInitData();
        return Result.succ(200,"查询成功",initData);
    }


    // 下载学生的信息excel
    @PostMapping("/repairsInfoLoad")
    public Result repairsInfoLoad(@RequestBody List<Map<String, String>> data) throws Exception {

        // 文件输出位置
        String fileName = UUID.randomUUID().toString();
        map.put(fileName, data);
        return Result.succ(fileName);
    }

    @GetMapping("/repairsInfoLoad2/{fileName}")
    public void repairsInfoLoad2(@PathVariable("fileName") String fileName, HttpServletResponse response) throws ServletException, IOException {


        List<Map<String, String>> data = map.remove(fileName);
        // 生成excel文件
        //工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFCellStyle cellStyle = workbook.createCellStyle();
        //设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        //设置垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置下边框
        cellStyle.setBorderBottom(BorderStyle.THIN);
        //设置上边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        //设置走边框
        cellStyle.setBorderLeft(BorderStyle.THIN);
        //设置右边框
        cellStyle.setBorderRight(BorderStyle.THIN);
        //设置字体
        HSSFFont font = workbook.createFont();
        font.setFontName("华文行楷");//设置字体名称
        font.setFontHeightInPoints((short) 28);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(true);//设置是否加粗
        font.setColor(IndexedColors.RED.index);//设置字体颜色
        cellStyle.setFont(font);
        //设置背景
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
        //设置宽度和高度


        // 电子文档
        HSSFSheet sheet = workbook.createSheet("学生信息表");
        sheet.setDefaultColumnWidth(15);
        // 创建行
        HSSFRow firstRow = sheet.createRow(0);

        // 表头
        String[] titles = {"单号", "学号", "姓名", "宿舍楼", "宿舍号", "申请时间","故障描述"};
        for (int i = 0; i < titles.length; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        // 创建数据行

        String[] cellData = {"repairsId", "sid", "studentName", "buildingName", "roomId","creatTime","repairsContent"};
        for (int i = 0; i < data.size(); i++) {


            Row row1 = sheet.createRow(i + 1);
            for (int j = 0; j < cellData.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(data.get(i).get(cellData[j]));

            }

        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("学生信息表.xls", "UTF-8"));

        workbook.write(response.getOutputStream());

    }


}
