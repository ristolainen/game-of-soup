(ns soup.db
  (:require [clojure.tools.logging :refer (info)]
   [clj-dbcp.core :as dbcp]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(def ^:dynamic *db*)
(def pool-settings {:init-size 1 :max-idle 2 :max-active 5})

(defn create-dbcp [spec settings]
  (let [options (merge spec settings)]
    (info (str "Creating DBCP: " options))
    {:datasource (dbcp/make-datasource options)}))

(def prod-db
  {:adapter :mysql
   :host "localhost"
   :database "soup"
   :username "root"
   :password "admin"})

(defn load-prod-db []
  (alter-var-root #'*db* (constantly (create-dbcp prod-db pool-settings))))

(defn insert-user! [rec]
  (jdbc/insert! *db* :user rec))

(defn fetch-user [id]
  (let [q (sql/select * :user (sql/where {:id id}))]
    (first (jdbc/query *db* q))))
