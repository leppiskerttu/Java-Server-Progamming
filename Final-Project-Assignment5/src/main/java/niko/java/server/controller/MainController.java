package niko.java.server.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import niko.java.server.model.Course;
import niko.java.server.model.Student;
import niko.java.server.service.CourseService;
import niko.java.server.service.StudentService;

@Controller
@RequestMapping("/api")
public class MainController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;

    @GetMapping("/main")
    public String showUserDetails(Model model, Principal principal) {
        List<Course> courses = courseService.getAllCourses();
        List<Student> students = studentService.getAllStudents();

        model.addAttribute("courses", courses);
        model.addAttribute("students", students);


        return "main-page";
    }
}
