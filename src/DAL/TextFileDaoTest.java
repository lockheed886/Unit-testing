package DAL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TextFileDaoTest {
    private TextFileDao textFileDao;

    @BeforeEach
    void setUp() throws Exception {
        textFileDao = new TextFileDao();
    }

    @Test
    void testGetFileNames_NoFiles() {
        assertEquals(0, textFileDao.getFileNames().size(), "Expected no files in an empty database");
    }

    @Test
    void testGetFileContent_ValidFile() {
        String content = textFileDao.getFileContent("existingFile.txt");
        assertNotNull(content, "Content should not be null for an existing file");
    }

    @Test
    void testSaveToDatabase_DuplicateFile() {
        assertDoesNotThrow(() -> textFileDao.saveToDatabase("duplicateFile.txt", "Updated content"), "Should handle duplicate file save without error");
    }
}
