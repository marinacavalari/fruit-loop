(ns fruit-loop.logic.board)

(defn position [width height]
  {:x (rand-int width)
   :y (rand-int height)})

(defn ->board [width height]
  {:width width
    :height height
    :fruit-position (position width height)
    :player-position (position width height)
    :score 0})

(defn get-state [{:keys [board]}]
  (select-keys board [:score :player-position :fruit-position]))

(defn move->new-position [move player-position]
  (case move
    "rigth" (update player-position :x inc)
    "left" (update player-position :x dec)
    "up" (update player-position :y inc)
    "down" (update player-position :y dec)))

(defn player-position->fruit-position
  [new-player-position {:keys [fruit-position width height]}]
  (if (= new-player-position fruit-position)
    (position width height)
    fruit-position))

(defn positions->score [new-player-position {:keys [fruit-position score]}]
  (if (= new-player-position fruit-position)
    (inc score)
    score))

(defn won? [{:keys [score]}]
  (= 3 score))

(defn move [{:keys [board player-position score] :as game} move]
  (let [new-player-position (move->new-position move player-position)
        new-fruit-position (player-position->fruit-position new-player-position game)]
    (-> board
        (assoc :width 
               :height
               :fruit-position new-fruit-position
               :player-position new-player-position
               :score (positions->score new-player-position new-fruit-position)))))
