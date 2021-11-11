package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.CourseMapper;
import com.wingsiwoo.www.dao.StudentCourseMapper;
import com.wingsiwoo.www.dao.UserMapper;
import com.wingsiwoo.www.entity.bo.StudentGradeExcelBo;
import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.entity.po.Course;
import com.wingsiwoo.www.entity.po.StudentCourse;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.service.CourseService;
import com.wingsiwoo.www.util.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Resource
    private StudentCourseMapper studentCourseMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResponseEntity<byte[]> exportGradeExcel(Integer courseId) {
        Course course = getById(courseId);
        Assert.notNull(course, "课程不存在");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<StudentCourse> studentCourses = studentCourseMapper.selectByCourseId(courseId);
        if (CollectionUtils.isNotEmpty(studentCourses)) {
            List<Integer> studentIds = studentCourses.stream().map(StudentCourse::getStudentId).collect(Collectors.toList());
            List<User> users = userMapper.selectBatchIds(studentIds);
            Map<Integer, Float> gradeMap = studentCourses.stream().collect(Collectors.toMap(StudentCourse::getStudentId, StudentCourse::getGrade));
            List<StudentGradeExcelBo> excelBoList = users.stream().map(user -> {
                StudentGradeExcelBo excelBo = new StudentGradeExcelBo();
                excelBo.setAccount(user.getAccount());
                excelBo.setName(user.getName());
                excelBo.setGrade(gradeMap.get(user.getId()));
                return excelBo;
            }).collect(Collectors.toList());
            ExcelUtil.writeToExcel(course.getName() + "成绩", byteArrayOutputStream, StudentGradeExcelBo.class, null, excelBoList, 1, 0);
        } else {
            ExcelUtil.writeToExcel(course.getName() + "成绩", byteArrayOutputStream, StudentGradeExcelBo.class, null, new LinkedList<>(), 1, 0);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", course.getName() + "成绩");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<byte[]> exportStudentCourseInfo(Integer courseId) {
        Course course = getById(courseId);
        Assert.notNull(course, "课程不存在");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        List<StudentCourse> studentCourses = studentCourseMapper.selectByCourseId(courseId);
        if (CollectionUtils.isNotEmpty(studentCourses)) {
            List<Integer> studentIds = studentCourses.stream().map(StudentCourse::getStudentId).collect(Collectors.toList());
            List<User> users = userMapper.selectBatchIds(studentIds);
            List<UserExcelBo> excelBoList = UserExcelBo.transformToUserExcelBo(users);
            ExcelUtil.writeToExcel(course.getName() + "学生名单", byteArrayOutputStream, UserExcelBo.class, null, excelBoList, 1, 0);
        } else {
            ExcelUtil.writeToExcel(course.getName() + "学生名单", byteArrayOutputStream, UserExcelBo.class, null, new LinkedList<>(), 1, 0);
        }


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", course.getName() + "学生名单");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }
}
