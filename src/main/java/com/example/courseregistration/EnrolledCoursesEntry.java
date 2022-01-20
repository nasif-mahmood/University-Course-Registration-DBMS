package com.example.courseregistration;

public class EnrolledCoursesEntry {
    private String Course;
    private String CourseName;
    private String Professor;

    public EnrolledCoursesEntry(String Course, String courseName, String Professor) {
        this.Course = Course;
        this.CourseName = courseName;
        this.Professor = Professor;
    }

    public String getCourse() {
        return this.Course;
    }

    public String getCourseName() {
        return this.CourseName;
    }

    public String getProfessor() {
        return this.Professor;
    }
}
