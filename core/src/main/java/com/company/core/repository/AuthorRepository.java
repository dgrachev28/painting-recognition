package com.company.core.repository;

import com.company.core.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByReference(String reference);

}
