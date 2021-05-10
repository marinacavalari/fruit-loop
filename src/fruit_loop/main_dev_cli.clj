(ns fruit-loop.main-dev-cli
  (:require [fruit-loop.controllers.board :as c.board]
            [clojure.data.json :as json]))

(defn- safe-parse-json [string]
  (try
    (-> string
        (json/read-str :key-fn keyword))
    (catch Exception _
      nil)))

(defn create [input]
  (try
    (c.board/create! input)
    (-> {:state :success
         :violations []}
        json/write-str
        println)
    (catch clojure.lang.ExceptionInfo e
      (-> {:state :failed
           :violations [(-> e ex-data :violations)]}
          json/write-str
          println))))

(defn movement [input]
  (try
    (c.board/move input)
  (catch clojure.lang.ExceptionInfo e
    (-> {:state :failed
         :violations [(-> e ex-data :violations)]}
        json/write-str
        println))))

(defn -main [& _args]
  (loop []
    (let [{:keys [board player] :as input} (safe-parse-json (read-line))]
      (cond
        board
        (create input)
        player
        (movement input)
        :else
        (println "invalid command"))
      (recur))))