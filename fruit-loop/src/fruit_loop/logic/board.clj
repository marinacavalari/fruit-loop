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

