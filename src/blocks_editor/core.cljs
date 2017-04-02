(ns blocks-editor.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blockly.core :as b]))

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
                          :margin-bottom 0}}
              [:em "super"]
              "MENU"]]]
   [:div#blocklyDiv {:style blockly-style}]])

(defn ^:export init!
  [settings]
  (reagent/render
   [ui]
   (get-e "app"))
  ;; (.inject b/Blockly
  ;; (get-e "blocklyDiv")
  ;; (clj->js {:toolbox (get-e "toolbox")}))
  )

