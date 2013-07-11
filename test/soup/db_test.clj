(ns soup.db-test
  (:require [clojure.test :refer :all]
            [soup.db :refer :all])
  (:import [java.util Date]))

(def sql-script "test-resources/soup-h2.sql")

(def test-db
  {:adapter :h2
   :target :memory
   :database (str "soup;INIT=RUNSCRIPT FROM '" sql-script "';MODE=MySQL")})

(defn create-test-dbcp []
  (let [pool-settings {:init-size 1 :max-idle 1 :max-active 1}] 
    (create-dbcp test-db pool-settings)))

(deftest save-and-fetch-user
  (testing "save and fetch user"
    (binding [*db* (create-test-dbcp)]
      (let [created-date (Date.)
            _ (insert-user! {:id "schneider" 
                             :name "Admiral von Schneider" 
                             :created created-date})
            {:keys [id name created]} (fetch-user "schneider")]
        (is (= "schneider" id))
        (is (= "Admiral von Schneider" name))
        (is (= created-date created))))))
