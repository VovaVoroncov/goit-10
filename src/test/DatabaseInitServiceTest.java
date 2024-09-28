
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.goit.hw10.DatabaseInitService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DatabaseInitServiceTest {

    private DatabaseInitService databaseInitService;
    private ClassLoader classLoader;

    @BeforeEach
    void setUp() {
        databaseInitService = new DatabaseInitService();
        classLoader = mock(ClassLoader.class);
        Thread.currentThread().setContextClassLoader(classLoader);
    }

    @Test
    void testGetProperties_Success() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:testdb");

        InputStream inputStream = mock(InputStream.class);
        when(classLoader.getResourceAsStream("hibernate.properties")).thenReturn(inputStream);
        properties.store(inputStream, null);  // Ensure the properties are loaded

        String url = databaseInitService.getProperties();

        assertEquals("jdbc:h2:mem:testdb", url);
    }

    @Test
    void testGetProperties_Failure() {
        when(classLoader.getResourceAsStream("hibernate.properties")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            databaseInitService.getProperties();
        });

        assertTrue(exception.getMessage().contains("Failed to load database."));
    }

    @Test
    void testInitDB() {
        String testUrl = "jdbc:h2:mem:testdb";
        when(classLoader.getResourceAsStream("hibernate.properties"))
                .thenReturn(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));

        databaseInitService.getProperties();  // Set up database URL

        Flyway flywayMock = Mockito.mock(Flyway.class);
        Flyway.configure().dataSource(testUrl, null, null).load();  // Initialize Flyway with mock
        flywayMock.migrate();  // Mock the migrate method

        databaseInitService.initDB();

        verify(flywayMock).migrate();  // Verify that migrate was called
    }
}
