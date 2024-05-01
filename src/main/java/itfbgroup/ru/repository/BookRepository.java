package itfbgroup.ru.repository;

import itfbgroup.ru.entity.Book;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends Neo4jRepository<Book, String> {
    Book findOneByTitle(String title);
    List<Book> findAllByYear(Integer year);

    @Query("MATCH (book: Book) RETURN COUNT(*)")
    int countAll();

    @Query("MATCH (book: Book) RETURN AVG(book.year)")
    int averageYear();

    @Query("MATCH (book: Book) RETURN MIN(book.year)")
    int minYear();

    @Query("MATCH (book: Book) RETURN MAX(book.year)")
    int maxYear();
}

