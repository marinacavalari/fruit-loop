(ns fruit-loop.handlers.routes
  (:require [io.pedestal.http :as server]
            [io.pedestal.http.body-params :as body-params]
            [io.pedestal.http.route :as route]
            [fruit-loop.controllers.board :as c.board]
            [clojure.data.json :as json]))

(def common-interceptors
  [(body-params/body-params)])

(defn- create-board [{board :json-params}]
  (c.board/create! board)
  {:status 201
   :headers {"Content-Type" "application/json"}
   :body {}})

(defn- delete-board [_]
  (c.board/delete)
  {:status 200
   :body {}})

(defn- get-board [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (c.board/get-board)})

(defn- get-state [_]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/write-str (c.board/state))})

(defn- move-player [{{:keys [movement]} :path-params}]
  (c.board/move movement)
  {:status 200
   :body {}})

(defn- display [_]
  {:status 200
   :body (c.board/display-board)})

(defn routes []
  #{["/game" :post (conj common-interceptors create-board) :route-name :create-board]
    ["/game" :delete (conj common-interceptors delete-board) :route-name :delete-board]
    ["/board" :get (conj common-interceptors get-board) :route-name :get-board]
    ["/game/state" :get (conj common-interceptors get-state) :route-name :get-state]
    ["/player/move/:movement" :post (conj common-interceptors move-player) :route-name :move-player]
    ["/game/display" :get (conj common-interceptors display) :route-name :display]})


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