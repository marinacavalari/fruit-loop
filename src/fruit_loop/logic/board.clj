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
  (case move
    "right" (update player-position :x inc)
    "left" (update player-position :x dec)
    "up" (update player-position :y dec)
    "down" (update player-position :y inc)))

(defn player-position->fruit-position
  [new-player-position {:keys [fruit-position width height]}]
  (if (= new-player-position fruit-position)
    (position width height)
    fruit-position))

(defn positions->score [new-player-position {:keys [fruit-position score]}]
  (if (= new-player-position fruit-position)
    (inc score)
    score))

(defn move [board new-player-position]
  (let [new-fruit-position (player-position->fruit-position new-player-position board)]
    (-> board
        (assoc :fruit-position new-fruit-position
               :player-position new-player-position
               :score (positions->score new-player-position board)))))

(defn valid? [board {:keys [x y]}]
  (or (< x 0)
      (< y 0)
      (>= x (-> board :width))
      (>= y (-> board :height))))

(defn display [board]
  (println "---------->>>" (:width board))
  (let [display-width (+ 2 (:width board))
        display-height (+ 2 (:height board))
        player-x (-> board :player-position :x inc)
        player-y (-> board :player-position :y inc)
        fruit-x (-> board :fruit-position :x inc)
        fruit-y (-> board :fruit-position :y inc)]
    (->> (for [y1 (range display-height)
               x1 (range display-width)
               :let [x2 (inc x1)
                     y2 (inc y1)]]
           (cond
             (and (= x1 player-x)
                  (= y1 player-y))
             "o"

             (and (= x1 fruit-x)
                  (= y1 fruit-y))
             "x"

             (or (= x1 0)
                 (= x2 display-width))
             "|"

             (or (= y1 0)
                 (= y2 display-height))
             "-"

             :else " "))
         (partition display-width)
         (mapv #(clojure.string/join "" %))
         (string/join "\n"))))

(defn score [board]
  (if (= 3 (-> board :score))
    (str "WON!")
    (-> board :score)))

(defn screen [display score]
  (str display "\n\n Score: " score))
