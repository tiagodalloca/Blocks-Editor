(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.view.elements :as ve]))

(defonce node-require (js* "require"))

(defonce menu-button-size "60px")

(defn menu-button
  [icon tip event]
  [:button (assoc (ve/menu-button menu-button-size)
                  :title tip
                  :on-click #(rf/dispatch [event]))
   [:span (assoc (ve/icon icon) :style {:font-size "30px"})]])

(defn chooser
  [id title items]
  (reduce conj [:select {:id id
                         :placeholder title
                         :class "form-control"}]
          (reduce (fn [acc [l v]]
                    (conj acc [:option {:value v} l]))
                  [] (partition 2 items))))

(defn ui
  []
  [:div ve/body
   [:div
    [:div#menu ve/menu
     [menu-button "glyphicon-folder-open"
      "Open file"
      :open-file]
     [menu-button "glyphicon-floppy-disk"
      "Save file"
      :save-file]
     [menu-button "glyphicon-cog" 
      "Compile"
      :compile]
     [chooser "robot-select" "Select your robot"
      ["Simple" "simple"
       "Advanced" "advanced"]]]]
   [:div#blocklyDiv ve/blockly]])

