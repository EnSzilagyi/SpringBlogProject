package dev.esz.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> getStudents() {
        return StreamSupport.stream(studentRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    ;

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id);
    }

    public void addStudent(StudentDTO student) {
         studentRepository.save(student);
    }

    public void updateStudent(StudentDTO student) throws StudentNotFoundException {
        getStudentById(student.getId()).orElseThrow(StudentNotFoundException::new);
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) throws StudentNotFoundException {
        StudentDTO student = getStudentById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
    }
}
