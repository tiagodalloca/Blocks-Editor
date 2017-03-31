(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(def blockly (js* "Blockly"))

(def blockly-style {:height 600
                    :width "100%"})

(defn get-e [s]
  (js/document.getElementById s))

(defn ui
  []
  [:div
   [:div#menu {:style {:position "relative"
                       :top 0
                       :height 100
                       :width "100%"}}
    [:center [:p {:style {:position "relative"
                          :top 30
                          :margin-bottom 0}} "_super_ MENU"]]]
   [:div#blocklyDiv {:style blockly-style}]])

(defn ^:export init!
  [settings]
  (reagent/render
   [ui]
   (get-e "app"))
  (.inject blockly
           (get-e "blocklyDiv")
           (clj->js {:toolbox (get-e "toolbox")})))

