(ns blocks-editor.async-events
  (:require [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]]
            [Blockly.Msg :as bmsg])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(def w-load-c (chan))
(def w-load-mc (mult w-load-c))
(defn subscribe-on-wload []
  (let  [c (chan)]
    (tap w-load-mc c)
    c))

(set! (.-onload js/window) #(do (go (>! w-load-c %&))))

;; (let [c (subscribe-on-wload)]
;;   (go (print (<! c))))

;; (put! w-load-c "asldf")


