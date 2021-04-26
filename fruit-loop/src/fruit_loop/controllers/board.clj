(ns fruit-loop.controllers.board
  (:require [fruit-loop.logic.board :as l.board]
            [fruit-loop.db.board :as db.board]))

(defn create [width height]
  (-> (l.board/->board width height)
      db.board/upsert!))