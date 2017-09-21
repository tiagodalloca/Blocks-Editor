(ns cljs.user
  (:require [re-frame.core :as rf]
            [figwheel.client :as fw :include-macros true]
            [blocks-editor.async-events :refer [w-load-c]]
            [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(enable-console-print!)

(fw/watch-and-reload :websocket-url
                     "ws://localhost:3449/figwheel-ws")

(require '[blocks-editor.init])

(defn reload [] (go (>! w-load-c [])))

