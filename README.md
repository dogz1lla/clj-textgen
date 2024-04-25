# clj-textgen

## Short description
Little clojure lib for creating pieces of random text that tries to disguise as the real text. Example of use: placeholder text for a webpage in development. 
## Motivation 
Obvious alternative is [lorem ipsum](https://www.lipsum.com). I however wanted to implement something similar myself, and to do it in clojure as an exercise of sorts.
## How the text is generated 
To create a random text that at least vaguely resembles real human english text I needed to make sure artificial text possesses at least some qualities characteristic of the real text. 

Firstly, i found data on how often english alphabet letters appear in the beginning of words. This info is available online in excellent [article](http://norvig.com/mayzner.html) by P. Norvig. 

Next, i assumed that each word is a path of a Markov process and thus information on how likely it is to get a particular letter given the current letter is required. 
This transition matrix can be extracted by scraping contents of [this appendix](https://blogs.sas.com/content/iml/files/2014/09/bigrams.txt) of another great [article](https://blogs.sas.com/content/iml/2014/09/26/bigrams.html). 

Last piece of information is the distribution of word lengths in english language. Here i decided to take a somewhat custom approach that uses ideas from [this video](https://www.youtube.com/watch?v=C-kF25fWTO8) (very good watch btw!).
In particular i downloaded a copy of *Twenty Thousand Leagues Under the Seas* and inferred the word-length distribution based on such corpus. 

This way, to generate a random word one can draw a word length from the corresponding distribution, draw a letter from the distribution of first letters in a word and finally generate a markov chain of letters until the word of desired length is generated. 

**Note**: for words of length 1 only two possibilites are allowed: word *a* and word *I* (like in actual english).

## Usage
Use `generate-random-word`, `generate-random-sentence`, `generate-random-paragraph` or `generate-random-text` from src/textgen/main.clj.

## Example of generated text 
Teby srefe porb wdaonligow ove. Int ye abrero sellool thbe. Ua trm ypaust ven golclivicy. Pa of le meta estsi. Lyuicti hass fech yalsli ewo. 

Fth snpunio ierntihew tioo seqke. Oveb ac ablb an ba. Wn kinyan alo opnisi iti. Ertol ale hoandc ut aziw. Omeak ar te ymeoune lis. 

Ithmiso ervf vireasicaw alst ldh. I ditdco osti in ystdoeid. Tioss xel idaer oft extodtd. Tima groeasheri ilr teso tran. Fecishym ndsee bo odef ilirmido.

Heii ve ac sua he. Avd fordinh xtr ifei shinkith. Has thdinh ce acly widisexpde. Nnelda poliondanteh bacypnsuha uthit pub. Npa cke esti hert weova.

Nre hate teob pan chaa. Sanen coo peres monsi in. At cimar andistt aniutusre ecamo. Tivmeta gcosix seningof erwatps niaryta. Nth tedempo rn ti reeeth.
