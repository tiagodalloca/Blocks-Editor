(ns ^:figwheel-load blocks-editor.events
  (:require [re-frame.core :as rf]
            [blocks-editor.core :as c]
            [blocks-editor.utils :refer [node-require fs
                                         read-file
                                         select-save-file
                                         select-open-file
                                         select-dir
                                         save-to-file
                                         delete
                                         dirname
                                         call-system]]
            
            [Blockly.Xml :as bx]
            [Blockly.Variables :as bv]

            [cljs.core.async :as async
             :refer [put! chan tap mult <! >! timeout close!]])
  (:require-macros [cljs.core.async.macros
                    :refer [go go-loop alt!]]))

(defonce dialog (-> "electron" node-require .-remote .-dialog))

(defn save-workspace! [path]
  (when path
    (let [db @re-frame.db/app-db
          wdom (-> c/workspace bx/workspaceToDom)
          vars (.createElement js/document "meta")
          svars  (-> c/workspace bv/allUsedVariables .join)
          tvars (.createTextNode js/document svars)

          rname (.createElement js/document "meta") 
          srname (:robot-name db)
          trname (.createTextNode js/document srname)]
      
      (doto vars
        (.appendChild tvars)
        (.setAttribute "type" "variables_used"))
      (doto rname
        (.appendChild trname)
        (.setAttribute "type" "robot_name"))
      
      (.insertBefore wdom vars (.-firstChild wdom))
      (.insertBefore wdom rname vars)
      (save-to-file path (bx/domToPrettyText wdom)))))

;; EVENTS

(rf/reg-event-db
 :init-db
 (fn [_ _]
   {:robot-name nil}
   {:config nil}))

(rf/reg-event-fx
 :setup-config
 (fn [cofx [_ path]] 
   {:load-config path }))

(rf/reg-fx
 :load-config
 (fn [path] 
   (go (try (rf/dispatch
             [:set-config (some-> (<! (read-file path))
                                  cljs.reader/read-string)])
            (catch js/Error e
              (-> "config.edn: \n" (str e) js/alert))))))

(rf/reg-fx :alert js/alert)

(rf/reg-fx
 :call-compiler
 (fn [{:keys [name mono? compiler-path]}] 
   (let [temp-file-name  (str (dirname js/__dirname)
                              "/__temp-thing-to-save__")]
     (go (when-let [o (some-> (<! (select-dir)) (aget 0))] 
           (if-let [err (<! (save-workspace! temp-file-name))]
             (js/alert err)             
             (let [call-vec ["-i" (str \"temp-file-name\")
                             "-o" o
                             "-n" (str \"name "-lib" \")]
                   {:keys [err stderr stdout]}
                   (<! (call-system (str (when mono? "mono ")
                                         \"compiler-path\")  
                                    call-vec))] 
               (when err
                 (js/alert (str "Error to call compiler:\n"
                                err)))
               (when-not (empty? stderr)
                 (js/alert (str "Error compilig:\n"
                                stderr))) 
               (delete temp-file-name)))))))

 (rf/reg-event-db
  :set-config
  (fn [db [_ v]]
    (assoc db :config v))))

(rf/reg-event-db
 :update-robot-name
 (fn [db [_ v]]
   (assoc db :robot-name v)))

(rf/reg-event-fx
 :compile
 (fn [{:keys [db]} _] 
   (if-let [config (get db :config)]
     {:call-compiler (assoc config :name
                            (get db :robot-name))}
     {:alert (str "Bad formatted config.edn (or none at all)")})))

;; ===
;; THE UNGLY AND OLD
;; ===
;; ...

(rf/reg-event-db
 :open-file
 (fn [db [_ _]] 
   (.showOpenDialog
    dialog
    #(when %
       (.readFile
        fs (aget % 0) "utf-8"
        (fn [err content]
          (if-not err
            (let [wdom (bx/textToDom content)
                  rname (some-> wdom .-children (aget 0)
                                .-innerHTML)] 
              (.clear c/workspace)
              (bx/domToWorkspace wdom c/workspace)
              (rf/dispatch [:update-robot-name rname]))
            (do (js/alert (str
                           "Sorry, could not open your file: \n"
                           err))))))))
   db))

(rf/reg-event-db
 :save-file
 (fn [db [_ _]] 
   (if (.-getTopBlocks c/workspace) 
     (.showSaveDialog dialog save-workspace!)
     (js/alert "There's no blocks to save"))
   db))

