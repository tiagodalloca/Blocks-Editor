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
  {:type "game_update"
   :lastDummyAlign0 "RIGHT"
   :message0 "%1 %2"
   :args0 ["UPDATE"
           {:type "field_variable", :name "state", :variable "state"}]
   :nextStatement nil
   :colour 30
   :tooltip "Receive the game state and updates it with an action"
   :helpUrl ""})

(def return-block
  {:type "game_return"
   :lastDummyAlign0 "RIGHT"
   :message0 "%1 %2"
   :args0 ["return action"
           {:type "input_value", :name "ACTION", :check "ActionBlock"}]
   :previousStatement nil
   :colour 30
   :tooltip "Return an action to be handled by the game"
   :helpUrl ""})

(def action-block
  {:type "game_action"
   :lastDummyAlign0 "RIGHT"
   :message0 "%1 %2"
   :args0 ["action"
           {:type "field_input", :name "ACTION_NAME", :text "walk", :spellcheck false}]
   :output "ActionBlock"
   :colour 250
   :tooltip "Something to do and its args"
   :helpUrl ""})

;; (defn def-blocks [bindings]
;;   (let [bs (partition 2 bindings)]
;;     (def bs bs)
;;     `(let [goog# (js* "goog")
;;            asdf#  (.require goog "Blockly.Blocks")
;;            bblocks# (js* "Blockly.Blocks")
;;            bsvg# (js* "Blockly.BlockSvg")]
;;        ~(map (fn [[s m]] `(set! (~(symbol (str ".-" s))
;;                                 bblocks#)
;;                                (clj->js ~m)))
;;              ~bs))))

;; (def-blocks '[hue {:asdf "asdfj"}])

;; bs


(defn init-blocks! []
  (defonce goog (js* "goog"))
  (.require goog "Blockly.Blocks")
  (defonce bblocks (js* "Blockly.Blocks"))
  (defonce bsvg (js* "Blockly.BlockSvg"))
  
  (set! (.-game_update bblocks)
        (clj->js
         {:init (fn []
                  (this-as this
                    (.jsonInit this
                               (clj->js update-block))))}))

  (set! (.-game_return bblocks)
        (clj->js
         {:init (fn []
                  (this-as this
                    (.jsonInit this
                               (clj->js return-block))))}))

  (set! (.-game_action bblocks)
        (clj->js
         {:init (fn []
                  (this-as this
                    (.jsonInit this
                               (clj->js action-block))))}))
  
  (set! (.-START_HAT bsvg) true))

(go (<! (subscribe-on-wload))
    (init-blocks!)
    (c/init!))

