(ns fruit-loop.logic.board 
  (:require [clojure.string :as string]))

(defn position [width height]
  {:x (rand-int width)
   :y (rand-int height)})

(defn ->board [width height]
  {:width width
    :height height
    :fruit-position (position width height)
    :player-position (position width height)
    :score 0})

(defn get-state [board]
  (select-keys board [:score :player-position :fruit-position]))

(defn move->new-position [move {:keys [player-position]}]
  (println "------->" move player-position)
  (case move
    "right" (update player-position :x inc)
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


(defn move [board new-player-position]
  (let [new-fruit-position (player-position->fruit-position new-player-position board)]
    (-> board
        (assoc :width 
               :height
               :fruit-position new-fruit-position
               :player-position new-player-position
               :score (positions->score new-player-position board)))))

(defn valid? [board {:keys [x y]}]
  (or (< x 0)
      (< y 0)
      (>= x (-> board :width))
      (>= y (-> board :height))))

(defn display [board]
  (let [display-width (+ 2 (:width board))
        display-height (+ 2 (:height board))
        player-x (-> board :player-position :x)
        player-y (-> board :player-position :y)
        fruit-x (-> board :fruit-position :x)
        fruit-y (-> board :fruit-position :y)]
    (->> (for [y (range display-height)
               x (range display-width)]
           (cond
             (and (= x player-x)
                  (= y player-y))
             "o"

             (and (= x fruit-x)
                  (= y fruit-y))
             "x"

             (or (= x 0)
                 (= (inc x) display-width))
             "|"

             (or (= y 0)
                 (= (inc y) display-height))
             "-"

             :else " "))
         (partition display-width)
         (mapv #(clojure.string/join "" %))
         (string/join "\n")
         )))