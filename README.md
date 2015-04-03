# programming-language-detector

Detect the programing language of a file, based on its filename.


### Usage

To detect the language of a file:
```java
FilenameToLanguage.detectLanguage("abc.java") == "Java"
FilenameToLanguage.detectLanguage("abc.xml") == "XML"
FilenameToLanguage.detectLanguage("build.xml") == "Ant"
```

To detect the type of one line inside the file:
```java
SimpleFileParser parser = new SimpleFileParser("Java");
parser.feedNextLine("{") == SimpleFileParser.LineType.Code;
parser.feedNextLine("    ") == SimpleFileParser.LineType.Empty;
parser.feedNextLine("//a") == SimpleFileParser.LineType.Comment;
```
