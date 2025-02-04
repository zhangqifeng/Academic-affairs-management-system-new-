//package com.example.service;
//
//import cn.hutool.core.util.ObjectUtil;
//import com.example.common.Constants;
//import com.example.common.enums.ResultCodeEnum;
//import com.example.common.enums.RoleEnum;
//import com.example.common.enums.SegmentEnum;
//import com.example.common.enums.WeekEnum;
//import com.example.entity.Account;
//import com.example.entity.Choice;
//import com.example.entity.Course;
//import com.example.entity.Curriculum;
//import com.example.exception.CustomException;
//import com.example.mapper.ChoiceMapper;
//import com.example.mapper.CourseMapper;
//import com.example.utils.TokenUtils;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ChoiceService {
//
//    @Resource
//    private ChoiceMapper choiceMapper;
//    @Resource
//    private CourseMapper courseMapper;
//
//    /**
//     * 新增
//     */
////    public void add(Choice choice) {
////        Course course = courseMapper.selectById(choice.getCourseId());
////        List<Choice> list =choiceMapper.selectByCourseId(choice.getCourseId());
////        if (course.getNum().equals(list.size())) {
////            throw new CustomException(ResultCodeEnum.COURSE_NUM_ERROR);
////        }
////
////        List<Choice> sList =choiceMapper.selectByStudentId(choice.getStudentId());
////        for(Choice dbChoice : sList) {
////            Course tmpCourse = courseMapper.selectById(dbChoice.getCourseId());
////            if(course.getWeek().equals(tmpCourse.getWeek()) && course.getSegment().equals(tmpCourse.getSegment())) {
////                throw new CustomException("-1","您之前已经选过" + tmpCourse.getName() + ",与该门课的上课时间冲突，请重新选择");
////            }
////        }
////        choiceMapper.insert(choice);
////    }
//    public void add(Choice choice) {
//        // 检查学生是否已经选择过该课程
//        List<Choice> existingChoices = choiceMapper.selectByStudentId(choice.getStudentId());
//        for (Choice dbChoice : existingChoices) {
//            if (dbChoice.getCourseId().equals(choice.getCourseId())) {
//                throw new CustomException("-1", "您已选择过该课程，请勿重复选课");
//            }
//        }
//
//        Course course = courseMapper.selectById(choice.getCourseId());
//
//        // 获取该课程已选学生数
//        List<Choice> courseChoices = choiceMapper.selectByCourseId(choice.getCourseId());
//
//        // 检查课程是否已达到最大容量
//        if (course.getNum().equals(courseChoices.size())) {
//            throw new CustomException(ResultCodeEnum.COURSE_NUM_ERROR);
//        }
//
//        // 检查是否与其他已选课程的时间冲突
//        for (Choice dbChoice : existingChoices) {
//            Course tmpCourse = courseMapper.selectById(dbChoice.getCourseId());
//
//            // 检查星期和时间段是否冲突
//            if (course.getWeek() != null && tmpCourse.getWeek() != null &&
//                    course.getWeek().equals(tmpCourse.getWeek()) &&
//                    course.getSegment() != null && tmpCourse.getSegment() != null &&
//                    course.getSegment().equals(tmpCourse.getSegment())) {
//
//                throw new CustomException("-1", "您之前已经选过" + tmpCourse.getName() +
//                        ",与该门课的上课时间冲突，请重新选择");
//            }
//        }
//
//        // 插入选课记录
//        choiceMapper.insert(choice);
//    }
//
//
//
//
//    /**
//     * 删除
//     */
//    public void deleteById(Integer id) {
//        choiceMapper.deleteById(id);
//    }
//
//    /**
//     * 批量删除
//     */
//    public void deleteBatch(List<Integer> ids) {
//        for (Integer id : ids) {
//            choiceMapper.deleteById(id);
//        }
//    }
//
//    /**
//     * 修改
//     */
//    public void updateById(Choice choice) {
//        choiceMapper.updateById(choice);
//    }
//
//    /**
//     * 根据ID查询
//     */
//    public Choice selectById(Integer id) {
//        return choiceMapper.selectById(id);
//    }
//
//    /**
//     * 查询所有
//     */
//    public List<Choice> selectAll(Choice choice) {
//        return choiceMapper.selectAll(choice);
//    }
//
//    /**
//     * 分页查询
//     */
//    public PageInfo<Choice> selectPage(Choice choice, Integer pageNum, Integer pageSize) {
//        Account currentUser = TokenUtils.getCurrentUser();
//        if(RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
//            choice.setTeacherId(currentUser.getId());
//        }
//        if(RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
//            choice.setStudentId(currentUser.getId());
//        }
//        PageHelper.startPage(pageNum, pageSize);
//        List<Choice> list = choiceMapper.selectAll(choice);
//        return PageInfo.of(list);
//    }
//
//    public List<Curriculum> getCurriculum() {
//        Account currentUser = TokenUtils.getCurrentUser();
//        //查询出该学生所有的选课信息
//        Choice choice = new Choice();
//        choice.setStudentId(currentUser.getId());
//        List<Choice> choiceList = choiceMapper.selectAll(choice);
//
//        List<Curriculum> list = new ArrayList<>();
//        //按照第几大节和周几来处理数据（总共有5大节，8列数据）
//        //处理第一大节：第一大节（08:00 ~ 09:30）
//        Curriculum first = new Curriculum();
//        first.setSegment(SegmentEnum.FIRST.segment);
//        processWeek(first, getWeekChoiceList(choiceList,SegmentEnum.FIRST.segment));
//        list.add(first);
//
//        Curriculum second = new Curriculum();
//        second.setSegment(SegmentEnum.SECOND.segment);
//        processWeek(second,getWeekChoiceList(choiceList,SegmentEnum.SECOND.segment));
//        list.add(second);
//
//        Curriculum third = new Curriculum();
//        third.setSegment(SegmentEnum.THIRD.segment);
//        processWeek(third,getWeekChoiceList(choiceList,SegmentEnum.THIRD.segment));
//        list.add(third);
//
//        Curriculum forth = new Curriculum();
//        forth.setSegment(SegmentEnum.FORTH.segment);
//        processWeek(forth,getWeekChoiceList(choiceList,SegmentEnum.FORTH.segment));
//        list.add(forth);
//
//        Curriculum fifth = new Curriculum();
//        fifth.setSegment(SegmentEnum.FIFTH.segment);
//        processWeek(fifth,getWeekChoiceList(choiceList,SegmentEnum.FIFTH.segment));
//        list.add(fifth);
//
//
//
//        return list;
//
//    }
//
//        private List<Choice> getWeekChoiceList(List<Choice> choiceList,String segment) {
//            return choiceList.stream().filter(x -> x.getSegment().equals(segment)).collect(Collectors.toList());
//        }
////    private List<Choice> getWeekChoiceList(List<Choice> choiceList, String segment) {
////        return choiceList.stream()
////                .filter(x -> segment != null && segment.equals(x.getSegment()))
////                .collect(Collectors.toList());
////    }
//
//    private void processWeek(Curriculum curriculum,List<Choice> choiceList) {
//        Optional<Choice> first = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.MONDAY.week)).findFirst();
//        first.ifPresent(choice -> curriculum.setMonday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> second = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.TUESDAY.week)).findFirst();
//        second.ifPresent(choice -> curriculum.setTuesday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> third = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.WEDNESDAY.week)).findFirst();
//        third.ifPresent(choice -> curriculum.setWednesday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> forth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.THURSDAY.week)).findFirst();
//        forth.ifPresent(choice -> curriculum.setThursday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> fifth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.FRIDAY.week)).findFirst();
//        fifth.ifPresent(choice -> curriculum.setFriday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> sixth = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.SATURDAY.week)).findFirst();
//        sixth.ifPresent(choice -> curriculum.setSaturday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//        Optional<Choice> seventh = choiceList.stream().filter(x ->x.getWeek().equals(WeekEnum.SUNDAY.week)).findFirst();
//        seventh.ifPresent(choice -> curriculum.setSunday(choice.getName() + "(" + choice.getTeacherName() + ")"));
//
//
//    }
//}


