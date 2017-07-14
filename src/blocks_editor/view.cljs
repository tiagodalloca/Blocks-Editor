(ns blocks-editor.view
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [blocks-editor.view.elements :as ve]
            [goog.math :as math]
            [blocks-editor.db]))

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

(defn robot-name
  [default-value]
  (let [rname (rf/subscribe [:robot-name])]
    (fn [] [:input {:class "form-control robot-name"
                   :type "text"
                   :defaultValue default-value
                   :value @rname
                   :on-change #(rf/dispatch
                                [:update-robot-name
                                 (-> % .-target .-value)])}])))

(defn ui
  []
  [:div ve/body
   [:div
    [:div#menu ve/menu
     [:div {:style {:padding "6px 0px"}}
      [menu-button "glyphicon-folder-open"
       "Open file"
       :open-file]
      [menu-button "glyphicon-floppy-disk"
       "Save file"
       :save-file]
      [menu-button "glyphicon-cog" 
       "Compile"
       :compile]]
     [robot-name (str "Random robot #" (math/randomInt 100))]]]
   [:div#blocklyDiv ve/blockly]])

