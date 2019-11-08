package dev.esz.students;

import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<StudentDTO,Long> {
}
