package com.example.courseregistration;

public class AvailCoursesEntry {
    private int CID, currentStudents, maxStudents;
    private String course, section, professor;

    public AvailCoursesEntry(int cid, int currStdnts, int maxStdnts, String course, String section, String prof) {
        this.CID = cid;
        this.currentStudents = currStdnts;
        this.maxStudents = maxStdnts;
        this.course = course;
        this.section = section;
        this.professor = prof;
    }

    public int getCid() {
        return CID;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public int getMaxStudents() {
        return maxStudents;
    }

    public String getCourse() {
        return course;
    }

    public String getSection() {
        return section;
    }

    public String getProfessor() {
        return professor;
    }
}
