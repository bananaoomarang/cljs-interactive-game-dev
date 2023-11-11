(ns pixi-engine.core
  (:require
   [goog.object :as gobj]))

(def PIXIApplication (.-Application js/PIXI))
(def Sprite (.-Sprite js/PIXI))
(def Assets (.-Assets js/PIXI))
(def Graphics (.-Graphics js/PIXI))

(defn create-application [config]
  (PIXIApplication. (clj->js config)))

(defn add-child! [container sprite]
  (. container addChild sprite))

(defn get-resource [name]
  (. Assets get name))

(defn create-sprite [app resource-key {:keys [position anchor rotation scale]}]
  (let [resource (get-resource resource-key)
        sprite (.from Sprite resource)]

    (when position
      (. (.-position sprite) set (:x position) (:y position)))

    (when anchor
      (. (.-anchor sprite) set (:x anchor) (:y anchor)))

    (when rotation
      (set! (.-rotation sprite) rotation))

    (when scale
      (. (.-scale sprite) set (:x scale) (:y scale)))

    sprite))

(defn create-graphics []
  (Graphics.))

(defn create-rectangle [w h color]
  (let [graphics (create-graphics)]
    (.beginFill graphics color)
    (.drawRect graphics 0 0 w h)
    (.endFill graphics)))