package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.enums.SegmentEnum;
import com.example.common.enums.WeekEnum;
import com.example.entity.Account;
import com.example.entity.Choice;
import com.example.entity.Course;
import com.example.entity.Curriculum;
import com.example.exception.CustomException;
import com.example.mapper.ChoiceMapper;
import com.example.mapper.CourseMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChoiceService {

    @Resource
    private ChoiceMapper choiceMapper;
    @Resource
    private CourseMapper courseMapper;

    /**
     * 新增选课
     */
    public void add(Choice choice) {
        if (choice == null || choice.getCourseId() == null || choice.getStudentId() == null) {
            throw new CustomException("-1", "选课信息不完整");
        }

        // 检查学生是否已经选择过该课程
        List<Choice> existingChoices = choiceMapper.selectByStudentId(choice.getStudentId());
        for (Choice dbChoice : existingChoices) {
            if (dbChoice.getCourseId().equals(choice.getCourseId())) {
                throw new CustomException("-1", "您已选择过该课程，请勿重复选课");
            }
        }

        Course course = courseMapper.selectById(choice.getCourseId());
        if (course == null) {
            throw new CustomException("-1", "课程不存在");
        }

        // 获取该课程已选学生数
        List<Choice> courseChoices = choiceMapper.selectByCourseId(choice.getCourseId());

        // 检查课程是否已达到最大容量
        if (course.getNum() != null && course.getNum().equals(courseChoices.size())) {
            throw new CustomException(ResultCodeEnum.COURSE_NUM_ERROR);
        }

        // 检查是否与其他已选课程的时间冲突
        for (Choice dbChoice : existingChoices) {
            Course tmpCourse = courseMapper.selectById(dbChoice.getCourseId());
            if (tmpCourse == null) {
                continue;
            }

            // 检查星期和时间段是否冲突
            if (course.getWeek() != null && tmpCourse.getWeek() != null &&
                    course.getWeek().equals(tmpCourse.getWeek()) &&
                    course.getSegment() != null && tmpCourse.getSegment() != null &&
                    course.getSegment().equals(tmpCourse.getSegment())) {

                throw new CustomException("-1", "您之前已经选过" + tmpCourse.getName() +
                        ",与该门课的上课时间冲突，请重新选择");
            }
        }

        // 插入选课记录
        choiceMapper.insert(choice);
    }

    /**
     * 删除选课
     */
    public void deleteById(Integer id) {
        if (id == null) {
            throw new CustomException("-1", "选课ID不能为空");
        }
        choiceMapper.deleteById(id);
    }


    /**
     //     * 删除
     //     */

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            choiceMapper.deleteById(id);
        }
    }

    /**
     * 修改选课
     */
    public void updateById(Choice choice) {
        if (choice == null || choice.getId() == null) {
            throw new CustomException("-1", "选课信息不完整");
        }
        choiceMapper.updateById(choice);
    }

    /**
     * 根据ID查询选课
     */
    public Choice selectById(Integer id) {
        if (id == null) {
            throw new CustomException("-1", "选课ID不能为空");
        }
        return choiceMapper.selectById(id);
    }

    /**
     * 查询所有选课
     */
    public List<Choice> selectAll(Choice choice) {
        return choiceMapper.selectAll(choice);
    }

    /**
     * 分页查询选课
     */
    public PageInfo<Choice> selectPage(Choice choice, Integer pageNum, Integer pageSize) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }

        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.TEACHER.name().equals(currentUser.getRole())) {
            choice.setTeacherId(currentUser.getId());
        }
        if (RoleEnum.STUDENT.name().equals(currentUser.getRole())) {
            choice.setStudentId(currentUser.getId());
        }

        PageHelper.startPage(pageNum, pageSize);
        List<Choice> list = choiceMapper.selectAll(choice);
        return PageInfo.of(list);
    }

    /**
     * 获取课程表
     */
    public List<Curriculum> getCurriculum() {
        Account currentUser = TokenUtils.getCurrentUser();
        if (currentUser == null || currentUser.getId() == null) {
            throw new CustomException("-1", "用户未登录或用户信息不完整");
        }

        // 查询出该学生所有的选课信息
        Choice choice = new Choice();
        choice.setStudentId(currentUser.getId());
        List<Choice> choiceList = choiceMapper.selectAll(choice);

        List<Curriculum> list = new ArrayList<>();

        // 按照时间段处理数据
        for (SegmentEnum segmentEnum : SegmentEnum.values()) {
            Curriculum curriculum = createCurriculum(choiceList, segmentEnum);
            list.add(curriculum);
        }

        return list;
    }

    /**
     * 创建课程表项
     */
    private Curriculum createCurriculum(List<Choice> choiceList, SegmentEnum segmentEnum) {
        Curriculum curriculum = new Curriculum();
        curriculum.setSegment(segmentEnum.segment);
        processWeek(curriculum, getWeekChoiceList(choiceList, segmentEnum.segment));
        return curriculum;
    }

    /**
     * 根据时间段筛选选课列表
     */
    private List<Choice> getWeekChoiceList(List<Choice> choiceList, String segment) {
        if (choiceList == null || segment == null) {
            return new ArrayList<>();
        }
        return choiceList.stream()
                .filter(x -> x != null && segment.equals(x.getSegment()))
                .collect(Collectors.toList());
    }

    /**
     * 处理每周的课程
     */
    private void processWeek(Curriculum curriculum, List<Choice> choiceList) {
        if (choiceList == null || curriculum == null) {
            return;
        }

        for (WeekEnum weekEnum : WeekEnum.values()) {
            Optional<Choice> optionalChoice = choiceList.stream()
                    .filter(x -> x != null && weekEnum.week.equals(x.getWeek()))
                    .findFirst();
            optionalChoice.ifPresent(choice -> setCurriculumDay(curriculum, weekEnum, choice));
        }
    }

    /**
     * 设置课程表的具体天
     */
    private void setCurriculumDay(Curriculum curriculum, WeekEnum weekEnum, Choice choice) {
        if (curriculum == null || weekEnum == null || choice == null) {
            return;
        }

        String courseInfo = choice.getName() + "(" + choice.getTeacherName() + ")";
        switch (weekEnum) {
            case MONDAY:
                curriculum.setMonday(courseInfo);
                break;
            case TUESDAY:
                curriculum.setTuesday(courseInfo);
                break;
            case WEDNESDAY:
                curriculum.setWednesday(courseInfo);
                break;
            case THURSDAY:
                curriculum.setThursday(courseInfo);
                break;
            case FRIDAY:
                curriculum.setFriday(courseInfo);
                break;
            case SATURDAY:
                curriculum.setSaturday(courseInfo);
                break;
            case SUNDAY:
                curriculum.setSunday(courseInfo);
                break;
        }
    }
}