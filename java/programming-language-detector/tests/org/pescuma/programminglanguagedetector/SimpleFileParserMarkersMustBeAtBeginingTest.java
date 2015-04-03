package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pescuma.programminglanguagedetector.SimpleFileParser.Language;

public class SimpleFileParserMarkersMustBeAtBeginingTest {
	
	private SimpleFileParser sfp;
	
	@Before
	public void setup() {
		sfp = new SimpleFileParser(new Language("\\begin{comment}", "\\end{comment}", "%", true));
	}
	
	@Test
	public void testStart() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\begin{comment}"));
	}
	
	@Test
	public void testStartAtMiddle() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a\\begin{comment}"));
	}
	
	@Test
	public void testSecondLine() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\begin{comment}"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
	}
	
	@Test
	public void testEnd() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\begin{comment}"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\end{comment}"));
	}
	
	@Test
	public void testAfterEnd() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\begin{comment}"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("a"));
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\end{comment}"));
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a"));
	}
	
	@Test
	public void testEndAtMiddle() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("\\begin{comment}"));
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("\\end{comment}a"));
	}
	
	@Test
	public void testSingleLine() {
		assertEquals(SimpleFileParser.LineType.Comment, sfp.feedNextLine("%a"));
	}
	
	@Test
	public void testSingleLineAtMiddle() {
		assertEquals(SimpleFileParser.LineType.Code, sfp.feedNextLine("a%a"));
	}
	
}
