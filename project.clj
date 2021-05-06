(defproject fruit-loop "0.1.0-SNAPSHOT"
  :description "Game to emulate the snake game"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [io.pedestal/pedestal.service "0.5.8"]
                 [io.pedestal/pedestal.jetty "0.5.8"]
                 [cheshire/cheshire "5.10.0"]
                 [org.clojure/data.json "2.2.1"]]
  :resource-paths ["config" "resources"]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "fruit-loop.main-dev-web/-main"]}
                   :dependencies [[io.pedestal/pedestal.service-tools "0.5.8"]]
                   :main fruit-loop.main-dev-web}
             :cli {:main fruit-loop.main-dev-cli}}
  :aliases {:cli ["with-profile" "cli" "run"]}
  :repl-options {:init-ns fruit-loop.dev})
