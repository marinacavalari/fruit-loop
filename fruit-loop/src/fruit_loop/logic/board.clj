(ns fruit-loop.logic.board)

(defn fruit-position [width height]
  {:x (rand-int width)
   :y (rand-int height)})

(defn ->board [width height]
  #:board{:width width
          :height height
          :fruit-position (fruit-position width height)})

