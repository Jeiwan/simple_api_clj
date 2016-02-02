(ns simple_api.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [simple_api.handler :refer :all]
            [ring.mock.request :as mock]
            [clojure.java.jdbc :as jdbc]
            [korma.db :as db]
            [korma.core :as query]
            [simple_api.controllers.v1.users :refer [users]]))

(defn clear
  [tests]
  (db/transaction
    (tests)
    (db/rollback)))

(use-fixtures :each clear)

(defn create-user
  [name]
  (query/insert users
                (query/values {:name name})))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest a-test

  (testing "Test GET request to /hello?name={a-name} returns expected response"
    (let [response (app (-> (mock/request :get  "/hello?name=Stranger")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:message body) "Terve, Stranger"))))

  (testing "GET /users"
    (let [test-user-1 (create-user "Ivan")
          test-user-2 (create-user "Juan")
          response (app (-> (mock/request :get "/users")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= body {:users [{:id (:id test-user-1) :name "Ivan"} {:id (:id test-user-2) :name "Juan"}]}))))

  (testing "GET /users/:id"
    (let [test-user (create-user "Sally")
          response (app (-> (mock/request :get (str "/users/" (:id test-user)))))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= body {:user {:id (:id test-user) :name (:name test-user)}}))))

  (testing "POST /users"
    (let [user-data {:name "Pedro"}
          response (app (-> (mock/request :post  "/users" user-data)))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (contains? body :user))
      (is (contains? (:user body) :id))
      (is (= (:name (:user body)) (:name user-data))))))
