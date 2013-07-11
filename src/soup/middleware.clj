(ns soup.middleware
  (:require [compojure.core :refer :all]
            [clojure.string :refer (upper-case)]
            [clojure.tools.logging :as logging]))

(defn ^:dynamic log [& s]
  (logging/debug (apply str (interpose " " s))))

(defn wrap-request-logger [handler]
  (fn [request]
    (let [{:keys [remote-addr request-method uri]} request]
      (log remote-addr (upper-case (name request-method)) uri)
      (handler request))))

(defn wrap-response-logger [handler]
  (fn [request]
    (let [response (handler request)
          {:keys [remote-addr request-method uri]} request
          {status :status} response]
      (log remote-addr (upper-case (name request-method)) uri "->" status)
      response)))
