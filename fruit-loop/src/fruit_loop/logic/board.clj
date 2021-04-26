(ns fruit-loop.logic.board)

(defn position [width height]
  {:x (rand-int width)
   :y (rand-int height)})


(defn ->board [width height]
  #:board{:width width
          :height height
          :fruit-position (position width height)
          :player-position (position width height)
          :score 0
          :state :inactive})

(defn updating [old-board score state player-position]
  (assoc old-board
         :board/state state
         :board/score score
         :board/player-position player-position
         :board/fruit-position (position (get player-position :x)
                                         (get player-position :y))))