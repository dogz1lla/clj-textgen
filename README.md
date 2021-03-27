# clj-textgen

## Short description
Little clojure lib for creating pieces of random text that tries to disguise as the real text. Example of use: placeholder text for a webpage in development. 
## Motivation 
Obvious alternative is [lorem ipsum](https://www.lipsum.com). I however wanted to implement something similar myself, and do it in clojure as an exercise of sorts.
## How the text is generated 
To create a random text that at least vaguely resembles real human english text I needed to make sure artificial text possesses at least some qualities characteristic of the real text. 
Firstly, i found data on how often english alphabet letters appear in the beginning of words. This info is available online in excellent [article](http://norvig.com/mayzner.html) by P. Norvig. 
Next, i assumed that each word is a path of a Markov process and thus information on how likely it is to get a particular letter given the current letter is required. 
This transition matrix can be extracted by scraping contents of [this appendix](https://blogs.sas.com/content/iml/files/2014/09/bigrams.txt) of another great [article](https://blogs.sas.com/content/iml/2014/09/26/bigrams.html). 
Last piece of information is the distribution of word lengths in english language. Here i decided to take a somewhat custom approach that uses ideas from [this video](https://www.youtube.com/watch?v=C-kF25fWTO8) (very good watch btw!).
In particular i downloaded a copy of *Twenty Thousand Leagues Under the Seas* and inferred the word-length distribution based on such corpus. 
## Usage
Use `generate-random-word`, `generate-random-sentence`, `generate-random-paragraph` or `generate-random-text` from src/textgen/main.clj.
