(ns soup.web
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :refer :all]
            [ring.middleware.format-response 
             :refer (wrap-restful-response)]
            [clj-time.core :refer (now)]
            [clj-time.format :refer (unparse formatter)]))

(def server-time-formatter (formatter "yyyy-MM-dd HH:mm:ss.SSS"))

(defn get-server-info []
  {:name "The Game Of Soup Server"
   :version "0.1"
   :server-time (unparse server-time-formatter (now))})

(defroutes api-routes
  (context "/" []
           (GET "/" []
                (response (get-server-info))))
  (context "/api" []
           (GET "/" []
                (response {:api-version "0.1"})))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> (handler/api api-routes)
      (wrap-restful-response)))

