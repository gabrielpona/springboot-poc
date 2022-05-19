package dev.vfcardoso.poc.business.repositories.base;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import dev.vfcardoso.poc.business.models.Book;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
