package dev.esz.students;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.rmi.StubNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/student")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentDTO> getAllStudents() {
        return studentService.getStudents();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentDTO getStudent(@PathVariable("id") Long id) throws StudentNotFoundException {
        return studentService.getStudentById(id).orElseThrow(StudentNotFoundException::new);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND,
            reason = "Student not found!")
    @ExceptionHandler(StudentNotFoundException.class)
    public void notFound() {
        // Nothing to do
    }


    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addNewStudent(@RequestBody StudentDTO student) {
        studentService.addStudent(student);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void updateNewStudent(@RequestBody StudentDTO student) throws StudentNotFoundException {
        studentService.updateStudent(student);
    }

    @DeleteMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("id") Long id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
    }



}
