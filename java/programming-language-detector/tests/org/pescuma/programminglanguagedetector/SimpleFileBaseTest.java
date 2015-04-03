package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pescuma.programminglanguagedetector.SimpleFileParser.Language;

public class SimpleFileBaseTest {
	
	private SimpleFileParser sfp;
	
	@Before
	public void setup() {
		sfp = new SimpleFileParser(new Language("/*", "*/", "//"));
	}
	
	@Test
	public void testEmpty() {
		assertEquals(SimpleFileParser.LineType.Empty, sfp.feedNextLine(""));
	}
	
	@Test
	public void testCode() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a"));
	}
	
	@Test
	public void testMultipleComment_Start() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*a"));
	}
	
	@Test
	public void testMultipleComment_StartAtMiddle() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a/*a"));
	}
	
	@Test
	public void testMultipleComment_SecondLine() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
	}
	
	@Test
	public void testMultipleComment_End() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("*/"));
	}
	
	@Test
	public void testMultipleComment_AfterEnd() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("*/"));
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a"));
	}
	
	@Test
	public void testMultipleComment_EndAtMiddle() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("*/b"));
	}
	
	@Test
	public void testMultipleComment_SameLineNothingElse() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/**//**//**/"));
	}
	
	@Test
	public void testMultipleComment_SameLineNoCode() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("  /* x*/\t/*b*/ /* a */"));
	}
	
	@Test
	public void testMultipleComment_SameLineWithCodeInBetween() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("/**/a/**/"));
	}
	
	@Test
	public void testMultipleComment_WithCodeInBetween() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*"));
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("*/a/*"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("*/"));
	}
	
	@Test
	public void testMultipleComment_StartInsideComment() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("/*a/*b*/"));
	}
	
	@Test
	public void testMultipleComment_EndBeforeStart() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("*//*"));
	}
	
}
