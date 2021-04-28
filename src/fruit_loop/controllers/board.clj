(ns fruit-loop.controllers.board
  (:require [fruit-loop.logic.board :as l.board]
            [fruit-loop.db.board :as db.board]))

(defn create [width height]
  (-> (l.board/->board width height)
      db.board/upsert!))

(defn delete []
  (db.board/delete!))

(defn state []
  (->> (db.board/get-board)
       l.board/get-state))

(defn get-board []
  (db.board/get-board))

(defn update-state [board move]
  (-> board
       (l.board/move move)
       db.board/upsert!))
