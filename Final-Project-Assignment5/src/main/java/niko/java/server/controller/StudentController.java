package niko.java.server.controller;

import niko.java.server.model.Student;
import niko.java.server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/add")
    public String showAddStudentForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("student", new Student());
            return "add-student";
        } else {
            return "redirect:/login";
        }
    }

    @PostMapping("/add")
    public String createStudent(@ModelAttribute Student student, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            studentRepository.save(student);
            return "redirect:/api/main";
        } else {
            return "redirect:/login";
        }
    }

    @PutMapping("/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student updatedStudent, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Student existingStudent = studentRepository.findById(id).orElse(null);
            if (existingStudent != null) {
                existingStudent.setStudentName(updatedStudent.getStudentName());
                existingStudent.setFirstname(updatedStudent.getFirstname());
                existingStudent.setLastname(updatedStudent.getLastname());
                existingStudent.setEmail(updatedStudent.getEmail());
                studentRepository.save(existingStudent);
            }
            return "redirect:/api/students";
        } else {
            return "redirect:/login";
        }
    }

}

