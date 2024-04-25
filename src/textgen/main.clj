(ns textgen.main
  (:require [textgen.word-length :as words]
            [textgen.markov-letter :as markov]
            [textgen.first-letter :as first]
            [textgen.trigrams :as trigram]
            [textgen.utils :as utils]))

;; ---------------------------------------------------------
;; calculate cdfs from pdfs
;; ---------------------------------------------------------
(defn generate-word-cdf
  "Generate a cumulative distribution function of the frequencies of lengths of words from a corpus."
  []
  (utils/generate-cdf words/word-frequencies))

(defn generate-letter-transition-proba
  "Generate transition cdfs for each letter using rows of the transition probability matrix."
  []
  (zipmap (keys markov/transition-matrix-map) (map utils/generate-cdf (vals markov/transition-matrix-map))))

(defn generate-first-letter-cdf
  "Generate a cdf of letters in the first position in the word."
  []
  (utils/generate-cdf first/first-letter-frequencies))

(defn generate-trigram-cdf
  "Generate a cdf of trigrams."
  []
  (utils/generate-cdf trigram/trigram-frequencies))

(def word-cdf (generate-word-cdf))
(def letter-transition-cdf (generate-letter-transition-proba))
(def first-letter-cdf (generate-first-letter-cdf))
(def trigram-cdf (generate-trigram-cdf))

;; ---------------------------------------------------------
;; draw from cdfs
;; ---------------------------------------------------------
(defn draw-word-length
  "Draw a word length (an int from 1 to 18 currently)."
  []
  (utils/draw-from-cdf word-cdf))

(defn make-markov-step
  "Given a letter (ie current state in markov process) draw the next letter (ie new state)
  according to transition probability matrix."
  [prev-letter]
  (utils/draw-from-cdf (get letter-transition-cdf prev-letter)))

(defn draw-first-letter
  "Draw a letter for the beginning of the word."
  []
  (utils/draw-from-cdf first-letter-cdf))

(defn draw-trigram
  "Draw a trigram."
  []
  (utils/draw-from-cdf trigram-cdf))

(defn generate-markov-chain-letters
  "Generate a random word as a markov process."
  [length]
  (let [first-letter (draw-first-letter)]
    (loop [i 0
           word first-letter]
      (if (= i (dec length))
        word
        (recur (inc i) (str word (make-markov-step (subs word i))))))))

(defn build-up-word
  [word-so-far final-length]
  (let [have (count word-so-far)
        todo (- final-length have)]
    (if (= have final-length)
      word-so-far
      (cond
        (< todo 3) (recur (str word-so-far (generate-markov-chain-letters todo)) final-length)
        :else (if (< 0.2 (rand))
                ; in 20% of the cases use a trigram
                (recur (str word-so-far (draw-trigram)) final-length)
                ; otherwise default to markov digrams
                (recur (str word-so-far (generate-markov-chain-letters 2)) final-length))))))

(defn generate-random-word
  "Wrapper made because one letter words shoud have special treatment."
  [length]
  (if (= length 1)
    (if (< 0.5 (rand)) "a" "I")
    (let [word (build-up-word "" length)]
      (if (utils/n-consonants-in-a-row? word 4)
        (recur length)
        word))))

(defn generate-random-sentence
  "Generate a sentence of random words."
  [num-words]
  (str
   (clojure.string/capitalize
    (clojure.string/join
     " "
     (map generate-random-word (repeatedly num-words draw-word-length))))
   "."))

(defn generate-random-paragraph
  "Generate a sequence of random sentences."
  [num-sentences]
  (clojure.string/join " " (map generate-random-sentence (repeat num-sentences 5))))

(defn generate-random-text
  "Generate a sequence of random paragraphs."
  [num-paragraphs]
  (clojure.string/join \newline (map generate-random-paragraph (repeat num-paragraphs 5))))

(comment
  (build-up-word "bbbb" 7)
  (generate-random-word 7)
  (generate-random-text 5))
