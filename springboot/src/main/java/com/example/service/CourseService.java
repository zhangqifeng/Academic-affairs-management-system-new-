package com.example.service;
import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公告信息表业务处理
 **/
@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;


    public void add(Course course) {
        courseMapper.insert(course);
    }

    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }


    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            courseMapper.deleteById(id);
        }
    }


    public void updateById(Course course) {
        courseMapper.updateById(course);
    }


    public Course selectById(Integer id) {
        return courseMapper.selectById(id);
    }


    public List<Course> selectAll(Course course) {
        return courseMapper.selectAll(course);
    }


    public PageInfo<Course> selectPage(Course course, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Course> list = courseMapper.selectAll(course);
        return PageInfo.of(list);
    }

}
