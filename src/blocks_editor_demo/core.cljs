(ns blocks-editor-demo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

(defn ui []
  [:div#app-wrapper
   [:h1 "Hello world!"]
   [:p "Basic hw"]
   [:p "A prova não eh pra semana que vem"]
   [:marquee
    [:h2 "Obama"]
    [:a {:href "http://github.com"} "GitHub"]]])

(defn init! [setting]
  (rf/dispatch-sync [:initialize])
  (reagent/render [ui] (js/document.getElementById "app")))

