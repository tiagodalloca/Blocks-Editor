(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.styles :as s]))

(def menu-button-size "60px")

(defn menu-button
  [icon event]
  [:button (merge {:on-click #(rf/dispatch [event])}
                  (s/menu-button menu-button-size))
   [:img (s/img icon)]])

(defn ui
  []
  [:div s/body
   [:div
    [:center [:nav#menu s/menu
              [menu-button "assets/icons/open.png"
               :open-file]
              [menu-button "assets/icons/new.png"
               :new-file]
              [menu-button "assets/icons/save.png"
               :save-file]
              [menu-button "assets/icons/compile.png"
               :compile]]]]
   [:div#blocklyDiv s/blockly]])

