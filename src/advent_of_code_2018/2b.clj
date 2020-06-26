(ns advent-of-code-2018.2b
  (:require [advent-of-code-2018.input :as input]))

(def box-ids (input/lines 2))

(defn count-differ [ids]
  (-> (apply map (fn [& chars]
                   (apply = chars))
             ids)
      frequencies
      (get false)))

(defn common [ids]
  (->> ids
       (apply interleave)
       (partition (count ids))
       (filter #(apply = %))
       (map first)
       (apply str)))

(comment

  (->> (for [id1 box-ids
             id2 box-ids]
         [id1 id2])
       (filter #(= 1
                   (count-differ %)))
       first
       common)

  1)


