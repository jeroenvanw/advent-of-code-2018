(ns advent-of-code-2018.3
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]))

(def claims (input/lines 3))

(defn inch-range [offset length]
  (map (partial + offset) (range length)))

(defn square-inches [left top width height]
  (for [x (inch-range left width)
        y (inch-range top height)]
    [x y]))

(defn claim-area [claim]
  (let [info (map string/trim (string/split (second (string/split claim #"@")) #":"))
        info-numbers (fn [select regex] (map load-string (string/split (select info) regex)))
        offsets (info-numbers first #",")
        lengths (info-numbers second #"x")
        left (first offsets)
        top (second offsets)
        width (first lengths)
        height (second lengths)]
    (square-inches left top width height)))

(let [square-inch-claims (reduce concat (map claim-area claims))
      square-inch-claim-frequencies (frequencies square-inch-claims)
      contested-square-inch-claims (filter #(> (second %) 1) square-inch-claim-frequencies)]
  (count contested-square-inch-claims)
;;square-inch-claim-frequencies
  )
