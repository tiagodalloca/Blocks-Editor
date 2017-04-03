(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.styles :as s]))

(defn ui
  []
  [:div
   [:div#menu {:style s/menu}
    [:center [:p [:em "super  "] "MENU!"]]]
   [:div#blocklyDiv {:style s/blockly}]])

