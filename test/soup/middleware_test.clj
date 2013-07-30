(ns soup.middleware-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [soup.middleware :refer :all]))

(defn create-msg [parts]
  (apply str (interpose " " parts)))

(deftest logger-middleware
  (testing "request logging"
    (let [req (request :get "/test")
          handler (wrap-request-logger identity)
          log-result (atom nil)]
      (binding [log (fn [& parts] (reset! log-result (create-msg parts)))]
        (handler req))
      (is (= "localhost GET /test" @log-result))))

  (testing "response logging"
    (let [req (request :get "/test")
          res-fn (constantly (hash-map :status 200))
          handler (wrap-response-logger res-fn)
          log-result (atom nil)]
      (binding [log (fn [& parts] (reset! log-result (create-msg parts)))]
        (handler req))
      (is (= "localhost GET /test -> 200" @log-result)))))

