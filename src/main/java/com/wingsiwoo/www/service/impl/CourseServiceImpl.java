package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.CourseMapper;
import com.wingsiwoo.www.dao.StudentCourseMapper;
import com.wingsiwoo.www.dao.UserMapper;
import com.wingsiwoo.www.entity.bo.ImportGradeExcelBo;
import com.wingsiwoo.www.entity.bo.StudentGradeExcelBo;
import com.wingsiwoo.www.entity.bo.UpdateGradeBo;
import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.entity.po.Course;
import com.wingsiwoo.www.entity.po.StudentCourse;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.service.CourseService;
import com.wingsiwoo.www.service.StudentCourseService;
import com.wingsiwoo.www.util.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    @Resource
    private StudentCourseService studentCourseService;

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

    @Override
    public boolean teacherImportGrade(ImportGradeExcelBo excelBo) {
        Course course = getById(excelBo.getCourseId());
        Assert.notNull(course, "课程不存在");
        Assert.isTrue(course.getTeacherId().equals(excelBo.getTeacherId()), "非该课程任教老师，不可修改成绩");

        InputStream inputStream = null;
        List<StudentGradeExcelBo> excelBoList;
        try {
            inputStream = excelBo.getFile().getInputStream();
            excelBoList = ExcelUtil.simpleReadFromExcel(inputStream, 0, StudentGradeExcelBo.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("导入失败");
        } finally {
            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("关闭资源失败");
                }
            }
        }

        List<String> accounts = excelBoList.stream().map(StudentGradeExcelBo::getAccount).collect(Collectors.toList());
        List<User> users = userMapper.selectBatchByAccounts(accounts);
        accounts.removeAll(users.stream().map(User::getAccount).collect(Collectors.toList()));
        Assert.isTrue(CollectionUtils.isEmpty(accounts), "存在错误的学号：" + StringUtils.join(accounts.toArray(), ","));
        Map<String, Integer> accountMap = users.stream().collect(Collectors.toMap(User::getAccount, User::getId));
        List<StudentCourse> studentCourseList = excelBoList.stream().map(bo -> {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setCourseId(excelBo.getCourseId());
            studentCourse.setStudentId(accountMap.get(bo.getAccount()));
            studentCourse.setGrade(bo.getGrade());
            return studentCourse;
        }).collect(Collectors.toList());
        return studentCourseService.saveBatch(studentCourseList);
    }

    @Override
    public ResponseEntity<byte[]> exportGradeTemplate() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel("学生成绩模板", byteArrayOutputStream, StudentGradeExcelBo.class, null, new LinkedList<>(), 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", "学生成绩模板");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public boolean updateStudentGrade(UpdateGradeBo updateGradeBo) {
        User user = userMapper.selectById(updateGradeBo.getUserId());
        Assert.notNull(user, "用户id错误");
        User student = userMapper.selectByAccount(updateGradeBo.getAccount());
        Assert.notNull(student, "学生学号错误");
        Course course = getById(updateGradeBo.getCourseId());
        Assert.notNull(course, "课程id错误");
        Assert.isTrue(course.getTeacherId().equals(updateGradeBo.getUserId()), "非该门课程授课老师");
        StudentCourse studentCourse = studentCourseMapper.selectRelation(updateGradeBo.getCourseId(), student.getId());
        Assert.notNull(studentCourse, "该学生未选修该门课程");
        studentCourse.setGrade(updateGradeBo.getGrade());
        return studentCourseMapper.updateById(studentCourse) == 1;
    }
}
