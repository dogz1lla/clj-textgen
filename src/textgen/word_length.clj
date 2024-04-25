(ns textgen.word-length)

;; source for txt literature: project gutenberg
;; code taken from https://www.youtube.com/watch?v=C-kF25fWTO8

(def book (slurp "https://www.gutenberg.org/cache/epub/164/pg164.txt"))

(def words (re-seq #"[\w|']+" book))
(def num-words (float (count words)))

(defn generate-word-freq-distr
  []
  (->> words
       (map clojure.string/lower-case)
       (map count)
       (frequencies)))

(def word-freq-distr (generate-word-freq-distr))

(defn generate-word-length-frequencies
  []
  (->> word-freq-distr
       (map #(vector (key %) (/ (val %) num-words)))
       (flatten)
       (apply hash-map)))

(def word-frequencies (generate-word-length-frequencies))
