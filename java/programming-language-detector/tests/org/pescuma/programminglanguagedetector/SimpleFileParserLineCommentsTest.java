package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pescuma.programminglanguagedetector.SimpleFileParser.Language;

public class SimpleFileParserLineCommentsTest {
	
	private SimpleFileParser sfp;
	
	@Before
	public void setup() {
		sfp = new SimpleFileParser(new Language("/*", "/*", "!").addLineComment("*", true)
				.addCodeLine("!omp"));
	}
	
	@Test
	public void testAnywhere_AtStart() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("!a"));
	}
	
	@Test
	public void testAnywhere_AfterSpace() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine(" !a"));
	}
	
	@Test
	public void testAnywhere_AtMiddle() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a !a"));
	}
	
	@Test
	public void testOnlyFirstColumn_AtStart() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("*a"));
	}
	
	@Test
	public void testOnlyFirstColumn_AfterSpace() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine(" *a"));
	}
	
	@Test
	public void testOnlyFirstColumn_AtMiddle() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a *a"));
	}
	
	@Test
	public void testLooksLikeCommentButIsCode() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("!omp"));
	}
	
	@Test
	public void testLooksLikeCommentButIsCodeInsideBlock() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("!omp"));
	}
	
}
