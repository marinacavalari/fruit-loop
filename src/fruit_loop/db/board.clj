(ns fruit-loop.db.board)

(def board-db (atom {}))

(defn upsert! [board]
  (reset! board-db board))

(defn get-board []
  @board-db)

(defn delete! []
  (reset! board-db {}))
