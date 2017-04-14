(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.styles :as s]))

(def menu-button-size "60px")

(defn menu-button
  [icon]
  [:button (s/menu-button menu-button-size) [:img (s/img icon)]])

(defn ui
  []
  [:div s/body
   [:div
    [:center [:nav#menu s/menu
              [menu-button "assets/icons/open.png"]
              [menu-button "assets/icons/new.png"]
              [menu-button "assets/icons/save.png"]
              [menu-button "assets/icons/compile.png"]]]]
   [:div#blocklyDiv s/blockly]])

