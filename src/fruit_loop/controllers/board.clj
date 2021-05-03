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

(defn assert-valid-new-position! [board new-player-position]
  (if (l.board/valid? board new-player-position)
    (throw (ex-info "Invalid move" {:new-position new-player-position}))
    new-player-position))

(defn move [movement]
  (let [board (get-board)]
    (->> (l.board/move->new-position movement board)
         (assert-valid-new-position! board)
         (l.board/move board)
         db.board/upsert!))) 

(defn display-board []
  (let [board (get-board)
        display (l.board/display board)
        score (l.board/score board)]
    (l.board/screen display score)))