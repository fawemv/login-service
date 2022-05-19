package com.example.loginservice.servicecode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.loginservice.servicecode.entity.Student;
import com.example.loginservice.servicecode.mapper.StudentMapper;
import com.example.loginservice.servicecode.service.IStudentService;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈浪
 * @since 2022-04-14
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

    @Resource
    private StudentMapper studentMapper;


    @Override
    public IPage<Student> selectYesRegisterInfo(Integer page, Integer pageSize, Long likeData) {
        String str = null;
        if (likeData != 0) {
            str = String.valueOf(likeData) + "%";
        }
        return studentMapper.selectYesRegisterInfo(new Page(page, pageSize), str);
    }

    @Override
    public IPage<Student> selectNoRegisterInfo(Integer page, Integer pageSize, Long likeData) {
        String str = null;
        if (likeData != 0) {
            str = String.valueOf(likeData) + "%";
        }
        return studentMapper.selectNoRegisterInfo(new Page(page, pageSize), str);
    }

    @Override
    public IPage<Student> pageInfo(Integer page, Integer pageSize) {
        // 查询满足的数据
        Page<Object> objectPage = new Page<>(page, pageSize);
        IPage<Student> studentIPage = studentMapper.pageInfo(objectPage);

        return studentIPage;
    }

    @Override
    public IPage<Student> selectStudentInfo(Integer page, Integer pageSize, String likeData) {

        Page<Object> objectPage = new Page<>(page, pageSize);
        likeData = likeData + "%";
        IPage<Student> studentIPage = studentMapper.selectStudentInfo(objectPage, likeData);
        return studentIPage;
    }

    // 获取学生的信息

    @Override
    public Student getNowStudentInfo(String stuId) {

        return studentMapper.getNowStudentInfo(Long.parseLong(stuId));
    }

    @Override
    public List<Map<String, Object>> studentSexCount() {
        return studentMapper.studentSexCount();
    }

    @Override
    public List<Map<String, Object>> studentGradeCount() {
        return studentMapper.studentGradeCount();
    }

    @Override
    public HSSFWorkbook exportStudent(List<Map<String, String>> data) {
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
        String[] titles = {"学号", "姓名", "性别", "年级", "学院", "宿舍号"};
        for (int i = 0; i < titles.length; i++) {
            Cell cell = firstRow.createCell(i);
            cell.setCellValue(titles[i]);
        }

        // 创建数据行

        String[] cellData = {"sId", "name", "sex", "grade", "instituteName"};
        for (int i = 0; i < data.size(); i++) {


            Row row1 = sheet.createRow(i + 1);
            for (int j = 0; j < cellData.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(data.get(i).get(cellData[j]));

            }
            String roomId = null;
            try {
                roomId = data.get(i).get("roomId");
            } catch (Exception e) {
                roomId = "";
            }
            Cell cell5 = row1.createCell(5);
            cell5.setCellValue(roomId);

        }
        return workbook;
    }

    @Override
    public List<Map<String, Object>> studentInstituteCount() {

        return studentMapper.studentInstituteCount();
    }
}
