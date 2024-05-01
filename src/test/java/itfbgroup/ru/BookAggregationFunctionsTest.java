package itfbgroup.ru;

import itfbgroup.ru.repository.BookRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@DataNeo4jTest
public class BookAggregationFunctionsTest {

    private static Neo4j newServer;

    @BeforeAll
    static void initializeNeo4j() {
        newServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .withFixture(
                        "CREATE (b1:Book {isbn: '978-0395257302', name: 'The Silmarillion (1977)', year: 1977})" +
                                "CREATE (b2:Book {isbn: '978-0547928203', name: 'The Two Towers', year: 1956})" +
                                "CREATE (b3:Book {isbn: '978-0395071229 ', name: 'The Hobbit', year: 1937})" +
                                "CREATE (b4:Book {isbn: '978-0007123810', name: 'Lord of the Rings', year: 1954})")
                .build();
    }

    @AfterAll
    static void stopNeo4j() {
        newServer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", newServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "null");
    }

    @Autowired
    private BookRepository bookRepository;

    @Test
    void countAllBooks() {
        Assertions.assertEquals(4, bookRepository.countAll());
    }

    @Test
    void averageBookEditionYear() {
        Assertions.assertEquals(1956, bookRepository.averageYear());
    }

    @Test
    void minBookEditionYear() {
        Assertions.assertEquals(1937, bookRepository.minYear());
    }

    @Test
    void maxBookEditionYear() {
        Assertions.assertEquals(1977, bookRepository.maxYear());
    }
}
