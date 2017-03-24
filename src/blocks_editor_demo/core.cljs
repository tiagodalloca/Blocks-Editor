(ns blocks-editor-demo.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]))

;; ;; A detailed walk-through of this source code is provied in the docs:
;; ;; https://github.com/Day8/re-frame/blob/master/docs/CodeWalkthrough.md

;; ;; -- Domino 1 - Event Dispatch -----------------------------------------------

;; (defn dispatch-timer-event
;;   []
;;   (let [now (js/Date.)]
;;     (rf/dispatch [:timer now])))  ;; <-- dispatch used

;; ;; Call the dispatching function every second.
;; ;; `defonce` is like `def` but it ensures only instance is ever
;; ;; created in the face of figwheel hot-reloading of this file.
;; (defonce do-timer (js/setInterval dispatch-timer-event 1000))


;; ;; -- Domino 2 - Event Handlers -----------------------------------------------

;; (rf/reg-event-db              ;; sets up initial application state
;;  :initialize                 ;; usage:  (dispatch [:initialize])
;;  (fn [_ _]                   ;; the two parameters are not important here, so use _
;;    {:time (js/Date.)         ;; What it returns becomes the new application state
;;     :time-color "#f88"}))    ;; so the application state will initially be a map with two keys


;; (rf/reg-event-db                ;; usage:  (dispatch [:time-color-change 34562])
;;  :time-color-change            ;; dispatched when the user enters a new colour into the UI text field
;;  (fn [db [_ new-color-value]]  ;; -db event handlers given 2 parameters:  current application state and event (a vector)
;;    (assoc db :time-color new-color-value)))   ;; compute and return the new application state


;; (rf/reg-event-db                 ;; usage:  (dispatch [:timer a-js-Date])
;;  :timer                         ;; every second an event of this kind will be dispatched
;;  (fn [db [_ new-time]]          ;; note how the 2nd parameter is desctructure to obtain the data value
;;    (assoc db :time new-time)))  ;; compute and return the new application state


;; ;; -- Domino 4 - Query  -------------------------------------------------------

;; (rf/reg-sub
;;  :time
;;  (fn [db _]     ;; db is current app state. 2nd usused param is query vector
;;    (-> db
;;        :time)))

;; (rf/reg-sub
;;  :time-color
;;  (fn [db _]
;;    (:time-color db)))


;; ;; -- Domino 5 - View Functions ----------------------------------------------

;; (defn clock
;;   []
;;   [:div.example-clock
;;    {:style {:color @(rf/subscribe [:time-color])}}
;;    (-> @(rf/subscribe [:time])
;;        .toTimeString
;;        (clojure.string/split " ")
;;        first)])

;; (defn color-input
;;   []
;;   [:div.color-input
;;    "Time color: "
;;    [:input {:type "text"
;;             :value @(rf/subscribe [:time-color])
;;             :on-change #(rf/dispatch [:time-color-change (-> % .-target .-value)])}]])  ;; <---

;; (defn ui
;;   []
;;   [:div
;;    [:h1 "Hello world, it is now"]
;;    [:h2 {:style  {:color "green"}}
;;     [clock]
;;     [:p "THE CLOCK! oi "]]
;;    [color-input]])

;; ;; -- Entry Point -------------------------------------------------------------

(def types
  {:int "Integer"
   :decimal "Decimal" 
   :string "String"
   :bool "Boolean"

   :array "Array"
   :object "Object"})

(defn block
  [label class]
  [:div {:class (str "block" \space class)}
   [:div.label label]])

(defn select
  [options-values]
  (into [:select] (reduce (fn [acc [k v]]
                            (conj acc [:option {:value v} k]))
                          [] options-values)))

(defn method-block
  [name & commands]
  (block (into [:div "METHOD"
                [:input {:type "text" :value name}]]
               commands)
         "method-block"))

(defn var-block
  [name]
  (block [:div ""
          (select (map (fn [[k v]] [v v]) types))
          [:input {:type "text" :value name}]]
         "var-block"))

(defn while-block
  [& commands]
  (block (into [:div "WHILE" [:br]] commands) "while-block"))

(defn command-block
  [name arity]
  (block 
   (into [:div name]
         (take arity
               (repeat [:input {:type "text"}])))
   "command-block"))

(defn ui
  []
  [:div#canvas
   (method-block "Método exemplo")
   (var-block "x")
   (while-block (command-block "LESS THAN" 2) 
     (command-block "INC" 1)
     (command-block "RETURN" 1))])

(defn ^:export init!
  [settings]
  ;; (rf/dispatch-sync [:initialize])     ;; puts a value into application state
  (reagent/render
   [ui]
   (js/document.getElementById "app")))


