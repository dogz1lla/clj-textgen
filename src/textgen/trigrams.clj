(ns textgen.trigrams
  (:require [clojure.string :as s]
            [textgen.utils :as utils]))

;; P. Norvig's article: http://norvig.com/mayzner.html 
;; data from https://norvig.com/ngrams/count_3l.txt
(def raw-data (slurp "https://norvig.com/ngrams/count_3l.txt"))
(def data (->> raw-data
               (s/split-lines)
               (map #(s/split % #"\t"))
               (map (fn [[f s]] [f (utils/cast-to-float s)]))
               (into {})))

(defn generate-trigram-freq-distr
  []
  (let [norm (reduce + (vals data))]
    (->> data
         (map (fn [[k v]] [k (/ v norm)]))
         (into {}))))

(def trigram-frequencies (generate-trigram-freq-distr))

(comment
  (->> trigram-frequencies (sort-by second) (take-last 5)))
