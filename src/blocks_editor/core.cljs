(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]

            [Blockly :as b])  
  (:import [Blockly.Blocks
            loops logic procedures math texts variables lists colour]
           [Blockly.Msg en]))

(def workspace (atom nil))

(defn ^:export init!
  [settings]
  (reagent/render
   [v/ui]
   (-> "#app" js/$ (aget 0))) 
  (doto (.ajax js/$ (clj->js  {:url "assets/xml/toolbox.xml"}))
    (.done #(do (set! workspace (-> "#blocklyDiv" js/$ (aget 0)
                                    (b/inject
                                     (clj->js {:toolbox %}))))
                (rf/dispatch [:init-db {}])))))

