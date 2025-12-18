/*
 * Top-Down Integration Test Suite for Movie Recommendation System
 * Tests components layer by layer, integrating from top (Application) downward
 * Uses stubs for lower-level components and replaces them incrementally
 */
package com.mycompany.swtproject.IntegrationTesting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.mycompany.swtproject.Application;
import com.mycompany.swtproject.MovieFilereader;
import com.mycompany.swtproject.UserFilereader;

public class TopDown_IntegrationTest {

    private static final String TEST_DATA_PATH = "TextFile_integrationTest/";
    private static final String OUTPUT_PATH = "TextFile_integrationTest/test_topdown_output.txt";

    @AfterEach
    public void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_PATH));
        } catch (IOException e) {
        }
    }

    // ========== LAYER 1: Both FileReaders as Stubs ==========
    @Test
    public void Layer1_TestApplicationLogicWithAllStubs() {
        MovieFilereaderStub movieReaderStub = new MovieFilereaderStub();
        UserFilereaderStub userReaderStub = new UserFilereaderStub();

        Application app = new Application(movieReaderStub, userReaderStub);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 1: Output file should be created with stubs");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 1: Output file should not be empty with valid stub data");
        } catch (IOException e) {
            fail("Layer 1: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 2: Replace MovieFilereader Stub ==========
    @Test
    public void Layer2_TestApplicationUserStub() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereaderStub userReaderStub = new UserFilereaderStub();

        Application app = new Application(movieReader, userReaderStub);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 2: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 2: Application should work with real MovieFilereader");
        } catch (IOException e) {
            fail("Layer 2: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 3: Replace UserFilereader Stub ==========
    @Test
    public void Layer3_TestApplicationWithMovieStub() {
        MovieFilereaderStub movieReaderStub = new MovieFilereaderStub();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReaderStub, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 3: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 3: Application should work with real UserFilereader");
        } catch (IOException e) {
            fail("Layer 3: Output file should exist: " + e.getMessage());
        }
    }

    // ========== LAYER 4: Full Integration - Both Readers Real ==========
    @Test
    public void Layer4_TestApplicationWithBothRealReaders() {
        MovieFilereader movieReader = new MovieFilereader();
        UserFilereader userReader = new UserFilereader();

        Application app = new Application(movieReader, userReader);

        app.RecommenderApp(
                TEST_DATA_PATH + "moviesSmall.txt",
                TEST_DATA_PATH + "usersSmall.txt",
                OUTPUT_PATH
        );

        try {
            assertTrue(Files.exists(Paths.get(OUTPUT_PATH)),
                    "Layer 4: Output file should be created");
            String content = new String(Files.readAllBytes(Paths.get(OUTPUT_PATH)));
            assertNotNull(content);
            assertFalse(content.isEmpty(),
                    "Layer 4: Complete integration should generate recommendations");
            assertTrue(content.contains("michael") || content.contains("john"),
                    "Layer 4: Output should contain user recommendations");
        } catch (IOException e) {
            fail("Layer 4: Output file should exist: " + e.getMessage());
        }
    }
}
