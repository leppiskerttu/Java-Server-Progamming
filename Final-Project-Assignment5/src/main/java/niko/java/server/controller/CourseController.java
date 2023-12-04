package niko.java.server.controller;

import niko.java.server.model.Course;
import niko.java.server.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    public String getAllCourses(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<Course> courses = courseRepository.findAll();
            model.addAttribute("courses", courses);
            return "all-courses";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/add")
    public String showAddCourseForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("course", new Course());
            return "add-course";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Course course = courseRepository.findById(id).orElse(null);
            model.addAttribute("course", course);
            return "course-details";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping
    public String createCourse(@ModelAttribute Course course, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming you don't want users to set the ID manually, let the database generate it
            course.setId(null);
            
            courseRepository.save(course);
            return "redirect:/api/main";
        } else {
            return "redirect:/login";
        }
    }

    @PutMapping("/{id}")
    public String updateCourse(@PathVariable Long id, @ModelAttribute Course updatedCourse, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Course existingCourse = courseRepository.findById(id).orElse(null);
            
            if (existingCourse != null) {
                // Update only the relevant fields
                existingCourse.setCourseName(updatedCourse.getCourseName());
                existingCourse.setTeacher(updatedCourse.getTeacher());
                
                courseRepository.save(existingCourse);
            }
            
            return "redirect:/api/courses";
        } else {
            return "redirect:/login";
        }
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            courseRepository.deleteById(id);
            return "redirect:/api/courses";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/main")
    public String showMainPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            List<Course> courses = courseRepository.findAll();
            model.addAttribute("courses", courses);

            return "main-page";
        } else {
            return "redirect:/login";
        }
    }
}




