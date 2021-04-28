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

(defn assert-valid-new-position! [board {:keys [x y] :as new-player-position}]
(l.board/is-valid? board x y new-player-position))


(defn update-state [movement]
  (let [board (get-board)]
    (->> (l.board/move->new-position movement)
         (assert-valid-new-position! board)
         (l.board/move board)
         db.board/upsert!)))