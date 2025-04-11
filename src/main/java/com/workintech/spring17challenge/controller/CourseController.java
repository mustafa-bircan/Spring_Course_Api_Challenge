package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.model.HighCourseGpa;
import com.workintech.spring17challenge.model.LowCourseGpa;
import com.workintech.spring17challenge.model.MediumCourseGpa;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
public class CourseController {

    private final List<Course> courses = new ArrayList<>();

    private final LowCourseGpa lowCourseGpa;
    private final MediumCourseGpa mediumCourseGpa;
    private final HighCourseGpa highCourseGpa;

    @Autowired
    public CourseController(LowCourseGpa lowCourseGpa, MediumCourseGpa mediumCourseGpa, HighCourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courses;
    }

    @GetMapping("/courses/{name}")
    public ResponseEntity<Course> getCourseByName(@PathVariable String name) {
        log.info("Searching for course with name: {}", name);
        log.info("Available courses: {}", courses);

        Course course = courses.stream()
                .filter(c -> c.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("Course not found with name: {}", name);
                    return new ApiException("Course not found with name: " + name, HttpStatus.NOT_FOUND);
                });

        log.info("Found course: {}", course);
        return ResponseEntity.ok(course);
    }


    @GetMapping("/courses/gpa")
    public ResponseEntity<Double> getTotalGpa() {
        if (courses.isEmpty()) {
            throw new ApiException("No courses to calculate GPA.", HttpStatus.BAD_REQUEST);
        }

        double totalWeightedGrade = 0;
        int totalCredits = 0;

        for (Course course : courses) {
            double courseGpa = 0;
            String gradeNote = course.getGrade().getNote().toUpperCase();

            if (gradeNote.equals("AA") || gradeNote.equals("BA")) {
                courseGpa = course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
            } else if (gradeNote.equals("BB") || gradeNote.equals("CB")) {
                courseGpa = course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
            } else {
                courseGpa = course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
            }

            totalWeightedGrade += courseGpa;
            totalCredits += course.getCredit();
        }

        double gpa = totalWeightedGrade / totalCredits;
        return ResponseEntity.ok(gpa);
    }




    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        if (course.getCredit() == null || course.getCredit() < 1 || course.getCredit() > 4) {
            throw new ApiException("Credit must be between 1 and 4.", HttpStatus.BAD_REQUEST);
        }
        courses.removeIf(c -> c.getName().equals(course.getName()));
        course.setId(generateUniqueId());
        courses.add(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }


    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Integer id, @RequestBody Course newCourseData) {
        Course existingCourse = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("Course not found with ID: " + id, HttpStatus.NOT_FOUND));

        existingCourse.setName(newCourseData.getName());
        existingCourse.setCredit(newCourseData.getCredit());
        existingCourse.setGrade(newCourseData.getGrade());

        return ResponseEntity.ok(existingCourse);
    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Integer id) {
        Course existingCourse = courses.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("Course not found with ID: " + id, HttpStatus.NOT_FOUND));

        courses.remove(existingCourse);
        return ResponseEntity.ok().build();
    }

    private Integer generateUniqueId() {
        return courses.size() + 1;
    }
}