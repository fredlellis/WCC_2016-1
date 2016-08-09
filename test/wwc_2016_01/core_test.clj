(ns wwc-2016-01.core-test
  (:require [clojure.test :refer :all]
            [clojure.math.combinatorics :as combo]
            [wwc-2016-01.core :refer :all]))


(defn wwwc-01-filter [max array]
  (first (apply sorted-set-by (fn [x y] (> (reduce + x) (reduce + y)))
                (filter (fn [item] (< (reduce + item) max))
                        (clojure.math.combinatorics/subsets array)))))


(defn wwwc-01-loop [x array]
  (loop
    [combinacoes (clojure.math.combinatorics/subsets array)
     array-maior-menores []]
    (if (empty? combinacoes)
      (vec array-maior-menores)
      (recur (rest combinacoes) (if (and (< (reduce + (first combinacoes)) x) (> (reduce + (first combinacoes)) (reduce + array-maior-menores)))
                                  (first combinacoes)
                                  array-maior-menores)))))

(deftest a-test
  (testing "Maior menor"
    (is (= (wwwc-01-loop 29 [2, 8, 3, 9, 11]) [8 9 11]))
    (is (= (wwwc-01-filter 29 [2, 8, 3, 9, 11]) [8 9 11]))))


(do
  (time (wwwc-01-filter 29 [2, 8, 3, 9, 11]))
  (time (wwwc-01-loop 29 [2, 8, 3, 9, 11])))