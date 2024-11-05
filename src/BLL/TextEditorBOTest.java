package BLL;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.ArrayList;

public class TextEditorBOTest {
    private TextEditorBO textEditorBO;

    @BeforeEach
    void setUp() throws Exception {
        textEditorBO = new TextEditorBO();
    }

    @Test
    void testGetFileNames_EmptyDatabase() {
        ArrayList<String> fileNames = textEditorBO.getFileNames();
        assertEquals(0, fileNames.size(), "Expected no file names in the database");
    }

    @Test
    void testGetFileNames_WithFiles() {
        ArrayList<String> fileNames = textEditorBO.getFileNames();
        assertTrue(fileNames.size() > 0, "Expected at least one file name in the database");
    }

    @Test
    void testGetFileContent_NonExistentFile() {
        String content = textEditorBO.getFileContent("nonexistent.txt");
        assertNull(content, "Expected null content for a non-existent file");
    }

    @Test
    void testSaveToDatabase_ValidFile() {
        assertDoesNotThrow(() -> textEditorBO.saveToDatabase("test.txt", "Sample content"), "Save operation should not throw an exception for valid input");
    }

    @Test
    void testCalculateHash_NonEmptyContent() {
        String hash = textEditorBO.calculateHash("Sample content");
        assertNotNull(hash, "Hash should not be null for non-empty content");
    }
}

