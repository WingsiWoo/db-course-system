package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.constant.RoleConstant;
import com.wingsiwoo.www.dao.CourseMapper;
import com.wingsiwoo.www.dao.StudentCourseMapper;
import com.wingsiwoo.www.dao.UserMapper;
import com.wingsiwoo.www.dao.UserRoleMapper;
import com.wingsiwoo.www.entity.bo.*;
import com.wingsiwoo.www.entity.po.Course;
import com.wingsiwoo.www.entity.po.StudentCourse;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.service.CourseService;
import com.wingsiwoo.www.service.StudentCourseService;
import com.wingsiwoo.www.util.ExcelUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private CourseMapper courseMapper;

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
            ExcelUtil.writeToExcel(course.getName() + "成绩.xlsx", byteArrayOutputStream, StudentGradeExcelBo.class, null, excelBoList, 1, 0);
        } else {
            ExcelUtil.writeToExcel(course.getName() + "成绩.xlsx", byteArrayOutputStream, StudentGradeExcelBo.class, null, new LinkedList<>(), 1, 0);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", course.getName() + "成绩.xlsx");
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
            ExcelUtil.writeToExcel(course.getName() + "学生名单.xlsx", byteArrayOutputStream, UserExcelBo.class, null, excelBoList, 1, 0);
        } else {
            ExcelUtil.writeToExcel(course.getName() + "学生名单.xlsx", byteArrayOutputStream, UserExcelBo.class, null, new LinkedList<>(), 1, 0);
        }


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", course.getName() + "学生名单.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public boolean teacherImportGrade(MultipartFile file, Integer teacherId, Integer courseId) {
        Course course = getById(courseId);
        Assert.notNull(course, "课程不存在");
        Assert.isTrue(course.getTeacherId().equals(teacherId), "非该课程任教老师，不可修改成绩");

        InputStream inputStream = null;
        List<StudentGradeExcelBo> excelBoList;
        try {
            inputStream = file.getInputStream();
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
            studentCourse.setCourseId(courseId);
            studentCourse.setStudentId(accountMap.get(bo.getAccount()));
            studentCourse.setGrade(bo.getGrade());
            return studentCourse;
        }).collect(Collectors.toList());
        return studentCourseService.saveBatch(studentCourseList);
    }

    @Override
    public ResponseEntity<byte[]> exportGradeTemplate() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel("学生成绩模板.xlsx", byteArrayOutputStream, StudentGradeExcelBo.class, null, new LinkedList<>(), 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", "学生成绩模板.xlsx");
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

    @Override
    public Page<CoursePageBo> showCoursesInPage() {
        List<Course> courses = courseMapper.selectList(null);
        Page<CoursePageBo> page = new Page<>(1, 10);
        Map<Integer, String> teacherMap = userMapper.selectBatchIds(courses.stream().map(Course::getTeacherId).collect(Collectors.toList()))
                .stream().collect(Collectors.toMap(User::getId, User::getName));
        List<CoursePageBo> pageBos = courses.stream().map(course -> {
            CoursePageBo coursePageBo = new CoursePageBo();
            coursePageBo.setId(course.getId());
            coursePageBo.setName(course.getName());
            coursePageBo.setTeacherName(teacherMap.get(course.getTeacherId()));
            coursePageBo.setCredit(course.getCredit());
            coursePageBo.setTime(course.getName());
            coursePageBo.setAddress(course.getAddress());
            return coursePageBo;
        }).collect(Collectors.toList());
        page.setRecords(pageBos);
        page.setTotal(pageBos.size());
        return page;
    }

    @Override
    public Page<CoursePageBo> showSelectedCoursesInPage(Integer userId) {
        User user = userMapper.selectById(userId);
        Assert.notNull(user, "用户不存在");
        Integer roleId = userRoleMapper.selectByUserId(userId).getRoleId();
        Page<CoursePageBo> page = new Page<>(1, 10);

        if (RoleConstant.ROLE_STUDENT.equals(roleId)) {
            List<StudentCourse> studentCourses = studentCourseMapper.selectByStudentId(userId);
            if (CollectionUtils.isNotEmpty(studentCourses)) {
                List<Course> courses = courseMapper.selectBatchIds(studentCourses.stream().map(StudentCourse::getCourseId).collect(Collectors.toList()));
                Map<Integer, String> teacherMap = userMapper.selectBatchIds(courses.stream().map(Course::getTeacherId).collect(Collectors.toList()))
                        .stream().collect(Collectors.toMap(User::getId, User::getName));
                List<CoursePageBo> pageBos = courses.stream().map(course -> {
                    CoursePageBo coursePageBo = new CoursePageBo();
                    coursePageBo.setId(course.getId());
                    coursePageBo.setName(course.getName());
                    coursePageBo.setTeacherName(teacherMap.get(course.getTeacherId()));
                    coursePageBo.setCredit(course.getCredit());
                    coursePageBo.setTime(course.getName());
                    coursePageBo.setAddress(course.getAddress());
                    return coursePageBo;
                }).collect(Collectors.toList());
                page.setRecords(pageBos);
                page.setTotal(pageBos.size());
            }
        } else if (RoleConstant.ROLE_TEACHER.equals(roleId)) {
            List<Course> courses = courseMapper.selectByTeacherId(userId);
            if (CollectionUtils.isNotEmpty(courses)) {
                List<CoursePageBo> pageBos = courses.stream().map(course -> {
                    CoursePageBo coursePageBo = new CoursePageBo();
                    coursePageBo.setId(course.getId());
                    coursePageBo.setName(course.getName());
                    coursePageBo.setTeacherName(user.getName());
                    coursePageBo.setCredit(course.getCredit());
                    coursePageBo.setTime(course.getName());
                    coursePageBo.setAddress(course.getAddress());
                    return coursePageBo;
                }).collect(Collectors.toList());
                page.setRecords(pageBos);
                page.setTotal(pageBos.size());
            }
        }
        return page;
    }

    @Override
    public boolean selectCourse(Integer studentId, Integer courseId) {
        Assert.notNull(userMapper.selectById(studentId), "该学生不存在");
        Course course = courseMapper.selectById(courseId);
        Assert.notNull(course, "该课程不存在");
        Assert.isNull(studentCourseMapper.selectRelation(courseId, studentId), "该学生已选修该门课程");
        LocalDateTime now = LocalDateTime.now();
        Assert.isTrue((now.isEqual(course.getSelectStart()) || now.isAfter(course.getSelectStart())) && (now.isEqual(course.getSelectEnd()) || now.isBefore(course.getSelectEnd())),
                "当前非选课时间");
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(studentId);
        studentCourse.setCourseId(courseId);
        return studentCourseMapper.insert(studentCourse) == 1;
    }

    @Override
    public boolean createCourse(CreateCourseBo createCourseBo) {
        Assert.notNull(userMapper.selectById(createCourseBo.getTeacherId()), " 该教师不存在");
        LocalDateTime now = LocalDateTime.now();
        Assert.isTrue(createCourseBo.getSelectStart().isBefore(createCourseBo.getSelectEnd()) && (now.isEqual(createCourseBo.getSelectStart()) || now.isBefore(createCourseBo.getSelectStart())),
                "选课时间不合法");
        Course course = new Course();
        BeanUtils.copyProperties(createCourseBo, course);
        return save(course);
    }
}
