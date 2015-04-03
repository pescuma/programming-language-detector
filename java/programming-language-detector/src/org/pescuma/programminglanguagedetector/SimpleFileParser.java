package org.pescuma.programminglanguagedetector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleFileParser {
	
	// Based on cloc.pl : Copyright (C) 2006-2013 Northrop Grumman Corporation
	// Based on https://code.google.com/p/gitinspector/source/browse/gitinspector/comment.py
	
	public enum LineType {
		Empty,
		Comment,
		Code
	}
	
	private static Map<String, Language> languages = new HashMap<String, Language>();
	static {
		languages.put("ABAP", new Language("\"").addLineComment("*", true));
		languages.put("ActionScript", new Language("/*", "*/", "//"));
		languages.put("Ada", new Language("--"));
		languages.put("ADSO/IDSM", new Language("*+").addLineComment("*!"));
		languages.put("AMPLE", new Language("//"));
		languages.put("Ant", new Language("<!--", "-->"));
		languages.put("Apex Trigger", new Language("/*", "*/", "//"));
		languages.put("Arduino Sketch", new Language("/*", "*/", "//"));
		languages.put("ASP", new Language("'"));
		languages.put("ASP.Net", new Language("/*", "*/", "//").addMultilineComment("<!--", "-->")
				.addMultilineComment("<%--", "--%>"));
		languages.put("Assembly", new Language("/*", "*/", "//").addLineComment(";"));
		languages.put("AutoHotkey", new Language(";"));
		languages.put("awk", new Language("#"));
		languages.put("Bourne Again Shell", new Language("#"));
		languages.put("Bourne Shell", new Language("#"));
		languages.put("C", new Language("/*", "*/", "//"));
		languages.put("C Shell", new Language("#"));
		languages.put("C#", new Language("/*", "*/", "//"));
		languages.put("C++", new Language("/*", "*/", "//"));
		languages.put("C/C++ Header", new Language("/*", "*/", "//"));
		languages.put("CCS", new Language("/*", "*/", "//"));
		languages.put("Clojure", new Language(";"));
		languages.put("ClojureScript", new Language(";"));
		languages.put("CMake", new Language("#"));
		// TODO languages.put("COBOL", new Language());
		languages.put("CoffeeScript", new Language("#"));
		languages.put("ColdFusion", new Language("<!--", "-->"));
		languages.put("ColdFusion CFScript", new Language("/*", "*/", "//"));
		languages.put("CSS", new Language("/*", "*/", "//"));
		languages.put("Cython", new Language("\"\"\"", "\"\"\"", "#"));
		languages.put("D", new Language("/*", "*/", "//").addMultilineComment("/+", "+/"));
		languages.put("DAL", new Language("[", "]"));
		languages.put("Dart", new Language("/*", "*/", "//"));
		languages.put("DOS Batch", new Language("rem"));
		languages.put("DTD", new Language("<!--", "-->"));
		languages.put("Erlang", new Language("%"));
		languages.put("Expect", new Language("#"));
		languages.put("F#", new Language("(*", "*)", "//")); // TODO Nested comments
		languages.put("Focus", new Language("-*"));
		languages.put("Fortran 77", new Language("!"));
		languages.put("Fortran 90",
				new Language("!").addLineComment("*", true).addLineComment("c", true)
						.addLineComment("C", true));
		languages.put("Fortran 95",
				new Language("!").addLineComment("*", true).addLineComment("c", true)
						.addLineComment("C", true).addCodeLine("!hpf").addCodeLine("!omp"));
		languages.put("GNU Gettext", new Language("#"));
		languages.put("Go", new Language("/*", "*/", "//"));
		languages.put("Groovy", new Language("/*", "*/", "//"));
		languages.put("Haskell", new Language("{-", "-}", "--"));
		languages.put("HTML", new Language("<!--", "-->"));
		languages.put("IDL", new Language(";"));
		languages.put("InstallShield", new Language("<!--", "-->"));
		languages.put("Java", new Language("/*", "*/", "//"));
		languages.put("Javascript", new Language("/*", "*/", "//"));
		languages.put("JavaServer Faces", new Language("/*", "*/", "//"));
		languages.put("JCL", new Language("//*"));
		languages.put("JSP", new Language("/*", "*/", "//").addMultilineComment("<!--", "-->")
				.addMultilineComment("<%--", "--%>"));
		languages.put("Kermit", new Language("#").addLineComment(";"));
		languages.put("Korn Shell", new Language("#"));
		languages.put("LESS", new Language("/*", "*/", "//"));
		languages.put("lex", new Language("/*", "*/", "//"));
		languages.put("Lisp", new Language(";"));
		languages.put("LiveLink OScript", new Language("//"));
		languages.put("Lua", new Language("--"));
		languages.put("m4", new Language("dnl "));
		languages.put("make", new Language("#"));
		languages.put("MATLAB", new Language("%"));
		languages.put("Maven", new Language("<!--", "-->"));
		languages.put("ML", new Language("(*", "*)"));
		languages.put("Modula3", new Language("(*", "*)"));
		languages.put("MSBuild scripts", new Language("<!--", "-->"));
		languages.put("MUMPS", new Language(";"));
		languages.put("MXML", new Language("/*", "*/", "//").addMultilineComment("<!--", "-->"));
		languages.put("NAnt scripts", new Language("<!--", "-->"));
		languages.put("NASTRAN DMAP", new Language("$"));
		languages.put("Objective C", new Language("/*", "*/", "//"));
		languages.put("Objective C++", new Language("/*", "*/", "//"));
		languages.put("OCaml", new Language("(*", "*)", "//").addMultilineComment("{", "}"));
		languages.put("OpenCL", new Language("/*", "*/", "//"));
		languages.put("OpenGL Shading Language", new Language("/*", "*/", "//"));
		languages.put("Oracle Forms", new Language("/*", "*/", "//"));
		languages.put("Oracle Reports", new Language("/*", "*/", "//"));
		languages.put("Pascal", new Language("(*", "*)", "//").addMultilineComment("{", "}"));
		languages.put("Patran Command Language", new Language("/*", "*/", "//").addLineComment("#")
				.addLineComment("$#"));
		languages.put("Perl", new Language("^=head1", "^=cut", "#", true).addMultilineComment(
				"^__(END|DATA)__", null));
		languages.put("PHP", new Language("/*", "*/", "//").addLineComment("#"));
		languages.put("Pig Latin", new Language("/*", "*/", "//").addLineComment("--"));
		languages.put("PowerShell", new Language("<#", "#>", "#"));
		languages.put("Python", new Language("\"\"\"", "\"\"\"", "#"));
		languages.put("QML", new Language("/*", "*/", "//"));
		languages.put("Razor", new Language("/*", "*/", "//").addMultilineComment("@*", "*@"));
		languages.put("Rexx", new Language("/*", "*/", "//"));
		languages.put("Ruby", new Language("=begin", "=end", "#", true));
		languages.put("Ruby HTML", new Language("<!--", "-->"));
		languages.put("Rust", new Language("/*", "*/", "//"));
		languages.put("SASS", new Language("/*", "*/", "//"));
		languages.put("Scala", new Language("/*", "*/", "//"));
		languages.put("sed", new Language("#"));
		languages.put("SKILL", new Language("/*", "*/", "//").addLineComment(";"));
		languages.put("SKILL++", new Language("/*", "*/", "//").addLineComment(";"));
		languages.put("Smarty", new Language("{*", "*}"));
		languages.put("Softbridge Basic",
				new Language(null, "Attribute VB_Name =", "'").addLineComment("Attribute "));
		languages.put("SQL", new Language("/*", "*/", "--"));
		languages.put("SQL Data", new Language("/*", "*/", "--"));
		languages.put("SQL Stored Procedure", new Language("/*", "*/", "--"));
		languages.put("Tcl/Tk", new Language("#"));
		languages.put("Teamcenter def", new Language("#"));
		languages.put("Teamcenter met", new Language("/*", "*/", "//"));
		languages.put("Teamcenter mth", new Language("#"));
		languages.put("Tex", new Language("\\begin{comment}", "\\end{comment}", "%", true));
		languages.put("Vala", new Language("/*", "*/", "//"));
		languages.put("Vala Header", new Language("/*", "*/", "//"));
		languages.put("Verilog-SystemVerilog", new Language("/*", "*/", "//"));
		languages.put("VHDL", new Language("/*", "*/", "//").addLineComment("--"));
		languages.put("vim script", new Language("\""));
		languages.put("Visual Basic",
				new Language(null, "Attribute VB_Name =", "'").addLineComment("Attribute "));
		languages.put("Visualforce Component", new Language("<!--", "-->"));
		languages.put("Visualforce Page", new Language("<!--", "-->"));
		languages.put("XAML", new Language("<!--", "-->"));
		languages.put("XML", new Language("<!--", "-->"));
		languages.put("XSD", new Language("<!--", "-->"));
		languages.put("XSLT", new Language("<!--", "-->"));
		languages.put("yacc", new Language("/*", "*/", "//"));
		languages.put("YAML", new Language("#"));
	}
	
	private final Language language;
	private String[] insideComment;
	private int lineNum = 0;
	
	public SimpleFileParser(String languageName) {
		this(languages.get(languageName));
	}
	
	public SimpleFileParser(Language lang) {
		language = lang != null ? lang : new Language(null);
	}
	
	public LineType feedNextLine(String line) {
		lineNum++;
		
		String lineClean = line.replaceAll("\\s+", " ").trim();
		
		if (lineClean.isEmpty())
			return LineType.Empty;
		
		if (insideComment == null) {
			for (LineComment code : language.codeLines) {
				if (code.matches(line, lineClean))
					return LineType.Code;
			}
			
			for (LineComment comment : language.lineComments) {
				if (comment.matches(line, lineClean))
					return LineType.Comment;
			}
		}
		
		if (language.multilineComments != null) {
			if (language.markersMustBeAtBegining)
				return processBeginEndOnlyAtBegining(lineClean);
			else
				return processBeginEnd(lineClean);
		}
		
		return LineType.Code;
	}
	
	private LineType processBeginEndOnlyAtBegining(String line) {
		if (insideComment == null) {
			for (String[] comment : language.multilineComments) {
				if (comment[0] == null) {
					if (lineNum == 1) {
						insideComment = comment;
						return LineType.Comment;
					}
					
				} else if (line.startsWith(comment[0])) {
					insideComment = comment;
					return LineType.Comment;
				}
			}
			return LineType.Code;
			
		} else {
			if (insideComment[1] != null && line.startsWith(insideComment[1])) {
				boolean hasCode = !line.substring(insideComment[1].length()).isEmpty();
				insideComment = null;
				return hasCode ? LineType.Code : LineType.Comment;
				
			} else {
				return LineType.Comment;
			}
		}
	}
	
	private LineType processBeginEnd(String line) {
		boolean hasCode = false;
		boolean hasComment = false;
		
		int pos = 0;
		int length = line.length();
		do {
			
			if (insideComment == null) {
				int foundPos = Integer.MAX_VALUE;
				String[] found = null;
				
				for (String[] comment : language.multilineComments) {
					if (comment[0] == null) {
						if (lineNum == 1 && pos == 0) {
							foundPos = pos;
							found = comment;
						}
						
					} else {
						int posStart = line.indexOf(comment[0], pos);
						if (posStart < 0 || posStart > foundPos)
							continue;
						
						foundPos = posStart;
						found = comment;
					}
				}
				
				if (found == null) {
					hasCode = hasCode || !line.substring(pos).trim().isEmpty();
					
					pos = length;
					
				} else {
					hasCode = hasCode || !line.substring(pos, foundPos).trim().isEmpty();
					hasComment = true;
					
					if (found[0] != null)
						pos = foundPos + found[0].length();
					insideComment = found;
				}
				
			} else {
				hasComment = true;
				
				int posEnd = insideComment[1] != null ? line.indexOf(insideComment[1], pos) : -1;
				if (posEnd < 0) {
					pos = length;
				} else {
					pos = posEnd + insideComment[1].length();
					insideComment = null;
				}
			}
			
		} while (pos < length);
		
		if (hasCode)
			return LineType.Code;
		
		if (hasComment)
			return LineType.Comment;
		
		return LineType.Code;
	}
	
	static class Language {
		public final List<LineComment> codeLines = new ArrayList<LineComment>();
		public final List<LineComment> lineComments = new ArrayList<LineComment>();
		public final List<String[]> multilineComments = new ArrayList<String[]>();
		public final boolean markersMustBeAtBegining;
		
		public Language(String commentBegin, String commentEnd, String commentLine,
				boolean markersMustBeAtBegining) {
			this.markersMustBeAtBegining = markersMustBeAtBegining;
			
			if (commentBegin != null || commentEnd != null)
				addMultilineComment(commentBegin, commentEnd);
			
			if (commentLine != null)
				addLineComment(commentLine);
		}
		
		public Language(String commentBegin, String commentEnd, String commentLine) {
			this(commentBegin, commentEnd, commentLine, false);
		}
		
		public Language(String commentLine) {
			this(null, null, commentLine, false);
		}
		
		public Language(String commentBegin, String commentEnd) {
			this(commentBegin, commentEnd, null, false);
		}
		
		public Language(String commentBegin, String commentEnd, boolean markersMustBeAtBegining) {
			this(commentBegin, commentEnd, null, markersMustBeAtBegining);
		}
		
		public Language addLineComment(String start) {
			return addLineComment(start, false);
		}
		
		public Language addLineComment(String start, boolean mustBeAtFirstColumn) {
			lineComments.add(new LineComment(start, mustBeAtFirstColumn));
			return this;
		}
		
		public Language addMultilineComment(String start, String end) {
			multilineComments.add(new String[] { start, end });
			return this;
		}
		
		public Language addCodeLine(String start) {
			codeLines.add(new LineComment(start, false));
			return this;
		}
		
	}
	
	private static class LineComment {
		public final String start;
		public final boolean mustBeAtFirstColumn;
		
		public LineComment(String start, boolean mustBeAtFirstColumn) {
			this.start = start;
			this.mustBeAtFirstColumn = mustBeAtFirstColumn;
		}
		
		public boolean matches(String line, String lineClean) {
			if (mustBeAtFirstColumn)
				return line.startsWith(start);
			else
				return lineClean.startsWith(start);
		}
	}
}
