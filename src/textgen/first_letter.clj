(ns textgen.first-letter
  (:require [textgen.utils :as utils]))

;; Scrape relevant tabular data from a great blog post by Mr. Norvig

(def url "http://norvig.com/mayzner.html")

(def page (slurp url))

(def the-line (str (re-seq #"&nbsp;1   <span title=\"e: 2.799%(.*)title=\"z: 0.045%" page)))

(def search-result (sort (distinct (re-seq #"[a-z]: [0-9][0-9]?.[0-9][0-9][0-9]%" the-line))))

(def numbers
  (->> search-result
       (map #(subs % 3 (dec (count %))))
       (map utils/cast-to-float)
       (map #(/ % 100.0))))

(def first-letter-frequencies (zipmap utils/alphabet-lower numbers))
