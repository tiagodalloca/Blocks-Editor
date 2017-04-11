(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]

            [Blockly :as b])
  (:import [Blockly
            Blocks
            Msg.en]))

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

