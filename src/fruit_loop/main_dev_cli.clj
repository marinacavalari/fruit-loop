(ns fruit-loop.main-dev-web
  (:require [fruit-loop.controllers.board :as c.board]
            [fruit-loop.logic.board :as l.board]
            [clojure.data.json :as json]))

(defn create [input]
  (let [board (-> input
                  (json/read-str :key-fn keyword)
                  (l.board/output))
        width (:width board)
        height (:height board)]
    (if (nil? board)
      board
      (c.board/create width height))
    (json/write-str (l.board/sucess))))

(defn movement [input]
  (let [move (-> input
                 (json/read-str :key-fn keyword)
                 l.board/movement-cli)]
    (if (nil? move)
      move
      (c.board/move move))
    (json/write-str (l.board/sucess))))

(defn -main [& args]
  (create args))