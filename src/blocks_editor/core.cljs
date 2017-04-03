(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blockly.core :as b]
            [blocks-editor.view :as v]))

(defn get-e [s]
  (js/document.getElementById s))

(defn ^:export init!
  [settings]
  (reagent/render
   [v/ui]
   (get-e "app"))
  (.inject js/Blockly
           (get-e "blocklyDiv")
           (clj->js {:toolbox (get-e "toolbox")})))

