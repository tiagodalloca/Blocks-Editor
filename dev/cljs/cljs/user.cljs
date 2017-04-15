(ns cljs.user
  (:require [figwheel.client :as fw :include-macros true]
            [blocks-editor.core :as c]))

(enable-console-print!)

(fw/watch-and-reload :websocket-url
                     "ws://localhost:3449/figwheel-ws")

(set! (.-onload js/window) #(c/init! {}))

(defn reload
  []
  (js/alert "Reloaded")
  (c/init! {}))
