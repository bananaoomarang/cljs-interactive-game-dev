(ns pixi-tutorial-1.game
  (:require
   [pixi-engine.wrapper :as game]
   [pixi-engine.core :as pixi]))

(def sprites {:bird "/images/chickadee.png"})
(def bird nil)

(defn setup [app]
  (let [bird (pixi/create-sprite app "bird" {:scale {:x 0.5 :y 0.5}})]
    (game/add-sprite! app bird)))

(game/init!
 "#pixi-app-1"
 {:width 400
  :height 400
  :sprites sprites
  :setup setup})
