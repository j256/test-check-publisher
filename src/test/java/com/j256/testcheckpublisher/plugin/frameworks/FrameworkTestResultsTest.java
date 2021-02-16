package com.j256.testcheckpublisher.plugin.frameworks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.j256.testcheckpublisher.plugin.frameworks.TestFileResult.TestLevel;

public class FrameworkTestResultsTest {

	@Test
	public void testStuff() {
		String name = "name";
		int numTests = 1;
		int numFailures = 2;
		int numErrors = 3;
		String format = "format";
		FrameworkTestResults results = new FrameworkTestResults(name, numTests, numFailures, numErrors, null, format);
		assertEquals(name, results.getName());
		assertEquals(numTests, results.getNumTests());
		assertEquals(numFailures, results.getNumFailures());
		assertEquals(numErrors, results.getNumErrors());
		assertEquals(format, results.getFormat());
		assertNull(results.getFileResults());
		results.limitFileResults(10, false);
		assertNull(results.getFileResults());
		assertEquals(name + ": " + numTests + " tests, " + numFailures + " failures, " + numErrors
				+ " errors, 0 file-results", results.asString());
		results.addCounts(1, 2, 3);
		assertEquals(name + ": " + (numTests + 1) + " tests, " + (numFailures + 2) + " failures, " + (numErrors + 3)
				+ " errors, 0 file-results", results.asString());

		name = "name2";
		results.setName(name);
		assertEquals(name, results.getName());
		format = "format2";
		results.setFormat(format);
		assertEquals(format, results.getFormat());
	}

	@Test
	public void testLimit() {
		FrameworkTestResults results = new FrameworkTestResults("name", 1, 2, 3, null, null);

		TestFileResult fileResult =
				new TestFileResult("path", 1, 1, TestLevel.ERROR, 0.1F, "testName", "messag", "details");
		results.addFileResult(fileResult);
		results.addFileResult(fileResult);
		results.addFileResult(fileResult);

		assertEquals(3, results.getFileResults().size());
		results.limitFileResults(10, false);
		assertEquals(3, results.getFileResults().size());
		results.limitFileResults(1, false);
		assertEquals(1, results.getFileResults().size());

		fileResult = new TestFileResult("path", 1, 1, TestLevel.NOTICE, 0.1F, "testName", "messag", "details");
		results.addFileResult(fileResult);
		results.addFileResult(fileResult);
		results.addFileResult(fileResult);

		assertEquals(4, results.getFileResults().size());
		results.limitFileResults(10, false);
		assertEquals(4, results.getFileResults().size());
		results.limitFileResults(10, true);
		assertEquals(1, results.getFileResults().size());
	}

	@Test
	public void testNoErrors() {
		FrameworkTestResults results = new FrameworkTestResults();
		String name = "name12";
		results.setName(name);
		assertEquals(name + ": 0 tests, 0 failures, 0 file-results", results.asString());
	}

	@Test
	public void testToString() {
		FrameworkTestResults results = new FrameworkTestResults();
		// had a NPE here
		System.out.println(results.toString());
		TestFileResult fileResult =
				new TestFileResult("path", 1, 1, TestLevel.ERROR, 0.1F, "testName", "messag", "details");
		results.addFileResult(fileResult);
		System.out.println(results.toString());
	}
}
