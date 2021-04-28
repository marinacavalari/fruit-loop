(ns fruit-loop.dev
  (:require [io.pedestal.http :as server]
            [fruit-loop.handlers.routes :as h.routes]))

(println "\nCreating your [DEV] server...")
(-> h.routes/dev-server-config
    server/default-interceptors
    server/dev-interceptors
    server/create-server
    server/start)