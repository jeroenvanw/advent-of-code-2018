(ns advent-of-code-2018.2a
  (:require [advent-of-code-2018.input :as input]))

(apply * (for [n [2 3]]
           (->> (input/lines 2)
                (filter #(-> %
                             frequencies
                             vals
                             set
                             (contains? n)))
                count)))