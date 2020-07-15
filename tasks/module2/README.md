# Create an application that parses text from a file and allows you to perform three different operations with text.
## Requirements
* The parsed text should be presented in the form of an object containing, for example, paragraphs, sentences, tokens, words, symbols. A token is a piece of text delimited by white space characters. Use Composite.
* Information classes are entity classes and should not be overloaded with logic.
* Parsed text must be restored to its original form. Spaces and tabs during parsing can be replaced by a single space.
* Regular expressions should be used to divide the text into components. Regular expressions for an application are defined as literal constants.
* The code that breaks the text into its component parts should be issued in the form of parser classes using Chain of Responsibility.
* When developing parsers that parse text, it is necessary to monitor the number of created parser objects.
* Use Log4J to record logs.
* Implement individual tasks for working on text.
## Individual tasks
1. Sort paragraphs by the number of sentences.
2. Sort words in a sentence by length.
3. Sort sentences in a paragraph by word count.