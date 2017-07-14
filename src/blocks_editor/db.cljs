(ns blocks-editor.db
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :robot-name
 (fn [db _]
   (-> db
      :robot-name)))
