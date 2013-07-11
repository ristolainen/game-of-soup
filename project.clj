(defproject soup "0.1.0-SNAPSHOT"
  :description "The Game Of Soup"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.logging "0.2.6"]
                 [clj-time "0.5.1"]
                 [compojure "1.1.5"]
                 [ring-middleware-format "0.3.0"]
                 [org.clojure/java.jdbc "0.3.0-alpha4"]
                 [clj-dbcp "0.8.1"]
                 [mysql/mysql-connector-java "5.1.25"]]
  :profiles {:dev {:resource-paths ["test-resources"]
                   :dependencies [[com.h2database/h2 "1.3.172"]]}}
  :plugins [[lein-ring "0.8.6"]]
  :ring {:handler soup.web/app
         :port 8080
         :nrepl {:start? true :port 18080}})
