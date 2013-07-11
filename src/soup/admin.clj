(ns soup.admin
  (:require [soup.db :as db]
            [clojure.tools.logging :refer (info)]))

(defn new-user! [user]
  (info (str "Creating new user: " user))
  (db/insert-user! user))

(defn get-user [user-id]
  (info (str "Get user with id '" user-id "'"))
  (or (db/fetch-user user-id) {}))
