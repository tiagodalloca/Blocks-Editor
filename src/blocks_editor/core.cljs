(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf] 
            [blocks-editor.view :as v]

            [Blockly :as b]))

(defn get-e [s]
  (js/document.getElementById s))

(defn ^:export init!
  [settings]
  (reagent/render
   [v/ui]
   (get-e "app"))
  (.done (.ajax js/$ (clj->js {:url "assets/xml/toolbar.xml"}))
         #(b/inject (get-e "blocklyDiv")
                    (clj->js {:toolbox %}))))

