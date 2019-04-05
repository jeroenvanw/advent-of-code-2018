(ns advent-of-code-2018.4-2
  (:require [advent-of-code-2018.input :as input]
            [clojure.string :as string]
            [clojure.core.reducers :as r]))

(def records (sort (input/lines 4)))

;; split records into vec of shifts
;; shift is vector of begin of shift record and corresponding sleep/wake records

(defn split-with_recursion [result under-construction predicate coll]
  (if (empty? coll)
    (conj result under-construction)
    (if (predicate (first coll))
      (recur (conj result under-construction) [(first coll)] predicate (rest coll))
      (recur result (conj under-construction (first coll)) predicate (rest coll)))))

(defn split-with [predicate coll]
  (if (empty? coll)
    coll
    (split-with_recursion [] [(first coll)] predicate (rest coll))))

(defn split-by-inclusion [coll str]
  (split-with #(string/includes? % str) coll))

(def shifts (split-by-inclusion records "Guard"))

;; map shifts to registers: { guard-id {:total count :minutes {minute count}}}

(defn find-minutes "of timestamp in record, returns number" [record]
  (Integer/parseInt (second (re-find #":(?<m>.*)\]" record))))

(defn find-guard-id "in record, returns number" [record]
  (Integer/parseInt (second (re-find #"#(?<m>\d+)" record))))

(defn pair->minutes-asleep [[sleep-record wake-record]]
  (let [minute-asleep (find-minutes sleep-record)
        minute-awake (find-minutes wake-record)
        count-minutes-asleep (- minute-awake minute-asleep)]
    (map (partial + minute-asleep) (range count-minutes-asleep))))

(defn records->minutes-asleep [records]
  (let [pairs (split-by-inclusion records "sleep")]
  (reduce concat (map pair->minutes-asleep pairs))))

(defn ->register [shift]
  (let [guard-id (find-guard-id (first shift))
        minutes-asleep (records->minutes-asleep (rest shift))]
    { guard-id { :minutes (zipmap minutes-asleep (repeat 1))
                 :total (count minutes-asleep)}}))

(def registers (map ->register shifts))

;; combine all these registers (monoid!)

;; if guard-id occurs in only 1, just copy into
;; if a guard-id occurs in both, merge total and minutes maps
;; merging minutes maps means: if key occurs in only 1, just copy into
;; if key occurs in both, add values

;; find guard-id with highest total, find his minute with highest count

(defn combine-minute-maps [minute-map1 minute-map2]
  (let [total (+ (minute-map1 :total) (minute-map2 :total))
        minutes (merge-with + (minute-map1 :minutes) (minute-map2 :minutes))]
    {:total total :minutes minutes}))

(defn combine-registers
  ([] {})
  ([reg1 reg2]
    (merge-with combine-minute-maps reg1 reg2)))

(defn key-max-value "returns key in m for which (f value) is maximal" [f m]
  (key (apply max-key (fn [[_ v]] (f v)) m)))

(let [final-register (reduce combine-registers registers)
      sleepiest-guard-G-id (key-max-value #(% :total) final-register)
      G-minutes ((final-register sleepiest-guard-G-id) :minutes)
      G-highest-minute (key-max-value #(identity %) G-minutes)]
  (* sleepiest-guard-G-id G-highest-minute))
