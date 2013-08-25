(ns soup.core
  (require [soup.db :as db]))

(defn initialize []
  (db/load-prod-db))

(defrecord Plate [table-pos poison glass antidote])

(defrecord Action [type action-data])

(defrecord Player [id user table-pos])

(defn create-plate [table-pos]
  (Plate. table-pos false 0 false))

(defn index-plates [plates]
  (into {} (map (fn [[k v]] [k (first v)]) 
                (group-by :table-pos plates))))

(defn add-poison [plate]
  (assoc plate :poison true))

(defn add-antidote [plate]
  (assoc plate :antidote true))

(defn add-glass [plate]
  (update-in plate [:glass] inc))

(defn switch-plates [plates from to]
  (assoc plates from (plates to) to (plates from)))

(defmulti act :type)

(defmethod act :poison [action plates]
  (let [pos (-> action :action-data :pos)
        plate (plates pos)]
    (assoc plates pos (add-poison plate))))

(defmethod act :antidote [action plates]
  (let [pos (-> action :action-data :pos)
        plate (plates pos)]
    (assoc plates pos (add-antidote plate))))

(defmethod act :glass [action plates]
  (let [pos (-> action :action-data :pos)
        plate (plates pos)]
    (assoc plates pos (add-glass plate))))

(defmethod act :switch [action plates]
  (let [{from :from to :to} (:action-data action)]
    (switch-plates plates from to)))

(defn apply-action [action plates]
  (let [indexed-plates (index-plates plates)]
    (act action indexed-plates)))
