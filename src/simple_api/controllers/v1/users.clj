(ns simple_api.controllers.v1.users
  (:require [simple_api.db :as db])
  ;(:require [simple_api.data.v1.users :as users])
  (:use korma.core))

(defentity users
  (pk :id))

(defn all
  []
  ;{:users (db/query users/all {})})
  {:users (select users)})

(defn show
  [id]
  {:user (first (select users (where {:id id})))})

(defn create
  [name]
  {:user (insert users (values {:name name}))})
