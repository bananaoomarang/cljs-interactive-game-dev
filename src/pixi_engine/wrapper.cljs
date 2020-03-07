(ns pixi-engine.wrapper
  (:require
   [pixi-engine.core :as pixi]))

(defn vec2 [x y]
  {:x x :y y})

(defn vec-x [v]
  (:x v))

(defn vec-y [v]
  (:y v))

(defn set-vec2
  ([v xy]
   (set-vec2 v xy xy))

  ([v x y]
   (assoc v :x x :y y)))

(defn vec2*
  ([v n]
   (vec2* v n n))

  ([v x y]
   (assoc v :x (* x (:x v)) :y (* y (:y v)))))

(defn get-app-element [selector]
  (. js/document querySelector selector))

(defn create-on-keydown [on-keydown]
  (fn [e]
    (. e preventDefault)

    (on-keydown e)))

(defn create-on-keyup [on-keyup]
  (fn [e]
    (. e preventDefault)

    (on-keyup e)))

(defn key-subscribe! [on-keydown on-keyup]
  (when on-keydown
    (. js/window addEventListener "keydown" (create-on-keydown on-keydown) false))

  (when on-keyup
    (. js/window addEventListener "keyup" (create-on-keyup on-keyup) false)))

(defn load-sprites! [app sprites]
  (doseq [[key val] sprites]
    (. (.-loader app) add (name key) val)))

(defn create-sprite [app resource-key opts]
  (pixi/create-sprite app resource-key opts))

(defn add-sprite! [app sprite]
  (pixi/add-child! (.-stage app) sprite))

(defn init!
  "Init stuff"

  [el-selector {:keys [width height sprites setup update on-keydown on-keyup]}]

  (let [app (pixi/create-application {:width width :height height})]
    (. (get-app-element el-selector) appendChild (.-view app))

    (load-sprites! app sprites)
    (key-subscribe! on-keydown on-keyup)

    (. (.-loader app) load
       (fn []
         (setup app)
         (. (.-ticker app) add update)))))
