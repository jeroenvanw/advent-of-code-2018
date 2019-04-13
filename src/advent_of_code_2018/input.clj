(ns advent-of-code-2018.input
  (:require [clojure.java.io :as io]))

(defn raw [n]
  (slurp (str "resources/input/" n ".txt")))

(defn lines [n]
  (clojure.string/split-lines (raw n)))
