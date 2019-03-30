(ns advent-of-code-2018.input
  (:require [clojure.java.io :as io]))

(defn lines [n]
  (let [input (slurp (str "resources/input/" n ".txt"))]
    (clojure.string/split-lines input)))
