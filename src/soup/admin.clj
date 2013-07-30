(ns soup.admin
  (:require [soup.db :as db]
            [clojure.tools.logging :refer (info)]))

(defn insert-user! [user]
  (info str "Creating new user:" user)
  (db/insert-user! user))

(defn update-user! [existing-user new-user]
  (info "Updating existing user:" existing-user "with" new-user)
  (db/update-user! (:id new-user) new-user))

(defn save-user! [{id :id :as user}]
  (if-let [existing-user (db/fetch-user id)]
    (update-user! existing-user user)
    (insert-user! user)))

(defn get-user [id]
  (info (str "Get user with id '" id "'"))
  (or (db/fetch-user id) {}))
