(defproject simple_api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [clj-time "0.9.0"] ; required due to bug in lein-ring
                 [metosin/compojure-api "0.22.0"]
                 [yesql "0.5.2"]
                 [environ "1.0.2"]
                 [ragtime "0.5.2"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.2"]
                 [korma "0.4.2"]
                 [heroku-database-url-to-jdbc "0.2.2"]]
  :ring {:handler simple_api.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                                  [cheshire "5.3.1"]
                                  [ring-mock "0.1.5"]]
                   :plugins [[lein-ring "0.9.6"]
                             [com.jakemccrary/lein-test-refresh "0.12.0"]
                             [lein-environ "1.0.2"]
                             [lein-pprint "1.1.1"]]
                   :env {:database-url "postgresql://Jeiwan:@localhost:5432/simple_api_dev?user=Jeiwan"
                         :database-force-transactions false}}
             :test {:env {:database-url "postgresql://Jeiwan:@localhost:5432/simple_api_test?user=Jeiwan"
                          :database-force-transactions true}}})
