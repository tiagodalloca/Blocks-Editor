(ns blocks-editor.misc)

(defonce node-require (js* "require"))
(defonce bootbox (node-require "bootbox"))
(defonce b-prompt (.-prompt bootbox))

(-> js/window .-prompt
   (set! (fn [title default]
           (let [ret (atom nil)
                 callback #(reset! ret
                                   (if (and % (empty? %))
                                     %
                                     default))
                 wait (fn [] nil)]
             (b-prompt title callback)
             ret))))

