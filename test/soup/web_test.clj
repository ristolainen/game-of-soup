(ns soup.web-test
  (:require [clojure.test :refer :all]
            [soup.web :refer :all]
            [ring.mock.request :refer :all]))

(deftest test-routes
  (testing "root"
    (let [response (api-routes (request :get "/"))]
      (is (= 200 (:status response)))
      (is (contains? (:body response) :version))))
  (testing "api"
    (let [response (api-routes (request :get "/api"))]
      (is (= 200 (:status response)))
      (is (contains? (:body response) :api-version)))
    (let [response (api-routes (request :put "/api"))]
      (is (= 405 (:status response)))
      (is (nil? (:body response)))))
  (testing "not found"
    (let [response (api-routes (request :get "/doesnotexist"))]
      (is (= 404 (:status response))))))
