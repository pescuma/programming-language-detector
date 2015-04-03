package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Test;
import org.pescuma.programminglanguagedetector.SimpleFileParser.Language;

public class SimpleFileParserMultilineAtStartOrEndTest {
	
	@Test
	public void testEatStart() {
		SimpleFileParser spf = new SimpleFileParser(new Language(null, "*/"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("*/"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
	}
	
	@Test
	public void testEatStart_CodeAfter() {
		SimpleFileParser spf = new SimpleFileParser(new Language(null, "*/"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("*/a"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
	}
	
	@Test
	public void testEatEnd() {
		SimpleFileParser spf = new SimpleFileParser(new Language("/*", null));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("a"));
	}
	
	@Test
	public void testEatEnd_CodeBefore() {
		SimpleFileParser spf = new SimpleFileParser(new Language("/*", null));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a/*"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("a"));
	}
	
	@Test
	public void testEatEnd_OnlyAtStart() {
		SimpleFileParser spf = new SimpleFileParser(new Language("/*", null, true));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("a"));
	}
	
	@Test
	public void testEatEnd_OnlyAtStart_CodeBefore() {
		SimpleFileParser spf = new SimpleFileParser(new Language("/*", null, true));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a/*"));
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("a"));
	}
}
