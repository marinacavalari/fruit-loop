(ns fruit-loop.db.board)

(def board-db (atom {}))

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(defn upsert! [{:board/keys [id] :as board}]
  (if id
    (swap! board-db assoc id board)
    (let [new-id (uuid)]
      (swap! board-db assoc new-id (assoc board :board/id new-id)))))




(defn get-all-boards [] @board-db)

(defn get-by-id [id]
  (get @board-db id))