(ns advent-of-code-2018.6
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

;; ([x y] ...)
(def input
  (let [lines (input/lines 6)
        ->coordinate (fn [line] (vec (map #(Integer/parseInt %) (string/split line #", "))))]
    (map ->coordinate (take (min 15 (count lines)) lines))))

(defn x [[x y]] x)

(defn y [[x y]] y)
