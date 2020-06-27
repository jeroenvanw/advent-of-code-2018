(ns advent-of-code-2018.8a
  (:require [advent-of-code-2018.input :as input]))

(count (input/raw 8))

(defn ->numbers [input]
  (->> input
      (re-seq #"\d+")
      (map read-string)))

(defn node [num-child-nodes num-metadata-entries child-nodes metadata-entries]
  {:header {:num-child-nodes num-child-nodes
            :num-metadata-entries num-metadata-entries}
   :child-nodes child-nodes
   :metadata-entries metadata-entries})

(defn nodes
  "Returns a seq of the nodes in the tree rooted at `root` by perform in-order tree walk."
  [root]
  )

(defn initial-node-parsing-process [numbers]
  {:remaining-numbers numbers})

(defn tree
  [node-parsing-processes]
  (let [process (first node-parsing-processes)]
    (cond
      (nil? (:header process))
      
      (let [[qc qm & r] (:remaining-numbers process)]
        (recur (-> (rest node-parsing-processes)
                   (conj (-> process
                             (assoc :remaining-numbers r)
                             (assoc :header {:quantity-children qc
                                             :quantity-metadata qm})
                             (assoc :children []))))))

      

      (< (count (:children process))
         (get-in process [:header :quantity-children]))

      (recur (-> node-parsing-processes
                 (conj (initial-node-parsing-process (:remaining-numbers process)))))

      

      (nil? (:metadata-entries process))

      (let [qm (get-in process [:header :quantity-metadata])
            entries (take qm (:remaining-numbers process))]
        (recur (-> (rest node-parsing-processes)
                   (conj (-> process
                             (assoc :metadata-entries entries)
                             (update :remaining-numbers (partial drop qm)))))))

      

      (:metadata-entries process)

      (let [node (dissoc process :remaining-numbers)]
        (if-let [parent (first (rest node-parsing-processes))]
          (recur (-> (rest (rest node-parsing-processes))
                     (conj (-> parent
                               (update :children conj node)
                               (assoc :remaining-numbers (:remaining-numbers process))))))
          node)))))

(tree (list (initial-node-parsing-process [2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2])))

2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2





(defn my-range [low high]
  (lazy-seq
   (if (>= low high)
     ()
     (do (prn :a)
         (cons low (my-range (inc low) high))))))


(defn rangepr [min max]
  (lazy-seq
   (println min)
   (when (< min max)
     (cons min (rangepr (inc min) max)))))

(rangepr 0 3)

{:meta [1 1 2]
 :children [{:meta 10 11 12
             :children []}
            {:meta 2
             :children [{:meta 99
                         :children []}]}]}

(->> (input/raw 8)
     ->numbers
     tree
     nodes
     (map :metadata)
     (reduce +))
