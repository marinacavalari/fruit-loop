(ns fruit-loop.handlers.routes
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [fruit-loop.controllers.board :as c.board]))

(def common-interceptors
  [(body-params/body-params)])

(defn- create-board [{{:keys [width height]} :json-params}]
  (c.board/create width height)
  {:status 201
   :headers {"Content-Type" "application/json"}
   :body {}})


(defn routes []
  #{["/board" :post (conj common-interceptors create-board) :route-name :create-board]})

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