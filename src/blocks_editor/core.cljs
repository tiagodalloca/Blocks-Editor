(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]

<<<<<<< 22870d996252d70a8fd5e5eda7f553d63abb2e75
            [Blockly :as b]))
=======
            [Blockly :as b]
            [JQuery])
  (:import [Blockly
            Blocks
            Msg.en]))
>>>>>>> It's working with menu

(defn get-e [s]
  (js/document.getElementById s))

(defn ^:export init!
  [settings]
  (reagent/render
   [v/ui]
   (get-e "app"))
  (doto (.ajax js/$ (clj->js  {:url "assets/xml/toolbox.xml"}))
    (.done #(b/inject (get-e "blocklyDiv")
                      (clj->js {:toolbox %})))))

