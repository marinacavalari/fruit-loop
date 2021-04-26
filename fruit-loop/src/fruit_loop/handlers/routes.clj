(ns fruit-loop.handlers.routes
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [cheshire.core :as json]))

(def common-interceptors
  [(body-params/body-params)])

(defn- create-board [request]
  {:status 201
   :headers {"Content-Type" "application/json"}
   :body (json/encode {:a (:json-params request)})})

(defn routes []
  #{["/" :post (conj common-interceptors create-board) :route-name :create-board]})

(def server-config
  {::server/port 8080
   ::server/type :jetty
   ::server/resource-path "/public"
   ::server/routes (route/expand-routes (routes))})

(def dev-server-config
  (merge server-config
         {:env :dev
          ::server/join? false
          ::server/routes #(route/expand-routes (routes))
          ::server/allowed-origins {:creds true :allowed-origins (constantly true)}
          ::server/secure-headers {:content-security-policy-settings {:object-src "'none'"}}}))