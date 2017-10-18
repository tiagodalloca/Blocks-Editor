(ns blocks-editor.async-events
  (:require [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]]
            [blocks-editor.utils :refer [electron]]
            [Blockly.Msg :as bmsg])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce $ (js* "$"))

(def w-load-c (chan))
(def w-load-mc (mult w-load-c))
(defn subscribe-on-wload []
  (let  [c (chan)]
    (tap w-load-mc c)
    c))

(set! (.-onload js/window) #(do (go (>! w-load-c %&))))

(.keypress
 ($ js/document)
 (fn [e] 
   (when (-> e .-which (= 9)) ;; CTRL-i
     (-> electron .-remote .getCurrentWindow .toggleDevTools))))

