package com.example.courseregistration;

public class CourseHistoryEntry {
    private int grade;
    private String Course_ID;

    public CourseHistoryEntry(String cid, int grde) {
        grade = grde;
        Course_ID = cid;
    }

    public int getGrade() {
        return this.grade;
    }

    public String getCid() {
        return this.Course_ID;
    }
}
