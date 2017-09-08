(ns blocks-editor.blocks)

(def new-blocks
  [{:type "game_init" 
    :message0 "%1"
    :args0 ["INIT"]
    :nextStatement nil
    :colour 30
    :tooltip "It's called when creating an instace of your robot"
    :helpUrl ""}

   {:type "game_update"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1 %2"
    :args0 ["UPDATE"
            {:type "field_variable", :name "state", :variable "state"}]
    :nextStatement nil
    :colour 30
    :tooltip "Receive the game state and updates it with an action"
    :helpUrl ""}

   {:type "game_return"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1 %2"
    :args0 ["return action"
            {:type "input_value", :name "ACTION", :check "ActionBlock"}]
    :previousStatement nil
    :colour 30
    :tooltip "Return an action to be handled by the game"
    :helpUrl ""}

   {:type "game_action"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1 %2 %3 %4"
    :args0 ["action"
            {:type "field_input", :name "ACTION_NAME", :text "action_name", :spellcheck false}
            "args"
            {:type "input_value"
             :name "ARGS"
             :check "Array"}]
    :output "ActionBlock"
    :colour 0
    :tooltip "Something to do and its args"
    :helpUrl ""}

   {:type "associative_get"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1 %2 %3 %4"
    :args0 ["get"
            {:type "field_input", :name "KEY", :text "key", :spellcheck false}
            "from"
            {:type "input_value"
             :name "ASSOCIATIVE"
             :check "Associative"}]
    :colour 200
    :output ""
    :tooltip "Gets something (key) from a dictionary"
    :helpUrl ""}

   {:type "associative_set"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1 %2 %3 %4 %5 %6"
    :args0 ["set"
            {:type "field_input", :name "KEY", :text "key", :spellcheck false}
            "from"
            {:type "input_value"
             :name "ASSOCIATIVE"
             :check "Associative"}
            "to"
            {:type "input_value", :name "VALUE"}]
    :colour 200
    :output ""
    :tooltip "Stores something into a dictionary with under a label (key)"
    :helpUrl ""}

   {:type "associative_new"
    :lastDummyAlign0 "RIGHT"
    :message0 "%1"
    :args0 ["create new dictionary"]
    :output "Associative"
    :colour 200
    :tooltip "Creates a new dictionary"
    :helpUrl ""}])

(defn init-blocks! []
  (defonce goog (js* "goog"))
  (.require goog "Blockly.Blocks")
  (defonce bblocks (js* "Blockly.Blocks"))
  (defonce bsvg (js* "Blockly.BlockSvg"))
  
  (doseq [{:keys [type] :as b} new-blocks]
    (aset bblocks type
          (clj->js
           {:init (fn []
                    (this-as this
                      (->> b clj->js (.jsonInit this))))})))
  
  (set! (.-START_HAT bsvg) true))
