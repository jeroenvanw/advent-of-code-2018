(ns advent-of-code-2018.2
  (:require [advent-of-code-2018.input :as input]))

(def box-ids (input/lines 2))

(defn contains-letter-n-times? [str n]
  (let [characters (set str)
        character-map (zipmap characters (repeat (count characters) 0))
        increase-count (fn [character-map character] (update character-map character inc))
        character-counts (reduce increase-count character-map str)]
    (contains? (set (vals character-counts)) n)))

(defn count-box-ids [n]
  (count (filter #(contains-letter-n-times? % n) box-ids)))

(* (count-box-ids 2) (count-box-ids 3))
