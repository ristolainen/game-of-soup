(ns soup.web
  (:require [soup.admin :as admin]
            [soup.middleware :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.util.response :refer :all]
            [ring.middleware.format :refer (wrap-restful-format)]
            [clj-time.core :refer (now)]
            [clj-time.format :refer (unparse formatter)]
            [hiccup.core :refer :all]))

(def server-time-formatter (formatter "yyyy-MM-dd HH:mm:ss.SSS"))

(defn get-server-info []
  {:name "The Game Of Soup Server"
   :version "0.1"
   :server-time (unparse server-time-formatter (now))})

(defn main-page []
  (html [:html [:body 
                [:h1 "Game Of Soup"]
                [:img {:src "http://25.media.tumblr.com/tumblr_ln25q4EBa71qjo5ngo1_400.gif"}]
                [:br]
                [:a {:href "https://github.com/Ristolainen/game-of-soup"} "Game Of Soup on GitHub"]]]))

(defroutes api-routes
  (context "/" []
           (GET "/" []
                (response (main-page)))
           (ANY "/" []
                (-> (response nil) (status 405))))
  (context "/api" []
           (GET "/" []
                (response (get-server-info)))
           (GET "/version" []
                (response {:api-version "0.1"}))
           (GET "/user/:id" [id]
                (response (admin/get-user id)))
           (POST "/user" {params :params}
                 (response (admin/save-user! params)))
           (ANY "/" []
                (-> (response nil) (status 405))))
  (route/not-found "<h1>Page not found</h1>"))

(def app
  (-> (handler/api api-routes)
      (wrap-request-logger)
      (wrap-response-logger)
      (wrap-restful-format)))
