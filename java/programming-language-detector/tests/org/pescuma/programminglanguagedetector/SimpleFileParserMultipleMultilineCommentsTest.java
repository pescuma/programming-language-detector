package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.pescuma.programminglanguagedetector.SimpleFileParser.Language;

public class SimpleFileParserMultipleMultilineCommentsTest {
	
	private SimpleFileParser spf;
	
	@Before
	public void setup() {
		spf = new SimpleFileParser(new Language("/*", "*/", "//").addMultilineComment("/+", "+/"));
	}
	
	@Test
	public void testMixedInSameLine() {
		assertEquals(SimpleFileParser.LineType.Comment, spf.feedNextLine("/*a*//+a+/"));
	}
	
	@Test
	public void testMixedInSameLineWithCode() {
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("/*a*/a/+a+/"));
	}
	
	@Test
	public void testOneInsideOtherIsIgnored() {
		assertEquals(SimpleFileParser.LineType.Code, spf.feedNextLine("/*/+*/a"));
	}
}
