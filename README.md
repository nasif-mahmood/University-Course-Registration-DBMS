# University Course Registration DBMS
## Introduction
The Course Registration System is a state of the art program that
allows users, whether they be students, professors, counselors,
deans, or administrators, to monitor and change the courses a college
offers, including the professor teaching the course, the students taking
the course, and more.

The Course Registration System uses a relational database, managed
by MySQL, to store persistent data related to users it must track,
courses and their relationships to users, and much more.

The Course Registration System allows users to login, and then,
depending on the type of user they are, as mentioned above,
add/remove themselves to/from courses, add/remove others to/from
courses, add/remove courses, etc. Each type of user is given a
different view of what they can (and for security reasons) can not do.
For example, a student can add/remove themselves to certain courses
but they can't remove other users from the system, that's something
only somebody logged in as an administrator can do.

What follows is the system requirements for the system, as well as a
conceptual overlook of the database that allows the system to operate
the way it does. Then we will show an overview of the actual
application, show some complex queries that can be ran on the
application, and finally how a user can use our application.
## System Requirements
### System Description
This system will allow students to register for college courses. The students will be able
to view different classes and choose what to sign up for. If the student meets the prerequisites
and the class is open, then they will be able to register for the course. Each student's course
history and enrolled classes will be stored in a database. The info for each course such as the
teacher, students enrolled, prerequisites will be stored in another database. A website will be
used for naive users (students and instructors) to be able to interact with the DBMS. Database
administrators will be able to interact with the DBMS directly using SQL to manipulate the
databases. The functional requirements will allow manipulation of students and teachers with
their courses.

The functions that students will be able to perform include viewing all the courses and
sections available for the university and the specific sections they are signed for in a separate
view, signing up for courses they are able to join based on their prerequisites and if they have
room in their schedule, and remove themselves for classes they longer wish to be enrolled in.
The functions that instructors will be able to perform include viewing which students are
enrolled in which of their sections and adding/removing students from their sections.
The functions that counselors will be able to perform include all the same functions that
students and instructors are able to do but with a higher permission level that will allow them to
override the students and instructors. The counselors will be able to do this for any student or
instructor in the system.

Administrators will have the highest level of permissions allowing them to override any
actions performed by students, instructors and counselors. The functions that administrators will
be able to perform include viewing all the students' sections that they are signed up for and
adding/removing the students from the sections, adding/removing courses and sections, and
assigning professors to different sections of the same course.
Some of the non-functional requirements for the system are the users not having access
to areas they are not allowed to, secure logins being handled by the SSO for the University, and
multiple students will be able to register at the same time and will be enrolled based on a priority
of who enrolled first. The non-functional requirements will focus on reliability, maintainability,
safety, and usability.

Some interface requirements of the system are users being able to access the database
through a website. This website shall have I/O based on the buttons the users press on the
website. Different users will be able to interact with different parts of the website and their view
of the web pages shall be different. The students will be able to see courses and enroll or drop.
The professors shall be able to view the students enrolled in their courses and change the
enrollment of their particular sections. The administrators will be able to manipulate professors'
courses and students' enrollment.
### Context Diagram
image here
### Functional Requirements
At a high level, the system will keep track of a given university's administrators, counselors,
students, professors, courses, and sections of courses in order to create a course registration
system that can be used by students, professors, counselors, and university administrators.
The system will relate professors to courses they teach, as well as sections of said courses,
counselors to subsets of students, administrators to everything, and students to the courses that
they are able to sign up for as well as courses they are currently signed up for.

The system shall allow the addition/removal of students from sections of courses taught in the
university. This functionality will be present for administrators, professors, counselors, and
students, and the addition/removal of students from sections of courses will be based on the
permission levels of each group of users, as well as a given student's credit hours and
previously taken courses.

The system will also allow for the addition of courses/sections of courses and of
removal/addition of professors from certain courses/sections.

