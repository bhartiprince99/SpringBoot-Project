package com.example.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.sms.entity.Student;
import com.example.sms.repository.StudentRepository;
import com.example.sms.service.StudentService;

@Controller
public class StudentController {

	private StudentService StudentService ;

	public StudentController(StudentService studentService) {
		super();
		StudentService = studentService;
	}

	// Handler method to handle list students and return  mode and view
	@GetMapping("/students")
	public String ListStudent(Model model) { 
		model.addAttribute("students",StudentService.getAllStudent());
		return "students";
	}
	
	@GetMapping("/students/new")
	public String createStudentForm(Model model) {
		
		//create student object to hold student from data
		Student student = new Student();
		model.addAttribute("student", student);
		return "create_student";	
	}
	@PostMapping("/students")
	public String saveStudent(@ModelAttribute("student") Student student) {
		StudentService.saveStudent(student);
		return "redirect:/students";
		
	}
	@GetMapping("/students/edit/{id}")
	public String editStudentFrom(@PathVariable Long id,Model model) {
		model.addAttribute("student", StudentService.getStudentById(id));
		return "edit_student";
		
	}

	@PostMapping("/students/{id}")
	public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,Model model) {
		
		// get student from database by id
		Student existingStudent = StudentService.getStudentById(id);
		existingStudent.setId(id);
		existingStudent.setFirstname(student.getFirstname());
		existingStudent.setLastname(student.getLastname());
		existingStudent.setEmail(student.getEmail());
		existingStudent.setMobileno(student.getMobileno());
		
		
		// save updated student object
		StudentService.updateStudent(existingStudent);
		return "redirect:/students ";
	}
	
	// handler method to handle delete student request
	@GetMapping("/students/{id}")
	public String deleteStudent(@PathVariable Long id) {
		StudentService.deleteStudentById(id);
		return "redirect:/students ";
		
	}
	
}
