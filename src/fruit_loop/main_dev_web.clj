(ns fruit-loop.main-dev-web
  (:require [io.pedestal.http :as server]
            [fruit-loop.handlers.routes :as h.routes]))

(defn -main [& _args]
  (-> h.routes/dev-server-config
      server/default-interceptors
      server/dev-interceptors
      server/create-server
      server/start))
