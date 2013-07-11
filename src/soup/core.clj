(ns soup.core
  (require [soup.db :as db]))

(defn initialize []
  (db/load-prod-db))
