(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]
            [blocks-editor.view.styles]

            [Blockly :as b])
  (:import [Blockly.Blocks
            loops logic procedures math texts variables lists colour]
           [Blockly.Msg en]))

(defonce $ (js* "$"))

(defonce workspace (atom nil))

(defn ^:export init!
  []
  (rf/dispatch [:init-db])
  (reagent/render
   [v/ui]
   (-> "#app" $ (aget 0)))
  (doto (.ajax $ (clj->js  {:url "assets/xml/toolbox.xml"}))
    (.done #(do (set! workspace (-> "#blocklyDiv" $ (aget 0)
                                    (b/inject
                                     (clj->js {:toolbox %})))))))
  (doto ($ "[data-toggle=\"tooltip\"]")
    .tooltip))

