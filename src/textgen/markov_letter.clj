(ns textgen.markov-letter
  (:require [textgen.utils :as utils]))

;; P. Norvig's article: http://norvig.com/mayzner.html 
;; data from https://blogs.sas.com/content/iml/2014/09/26/bigrams.html
(def data (slurp "https://blogs.sas.com/content/iml/files/2014/09/bigrams.txt"))

(defn get-keys
  []
  (->> data
       (re-seq #"[A-Z][A-Z]")
       (take 679)
       (take-last 676)))

(defn get-vals
  []
  (let [nums (re-seq #"[0-9]\.[0-9][0-9][0-9]\%" data)]
    (->> nums
         (map #(clojure.string/replace % #"%" ""))
         (map utils/cast-to-float)
         (map #(/ % 100.0)))))

(def table (zipmap (get-keys) (get-vals)))

(defn find-by-first-letter
  [letter]
  (filter #(re-seq (re-pattern (str "^" letter)) (key %)) table))

(defn make-transition-matrix
  []
    (->> utils/alphabet
      (map (fn [letter]
             (->> (find-by-first-letter letter)
                  (sort)
                  (map last)
                  (into []))))
      (into [])))

(defn normalize-rows
  [matrix]
  (->> matrix
       (map (fn [row]
              (let [norm (reduce + row)]
                (map #(/ % norm) row))))))

(def transition-matrix (normalize-rows (make-transition-matrix)))

(def transition-matrix-map (zipmap utils/alphabet-lower (map #(zipmap utils/alphabet-lower %) transition-matrix)))
