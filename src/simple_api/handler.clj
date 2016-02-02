(ns simple_api.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [schema.core :as s]
            [simple_api.controllers.v1.users :as c_users]))

(s/defschema Message {:message s/Str})
(s/defschema User {:user {:id s/Int :name s/Str}})
(s/defschema Users {:users [{:id s/Int :name s/Str}]})

(defapi app
  (swagger-ui "/swagger-ui")
  (swagger-docs
    {:info {:title "Simple_api"
            :description "Compojure Api example"}
     :tags [{:name "hello", :description "says hello in Finnish"}
            {:name "users", :description "users-related stuff"}]})

  (context* "/hello" []
    :tags ["hello"]
    (GET* "/" []
      :return Message
      :query-params [name :- String]
      :summary "say hello"
      (ok {:message (str "Terve, " name)})))

  (context* "/users" []
    :tags ["users"]
    (GET* "/" []
      :return Users
      :summary "get all users"
      (ok (c_users/all)))
    (GET* "/:id" []
      :return User
      :path-params [id :- s/Int]
      :summary "gets user by id"
      (ok (c_users/show id)))
    (POST* "/" []
      :return User
      :form-params [name :- s/Str]
      :summary "create a new user"
      (ok (c_users/create name)))))