The system will implement checks and balances in order to make sure the data in the system is
logically correct at all times, including making sure that the number of a students in a given
section of a course is at or under a certain limit, making sure that students cannot sign up for
sections of courses at the same time, that professors can't be moved to teach sections of
courses they don't teach or to sections that would present a time conflict, and more.
#### Functional Requirements for each group of users
**Professors**
- Shall be able to see a view of all the students signed up for each section of each course
they teach.
- Shall be able to add/remove students to their sections for each course they teach.
- Shall be able to view all the courses and their sections for the entire university.

**Students**
- Shall be able to view all the courses and their sections for the entire university.
- Shall be able to view all courses/sections currently signed up for.
- Shall be able to add themselves to courses in accordance to the amount of hours
they've accumulated, their prerequisites, and amount of credit hours they've signed up
for so far.
- Shall be able to remove themselves from courses.

**Counselors**
- Shall be able to see a view of all the students signed up for each section of each course.
- Shall be able to add/remove students to classes with the added ability to override
prerequisites and credit hour limits.

**Administrators**
- Shall be able to see a view of all the students signed up for each section of each course.
- Shall be able to switch professors to and from different sections of the same course.
- Shall be able to add/remove courses and sections.
- Shall be able to add/remove students from courses with the added ability to override
prerequisites and credit hour limits.
### Non-functional Requirements
- Access to student information shall be restricted based on FERPA guidelines and the
permissions of a given user
- Near real time updates should occur as changes are made to class enrollment and class
creation, an update should take no longer then 30 seconds to appear
- Secure login will be handled by the Universities SSO
- Course registration will be verified and processed within 5 seconds of submission
- System uptime will remain between between 99-100%
- Multiple students should be allowed to register at the same time, registration priority will
be determined based on who registered first
- Daily backups will be done to ensure suitable data redundancy
- On a registration attempt student prerequisites/corequisites will be verified
- Service will be accessible and performant globally
- A class search will be completed in less than 5 seconds
### Interface Requirements
- Usernames/passwords shall be used for access to the database.
- The database shall be updated through a website.
- I/O shall be based on buttons and selection lists.
- There shall be a search engine that allows users to look up courses by name, subject,
course number, and section.
- Professor/administrators shall be able to view all students enrolled in a course.
- Administrators shall be able to assign professors to the course.
- Students shall be able to see all their courses, as well as add and drop.
- No group of users shall be able to use a permission that is above their power. For
example, a student shall not be able to assign a professor to a course, that shall be a
power reserved for administrators only.
- The counsellors shall have a similar view, but they have certain special powers, such as
overriding prerequisites for the student.
## Entity Relationship Model
image here
## Relational Database Schema
image here
## Functional Dependencies
image here
## SQL Statements for Database Construction and Implementation
### CREATE TABLE Commands
User
```sql
CREATE TABLE USER
(User_ID INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
FName VARCHAR(50) NOT NULL,
MName VARCHAR(50) DEFAULT "N/A",
LName VARCHAR(50) NOT NULL,
DOB DATE,
Dept_Name VARCHAR(4),
FOREIGN KEY(Dept_Name) REFERENCES ACADEMIC_DEPARTMENT(D_Name)
ON DELETE SET NULL ON UPDATE CASCADE);
```
Professor
```sql
CREATE TABLE PROFESSOR
(Professor_ID INT PRIMARY KEY,
FOREIGN KEY(Professor_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Counselor
```sql
CREATE TABLE COUNSELOR
(Counselor_ID INT PRIMARY KEY,
FOREIGN KEY(Counselor_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Student
```sql
CREATE TABLE STUDENT
(Student_ID INT PRIMARY KEY,
Counselor_ID INT,
FOREIGN KEY(Counselor_ID) REFERENCES COUNSELOR(Counselor_ID)
ON DELETE SET NULL ON UPDATE CASCADE,
FOREIGN KEY(Student_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Dean
```sql
CREATE TABLE DEAN
(Dean_ID INT PRIMARY KEY,
FOREIGN KEY(Dean_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Enrolled Courses
```sql
CREATE TABLE ENROLLED_COURSES
(Student_ID INT NOT NULL,
Section_ID INT NOT NULL,
PRIMARY KEY (Student_ID, Section_ID),
FOREIGN KEY(Section_ID) REFERENCES SECTION(Section_ID)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(Student_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Section
```sql
CREATE TABLE SECTION
(Course_ID VARCHAR(12) ,
Section_ID INT AUTO_INCREMENT NOT NULL
PRIMARY KEY,
Section_Number CHAR(3) NOT NULL,
Professor_ID INT,
Max_students INT,
Current_students_taking INT,
FOREIGN KEY(Course_ID) REFERENCES COURSE(Course_ID)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(Professor_ID) REFERENCES USER(User_ID)
ON DELETE SET NULL ON UPDATE CASCADE);
```
Course
```sql
CREATE TABLE COURSE
(Course_ID VARCHAR(12) NOT NULL PRIMARY KEY,
Name VARCHAR(50) NOT NULL,
Credit_Hours INT NOT NULL,
D_Name VARCHAR(4) NOT NULL,
Corequisite_ID VARCHAR(12),
FOREIGN KEY(Corequisite_ID) REFERENCES COURSE(Course_ID)
ON DELETE SET NULL ON UPDATE CASCADE,
FOREIGN KEY(D_Name) REFERENCES ACADEMIC_DEPARTMENT(D_Name)
ON DELETE RESTRICT ON UPDATE CASCADE);
```
Prerequisites
```sql
CREATE TABLE PREREQUISITES
(Course_ID VARCHAR(12) NOT NULL,
REQUIRED_GRADE VARCHAR(2) NOT NULL,
Prerequisite_ID VARCHAR(12) NOT NULL PRIMARY
KEY,
FOREIGN KEY(Course_ID) REFERENCES COURSE(Course_ID)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(Prerequisite_ID) REFERENCES COURSE(Course_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Academic Department
```sql
CREATE TABLE ACADEMIC_DEPARTMENT
(D_Name VARCHAR(4) NOT NULL PRIMARY KEY,
Dean_ID INT,
FOREIGN KEY(Dean_ID) REFERENCES DEAN(Dean_ID)
ON DELETE SET NULL
ON UPDATE CASCADE);
```
Has Taken
```sql
CREATE TABLE HAS_TAKEN
(Student_ID INT ,
Course_ID VARCHAR(12) ,
Grade VARCHAR(2) NOT NULL,
PRIMARY KEY(Student_ID, Course_ID),
FOREIGN KEY(Student_ID) REFERENCES STUDENT(Student_ID)
ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY(Course_ID) REFERENCES COURSE(Course_ID)
ON DELETE RESTRICT ON UPDATE CASCADE);
```
User Account
```sql
CREATE TABLE USER_ACCOUNT
(Username VARCHAR(15) ,
Password_Hash VARCHAR(100) NOT NULL,
User_ID INT,
PRIMARY KEY(Username),
FOREIGN KEY(User_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
Administrator
```sql
CREATE TABLE ADMINISTRATOR
(Admin_ID INT PRIMARY KEY,
PRIMARY KEY(Admin_ID),
FOREIGN KEY(Admin_ID) REFERENCES USER(User_ID)
ON DELETE CASCADE ON UPDATE CASCADE);
```
### ALTER Commands
```sql
ALTER TABLE USER
ADD FOREIGN KEY(Dept_Name) REFERENCES ACADEMIC_DEPARTMENT(D_Name)
ON DELETE SET NULL
ON UPDATE CASCADE;
```
### INSERT Commands
User
```sql
INSERT INTO USER
VALUES
(NULL, "Sherman", NULL, "Briar", '1975-05-25', "CS"),
(NULL, "Priya", "M", "Celso", '1962-08-16', "A&H"),
(NULL, "Lanzo", "V", "Gwendal", '1980-11-05', "BBS"),
(NULL, "David", "A", "Olufunke", '1968-09-30', "SOM"),
(NULL, "Janne", "B", "Zafar", '1974-06-25', "IS"),
(NULL, "Denton", "O", "Anwar", '1963-02-12', "NSM"),
(NULL, "Jared", "H", "Hanes", '1950-06-01', NULL),
(NULL, "Jay", "R", "Areih", '1976-03-02', "CS"),
(NULL, "Diane", "O", "Jure", '1965-05-12', "SOM"),
(NULL, "Suljo", "I", "Teboho", '1945-12-23', "BBS"),
(NULL, "Kilikina", "S", "Kelly", '1953-04-26', "A&H"),
(NULL, "Dennis", "E", "Ipati", '1960-01-21', "IS"),
(NULL, "Chimo", NULL, "Donndubhan", '1976-06-04', "NSM");
```
Administrator
```sql
INSERT INTO ADMINISTRATOR
VALUES
(19);
```
Professor
```sql
INSERT INTO PROFESSOR
VALUES
(13),
(14),
(15),
(16),
(17),
(18);
```
Counselors
```sql
INSERT INTO COUNSELOR
VALUES (44), (45), (46), (47), (48), (49);
```
Students
```sql
INSERT INTO STUDENT
VALUES
(38, 45),
(39, 44),
(40, 44),
(41, 48),
(42, 46),
(43, 46);
```
Deans
```sql
INSERT INTO USER (FName, MName, LName, DOB, Dept_name)
VALUES ("Paulius", "T", "Jasmina", "1958-01-29", "CS"),
("Lucia", "P", "Misa", "1973-05-11", "SOM"),
("Ofir", NULL, "Pantaleon", "1950-12-02", "BBS"),
( "Aleksandra", "O", "Gayatri", "1972-02-12", "A&H"),
("Rong", "T", "Odette", "1964-07-05", "IS"),
("Alex", "D", "Stacee", "1970-09-12", "NSM");
INSERT INTO DEAN (Dean_ID)
VALUES (7),
(8),
(9),
(10),
(11),
(12);
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=7
WHERE D_Name="CS";
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=8
WHERE D_Name="SOM";
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=9
WHERE D_Name="BBS";
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=10
WHERE D_Name="A&H";
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=11
WHERE D_Name="IS";
UPDATE ACADEMIC_DEPARTMENT
SET Dean_ID=12
WHERE D_Name="NSM";
```
Enrolled Courses
```sql
INSERT INTO ENROLLED_COURSES (Student_ID, Section_ID)
VALUES
(42, 1),
(39, 1),
(40, 1),
(41, 1);
```
Course
```sql
INSERT INTO COURSE (Course_ID, Name, Credit_Hours, D_Name, Corequisite_ID)
VALUES
("GOVT 2306", "State Nameand Local Government", 3, "IS", NULL),
("AHST 1101", "Introduction to Art History", 3, "A&H", NULL),
("CS 4347", "Database Systems", 3, "CS", NULL),
("ECS 4308", "Technical Communications", 3, "CS", NULL),
("MIS 6308", "System Analysis and Project Management", 3, "SOM", NULL),
("NSC 3344", "Anatomy and Physiology of Speech and Hearing", 3, "BBS", NULL),
("CS 4392", "Computer Animation", 3, "CS", NULL),
("MATH 2418", "Linear Algebra", 4, "NSM", NULL),
("CS 4361", "Computer Graphics", 3, "CS", "MATH 2418"),
("MATH 2417", "Calculus 1", 4, "NSM", NULL),
("CS 3162", "Professional Responsibility in Computer Science", 1, "CS", NULL),
("CS 3345", "Data Structures and Algorithms", 3, "CS", NULL),
("GOVT 2107", "Government and Politics", 1, "IS", NULL);
```
Section
```sql
INSERT INTO SECTION
VALUES
("AHST 1101", 1, "001", 13, 40, 40),
("CS 4361", 55, "055", 16, 50, 2),
("MIS 6308", 13, "013", 15, 30, 0),
("NSC 3344", 25, "025", 14, 25, 10);
```
Prerequisites
```sql
INSERT INTO PREREQUISITES
VALUES
("CS 4392", "D-", "CS 4361"),
("MATH 2418", "D-", "MATH 2417"),
("CS 3162", "C+", "CS 3345"),
("GOVT 2306", "D-", "GOVT 2107");
```
Academic Department
```sql
INSERT INTO Academic_Department (D_Name, Dean_ID)
VALUES ("CS", 582749),
("BBS", 695738),
("A&H", 692749),
("SOM", 040586),
("IS", 599040),
("NSM", 860030);
```
Has Taken
```sql
INSERT INTO HAS_TAKEN (Student_ID, Course_ID, Grade)
VALUES
(39, "CS 3345", "A-"),
(40, "GOVT 2107", "D+"),
(41, "AHST 1101", "C-"),
(42, "MIS 6308", "B");
```
User Account
```sql
INSERT INTO User_Account (Username, Password_Hash, User_ID)
VALUES ("Briar_She02", "d9f8s@HD0!$cdf", 195628),
("Priya_MCel00", "02hr30H309hxF@H", 581650),
("Lanzo_VGwe48", "2MFJDS0!nf!$%mf", 937659),
("David_AOlu", "*j123FH!@hjsSDF", 703758),
("Jay_RAri", "SD23fn0#!%mds", 860546),
("Diane_OJur", "62mFS34$^Gk#$", 472650);
```
### Triggers
Prevent corequisite cycles (Insert)
```sql
DELIMITER |
CREATE TRIGGER PREVENT_COREQ_CYCLE_INSERT
BEFORE INSERT
ON COURSE
FOR EACH ROW
BEGIN
IF EXISTS ( SELECT *
FROM COURSE
WHERE Corequisite_ID = NEW.Course_ID
AND NEW.Corequisite_ID = Course_ID)
THEN
SET NEW.Course_ID = NULL;
END IF;
END |
DELIMITER ;
```
Prevent corequisite cycles (Update)
```sql
DELIMITER |
CREATE TRIGGER PREVENT_COREQ_CYCLE_UPDATE
BEFORE UPDATE
ON COURSE
FOR EACH ROW
BEGIN
IF EXISTS ( SELECT *
FROM COURSE
WHERE Corequisite_ID = NEW.Course_ID
AND NEW.Corequisite_ID = Course_ID)
THEN
SET NEW.Course_ID = NULL;
END IF;
END |
DELIMITER ;
```
Prevent prerequisite cycles (Insert)
```sql
DELIMITER |
CREATE TRIGGER PREVENT_PREREQ_CYCLE_INSERT
BEFORE INSERT
ON PREREQUISITES
FOR EACH ROW
BEGIN
IF EXISTS ( SELECT *
FROM PREREQUISITE
WHERE Prerequsite_ID = NEW.Course_ID
AND NEW.Prerequisite_ID = Course_ID)
THEN
SET NEW.Course_ID = NULL;
SET NEW.Prerequisite_ID = NULL;
END IF;
END |
DELIMITER ;
```
Prevent prerequisite cycles (Update)
```sql
DELIMITER |
CREATE TRIGGER PREVENT_PREREQ_CYCLE_UPDATE
BEFORE UPDATE
ON PREREQUISITES
FOR EACH ROW
BEGIN
IF EXISTS ( SELECT *
FROM PREREQUISITES
WHERE Prerequsite_ID = NEW.Course_ID
AND NEW.Prerequisite_ID = Course_ID)
THEN
SET NEW.Course_ID = NULL;
SET NEW.Prerequisite_ID = NULL;
END IF;
END |
DELIMITER ;
```
Check if prerequisites requirements are met before enrolling (Insert)
```sql
DELIMITER |
CREATE TRIGGER TAKEN_PREREQS_INSERT
BEFORE INSERT
ON ENROLLED_COURSES
FOR EACH ROW
BEGIN
IF(
NOT EXISTS ( SELECT *
FROM SECTION S, PREREQUISITES P, HAS_TAKEN HT
WHERE S.Section_ID = NEW.Section_ID
AND P.Course_ID = S.Course_ID
AND HT.Course_ID = P.Prerequisite_ID
AND HT.Grade >= P.REQUIRED_GRADE )
AND
EXISTS ( SELECT *
FROM SECTION S, PREREQUISITES P
WHERE S.Section_ID = NEW.Section_ID
AND P.Course_ID = S.Course_ID )
)
THEN
SET NEW.Section_ID = NULL;
SET NEW.Student_ID = NULL;
END IF ;
END |
DELIMITER ;
```
Check if prerequisites requirements are met before enrolling (Update)
```sql
DELIMITER |
CREATE TRIGGER TAKEN_PREREQS_UPDATE
BEFORE UPDATE
ON ENROLLED_COURSES
FOR EACH ROW
BEGIN
IF
NOT EXISTS ( SELECT *
FROM SECTION S, PREREQUISITES P, HAS_TAKEN HT
WHERE S.Section_ID = NEW.Section_ID
AND P.Course_ID = S.Course_ID
AND HT.Course_ID = P.Prerequisite_ID
AND HT.Grade >= P.REQUIRED_GRADE
)
AND
EXISTS ( SELECT *
FROM SECTION S, PREREQUISITES P
WHERE S.Section_ID = NEW.Section_ID
AND P.Course_ID = S.Course_ID )
THEN
SET NEW.Section_ID = NULL;
SET NEW.Student_ID = NULL;
END IF;
END |
DELIMITER ;
```
Check if corequisites requirements are met before enrolling (Insert)
```sql
DELIMITER |
CREATE TRIGGER TAKEN_COREQS_INSERT
BEFORE INSERT
ON ENROLLED_COURSES
FOR EACH ROW
BEGIN
IF
NOT EXISTS ( SELECT *
FROM SECTION S, COURSE C, HAS_TAKEN HT
WHERE S.Section_ID = NEW.Section_ID
AND C.Course_ID = S.Course_ID
AND HT.Course_ID = C.Corequisite_ID )
AND
EXISTS ( SELECT *
FROM SECTION S, COURSE C
WHERE S.Section_ID = NEW.Section_ID
AND C.Course_ID = S.Course_ID
AND C.Corequisite_ID IS NULL )
THEN
SET NEW.Section_ID = NULL;
SET NEW.Student_ID = NULL;
END IF;
END |
DELIMITER ;
```
Check if corequisites requirements are met before enrolling (Update)
```sql
DELIMITER |
CREATE TRIGGER TAKEN_COREQS_UPDATE
BEFORE UPDATE
ON ENROLLED_COURSES
FOR EACH ROW
BEGIN
IF
NOT EXISTS ( SELECT *
FROM SECTION S, COURSE C, HAS_TAKEN HT
WHERE S.Section_ID = NEW.Section_ID
AND C.Course_ID = S.Course_ID
AND HT.Course_ID = C.Corequisite_ID )
AND
EXISTS ( SELECT *
FROM SECTION S, COURSE C
WHERE S.Section_ID = NEW.Section_ID
AND C.Course_ID = S.Course_ID
AND C.Corequisite_ID IS NULL )
THEN
SET NEW.Section_ID = NULL;
SET NEW.Student_ID = NULL;
END IF;
END |
DELIMITER ;
```
### Views
Students
```sql
CREATE VIEW Students
AS
SELECT User_ID, FName, MName, LName, DOB, Dept_Name, Counselor_ID FROM
USER,STUDENT
WHERE User_ID IN (SELECT Student_ID FROM STUDENT) AND Student_ID=User_ID;
```
Deans
```sql
CREATE VIEW Deans
AS
SELECT User_ID, FName, MName, LName, DOB, Dept_Name FROM USER
WHERE User_ID IN (SELECT Dean_ID FROM DEAN);
```
Professors
```sql
CREATE VIEW Professors
AS
SELECT User_ID, FName, MName, LName, DOB, Dept_Name FROM USER
WHERE User_ID IN (SELECT Professor_ID FROM PROFESSOR);
```
Counselors
```sql
CREATE VIEW Counselors
AS
SELECT User_ID, FName, MName, LName, DOB, Dept_Name FROM USER
WHERE User_ID IN (SELECT Counselor_ID FROM COUNSELOR)
```
### Complex Queries Examples and Outputs
Get number of students enrolled in each section of AHST 1101
```sql
SELECT Section_ID, COUNT(*) AS Students
FROM SECTION
WHERE Course_ID = "AHST 1101"
GROUP BY Section_ID;
```
Output

|Section_ID   | Students  |
| ----------- | --------- |
| 1           | 1         |
| 2           | 1         | 

Get total number of students that can be enrolled in Computer Science Courses
```sql
SELECT SUM(MAX_STUDENTS) AS Total_Students
FROM SECTION
WHERE Course_ID LIKE 'CS%';
```
Output

|Total_Students|
|--------------|
|80            |

Get maximum amount of students that can be enrolled in each course
```sql
SELECT Course_ID, SUM(MAX_STUDENTS) AS Max_Students
FROM SECTION
GROUP BY Course_ID;
```
Output

| Course_ID | Max_Students|
| --------- | ----------- |
| AHST 1101 | 100         |
| CS 4361   | 50          |
| CS 4392   | 30          |
| MATH 2417 | 100         |
| MATH 2218 | 50          |
| MIS 6308  | 30          |
| NSC 3344  | 25          |
## Snapshots of Tables using MySQL Workbench
image here
## User Application Interface
### Menu Options and Associated SQL Statements
**Options for every user**
1. Login
2. View info about themselves
```sql
SELECT *
FROM USER_ACCOUNT
WHERE Username = (user's username)
```
3. View information about courses (who teaches, how many students, etc.)
```sql
SELECT FName, LName, Max_students, Current_students_taking, Course_ID,
Section_Number
FROM COURSE_REGISTRATION.SECTION,
COURSE_REGISTRATION.USER
WHERE Professor_ID = User_ID and Course_ID = (input course);
```
4. Quit

**After login if Student**
1. Register for course (search for a course based on input and choose to register or
not)
```sql
INSERT INTO ENROLLED_COURSES (Student_ID, Section_ID)
VALUES (x, y)
UPDATE SECTION
SET Current_students_taking = Current_students_taking + 1
WHERE SECTION.Section_ID = ENROLLED_COURSES.Section_ID;
```
2. View enrolled courses
```sql
SELECT Course_ID, Section_Number, FName, LName
FROM COURSE_REGISTRATION.USER,
COURSE_REGISTRATION.SECTION,
COURSE_REGISTRATION.ENROLLED_COURSES
WHERE ENROLLED_COURSES.Student_ID = (input id) AND Professor_ID =
User_ID AND SECTION.Section_ID = ENROLLED_COURSES.Section_ID;
```
3. Unenroll from course (input is section number)
```sql
DELETE FROM ENROLLED_COURSES WHERE Section_ID = (user input)
UPDATE SECTION
SET Current_students_taking = Current_students_taking - 1
WHERE SECTION.Section_ID = ENROLLED_COURSES.Section_ID;
```
4. View Course History
```sql
SELECT * FROM HAS_TAKEN WHERE Student_ID = (current users id);
```
**After login if Professor**
1. View courses and sections they teach
```sql
SELECT Course_ID, Section_Number, Max_students, Current_students_taking
FROM COURSE_REGISTRATION.SECTION
WHERE Professor_ID = (input id);
```
2. Change a student's grade
```sql
UPDATE HAS_TAKEN
SET Grade = (Input from professor)
WHERE Student_ID = (Input from professor) AND Course_ID = (Input from
professor);
```
3. Create/Remove a section
```sql
INSERT INTO SECTION (Section_ID, Section_Number, Max_Students,
Current_Students_Taking)
VALUES ((Input from professor))
DELETE FROM SECTION
WHERE Section_ID = (user input);
```
**After login if Counselor**
1. View students
```sql
SELECT Student_ID, FName, LName
FROM COURSE_REGISTRATION.STUDENT,
COURSE_REGISTRATION.USER
WHERE STUDENT.Counselor_ID = (input id) AND Student_ID = User_ID;
```
2. Add or remove students from courses
    1. Add
    ```sql
    INSERT INTO SECTION (Student_ID, Sec_ID)
    VALUES((Input from counselor))
    UPDATE SECTION
    SET Current_students_taking = Current_students_taking + 1
    WHERE SECTION.Section_ID = ENROLLED_COURSES.Section_ID;
    ```
    2. Remove
    ```sql
    DELETE FROM STUDENT
    WHERE Student_ID = (Input from counselor)
    UPDATE SECTION
    SET Current_students_taking = Current_students_taking - 1
    WHERE SECTION.Section_ID = ENROLLED_COURSES.Section_ID;
    ```
3. Have all of the student options for the students that the counselor is in charge of.

**After login if Dean**
1. View professors (Same stuff professors can view after picking a professor)
```sql
SELECT Professor_ID, FName, LName
FROM COURSE_REGISTRATION.PROFESSOR,
COURSE_REGISTRATION.USER
WHERE USER.Dept_Name = (input dept) AND Professor_ID = User_ID;
```
2. View students (Same stuff students can view after picking a student)
```sql
SELECT Student_ID, FName, LName
FROM COURSE_REGISTRATION.STUDENT,
COURSE_REGISTRATION.USER
WHERE USER.Dept_Name = (input dept) AND Student_ID = User_ID;
```
3. View counselors (Same stuff counselors can view after picking a counselor)
```sql
SELECT Counselor_ID, FName, LName
FROM COURSE_REGISTRATION.COUNSELOR,
COURSE_REGISTRATION.USER
WHERE USER.Dept_Name = (input dept) AND Counselor_ID = User_ID;
```
4. Change what professors teach which section
```sql
UPDATE SECTION
SET Professor_ID = (new professor)
WHERE Section_ID = (Input from dean) AND Professor_ID = (old professor);
```
5. Change counselors for students
```sql
UPDATE STUDENT
SET Counselor_ID = (new counselor)
WHERE Student_ID = (input from dean);
```
6. Create new courses
```sql
INSERT INTO COURSE (Course_ID, Name, Credit_Hours, D_Name,
Corequisite_ID)
VALUES (Input from dean);
```
7. Delete courses
```sql
DELETE FROM COURSE WHERE Course_ID = (input);
```
8. Add new students
```sql
INSERT INTO STUDENT
VALUES (student id and counselor id);
```
9. Delete student
```sql
DELETE FROM STUDENT WHERE Student_ID = (input);
```
10. Add new counselors
```sql
INSERT INTO COUNSELOR
VALUES (counselor id);
```
11. Delete counselor
```sql
DELETE FROM COUNSELOR WHERE Counselor_ID = (input);
```
12. Add new professors
```sql
INSERT INTO PROFESSOR
VALUES (professor id);
```
13. Delete professor
```sql
DELETE FROM PROFESSOR WHERE Professor_ID = (input);
```
**After login if Database Administrator**
1. View users
```sql
SELECT *
FROM USER
NATURAL JOIN USER_ACCOUNT;
```
2. Add users
```sql
INSERT INTO USER
VALUES(input);
```
3. Delete users
```sql
DELETE FROM USER WHERE User_ID = (input);
```
### Application Demonstration
image here
## Try it out
## Conclusion
The Course Registration System had to be built from the ground up. 
Realizing what the application had to do, a database was needed in 
order to fulfill the application's needs. To build a database, we had to
design a database. That has been the extent of this project summed
up from a high level view. The Course Registration System now works
as anybody would expect a system such as it to work, by utilizing a
database storing long term, persistent data about the entities the
Course Registration Systems needs to create, read, update, or delete.

Our group very much enjoyed working on this project, especially when
we had the database ready to go and made an application utilizing it.

Some future work involving the project could include setting up a long
term server to host it. We could also make the Course Registration
System application multithreaded in order to speed up its work,
particularly when running SQL statements over the Internet
## References
Oracle Corporation. "Getting Started with JDBC."
https://docs.oracle.com/javase/tutorial/jdbc/basics/gettingstarted.html

Redko, Alla. "JavaFX: TableView."
https://docs.oracle.com/javafx/2/ui_controls/table-view.htm
