;(ns simple_api.db
  ;(:require [environ.core :refer [env]])
  ;(:require [ragtime.repl :as ragtime])
  ;(:require [ragtime.jdbc :as rag_jdbc])
  ;(:require [jdbc.pool.c3p0 :as pool])
  ;(:require [clojure.java.jdbc :as jdbc]))

;;(def db-spec {:connection-uri (env :database-url)})

;(def db-spec
  ;(pool/make-datasource-spec {:connection-uri (env :database-url)}))

;(def migration-spec
  ;{:datastore (rag_jdbc/sql-database db-spec)
   ;:migrations (rag_jdbc/load-resources "migrations")})

;(defn query
  ;[yesql-query vars conn]
  ;(println yesql-query vars)
  ;(if (env :database-force-transactions)
    ;;(jdbc/with-db-transaction [db db-spec]
      ;;(jdbc/db-set-rollback-only! db)
      ;;(yesql-query vars {:connection db}))
    ;(yesql-query vars {:conenction conn})
    ;(yesql-query vars)))


(ns simple_api.db
  (:require [environ.core :refer [env]
            [ragtime.repl :as ragtime]
            [ragtime.jdbc :as rag_jdbc]])
  (:use korma.db))

;(def db-spec {:connection-uri (env :database-url)})

;(def migration-spec
  ;{:datastore (rag_jdbc/sql-database db-spec)
   ;:migrations (rag_jdbc/load-resources "migrations")})

;(defn migrate
  ;[]
  ;(ragtime/migrate migration-spec))

(defn- create-uri [url] (java.net.URI. url))

(defn- parse-username-and-password [db-uri]
  (clojure.string/split (.getUserInfo db-uri) #":"))

(defn- subname [db-uri]
  (format "//%s:%s%s" (.getHost db-uri) (.getPort db-uri) (.getPath db-uri)))

(defn korma-connection-map
  "Converts DATABASE_URL to a map that you can pass to Korma's
  defdb fn"
  [database-url]
  (let [db-uri (create-uri database-url)
        [username password] (parse-username-and-password db-uri)]
    {:classname "org.postgresql.Driver"
     :subprotocol "postgresql"
     :user username
     :password password
     :subname (subname db-uri)}))

(defdb db (postgres (korma-connection-map (env :database-url))))
