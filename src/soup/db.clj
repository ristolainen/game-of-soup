(ns soup.db
  (:require [clj-dbcp.core :as dbcp]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc.sql :as sql]))

(defn create-db-connection [spec]
  {:datasource (dbcp/make-datasource spec)})

(def prod-db
  {:adapter :mysql
   :host 'localhost
   :database 'soup
   :username "root"
   :password "admin"})

(def dev-db
  {:adapter :h2
   :target :memory
   :database "soup;INIT=RUNSCRIPT FROM 'resources/soup-h2.sql';MODE=MySQL"})

(defn insert-user [rec]
  (jdbc/with-connection dev-db
    (jdbc/insert-records :user rec)))

(defn fetch-user [id]
  (jdbc/with-connection dev-db
    (jdbc/with-query-results users 
      (vec (sql/select * :user (sql/where {:id id})))
      (first users))))
