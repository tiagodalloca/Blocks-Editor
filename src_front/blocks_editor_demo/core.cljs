(ns blocks-editor-demo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn ui []
  [:div#app-wrapper
   [:h1 "Hello world"]])

(defn init! [setting]
  (rf/dispatch-sync [:initialize])
  (reagent/render [ui] (js/document.getElementById "app")))

