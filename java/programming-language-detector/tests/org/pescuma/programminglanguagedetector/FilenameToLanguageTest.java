package org.pescuma.programminglanguagedetector;

import static org.junit.Assert.*;

import org.junit.Test;

public class FilenameToLanguageTest {
	
	@Test
	public void testXML() {
		assertEquals("XML", FilenameToLanguage.detectLanguage("a.xml"));
	}
	
	@Test
	public void testAnt() {
		assertEquals("Ant", FilenameToLanguage.detectLanguage("build.xml"));
	}
	
	@Test
	public void testAntWithPrefix() {
		assertEquals("Ant", FilenameToLanguage.detectLanguage("a.build.xml"));
	}
	
	@Test
	public void testWithoutBinaries() {
		assertEquals(null, FilenameToLanguage.detectLanguage("a.jar", false));
	}
	
	@Test
	public void testWithBinaries() {
		assertEquals("Java", FilenameToLanguage.detectLanguage("a.jar", true));
	}
}
