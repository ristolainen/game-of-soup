(ns soup.core-test
  (:require [clojure.test :refer :all]
            [soup.core :refer :all])
  (:import [soup.core Action Plate]))

(deftest test-apply-action
  (testing "poison"
    (let [action (Action. :poison {:pos 1})
          plates [(create-plate 1)]
          result (apply-action action plates)]
      (is (= 1 (count result)))
      (is (:poison (result 1)))))
  (testing "antidote"
    (let [action (Action. :antidote {:pos 2})
          plates [(create-plate 1) (assoc (create-plate 2) :poison true)]
          result (apply-action action plates)]
      (is (= 2 (count result)))
      (is (:antidote (result 2)))
      (is (:poison (result 2)))))
  (testing "switch"
    (let [action (Action. :switch {:from 1 :to 3})
          plates [(assoc (create-plate 1) :id :a) (assoc (create-plate 2) :id :b) (assoc (create-plate 3) :id :c)]
          result (apply-action action plates)]
      (is (= 3 (count result)))
      (is (= :c (:id (result 1))))
      (is (= :b (:id (result 2))))
      (is (= :a (:id (result 3)))))))


