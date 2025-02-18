package com.example.entity;

import java.io.Serializable;

/**
 * 评教信息表
 */
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Integer id;
    private String name;
    private String teacher;
    private String student;
    private String time;
    private String content;

    public Comment() {
    }

    public Comment(Integer id, String name, String teacher, String student, String time, String content) {
        this.id = id;
        this.name = name;
        this.teacher = teacher;
        this.student = student;
        this.time = time;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}