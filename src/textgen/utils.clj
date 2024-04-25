(ns textgen.utils
  (:require [net.cgrand.enlive-html :as html]))

;; ---------------------------------------------------------
;; utils
;; ---------------------------------------------------------
(def alphabet
  ["A", "B", "C", "D", "E", "F",
   "G", "H", "I", "J", "K",
   "L", "M", "N", "O", "P",
   "Q", "R", "S", "T", "U",
   "V", "W", "X", "Y", "Z"])

(def alphabet-lower
  ["a", "b", "c", "d", "e", "f",
   "g", "h", "i", "j", "k",
   "l", "m", "n", "o", "p",
   "q", "r", "s", "t", "u",
   "v", "w", "x", "y", "z"])

(def consonants
  #{"b", "c", "d", "f",
    "g", "h", "j", "k",
    "l", "m", "n", "p",
    "q", "r", "s", "t",
    "v", "w", "x", "z"})

(defn cast-to-float
  "Wrapper to use with map."
  [thing]
  (Float/parseFloat thing))

;; web scraping
(defn fetch-url
  "Fetch the html page source."
  [url]
  (html/html-resource (java.net.URL. url)))

(defn select-elements
  "Select elements by tags from the html tree."
  [web-page tags]
  (html/select web-page tags))

;; probability
(defn cumsum
  "Given a collection of numbers `coll` return a collection that contains cumulative sum of `coll`."
  [coll]
  (reductions + coll))

(defn generate-cdf
  "Generate a cumulative distribution function of the frequencies of english alphabet letters."
  [distribution]
  (let [d distribution
        k (keys d)
        v (cumsum (vals d))]
    (zipmap k v)))

(defn draw-from-cdf
  "Draw from a given cdf. Cdf has to be a map with keys being the possible draws and values being
  the corresponding cdf values."
  [cdf]
  (let [roll (rand)]
    (->> cdf
         (filter #(> (last %) roll))
         (first)
         (key))))

;; word structure
(defn n-consonants-in-a-row?
  "Predicate that returns true if the word has n consecutive consonants in it."
  [word n]
  (some #(<= n %) (reductions (fn [res v] (if (zero? v) 0 (+ res v))) (map #(if % 1 0) (map #(consonants (str %)) word)))))
