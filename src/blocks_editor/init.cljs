(ns blocks-editor.init
  (:require [blocks-editor.async-events :refer [subscribe-on-wload]]
            [blocks-editor.core :as c] 
            [blocks-editor.misc]
            [blocks-editor.events]
            
            [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))


(def update-block
  {:name "UPDATE"
   :type "update"
   :lastDummyAlign0 "RIGHT"
   :message0 "%1 %2"
   :args0 ["UPDATE"
           {:type "field_variable", :name "state", :variable "state"}]
   :nextStatement nil
   :colour 30
   :tooltip "Receive the game state and updates it with an action"
   :helpUrl ""})


(defn init-blocks! []
  (defonce goog (js* "goog"))
  (.require goog "Blockly.Blocks")
  (defonce bblocks (js* "Blockly.Blocks"))
  (defonce bsvg (js* "Blockly.BlockSvg"))
  
  (set! (.-update bblocks)
        (clj->js
         {:init (fn []
                  (this-as this
                    (.jsonInit this
                               (clj->js update-block))))}))
  (set! (.-START_HAT bsvg) true))

(go (<! (subscribe-on-wload))
    (init-blocks!)
    (c/init!))

