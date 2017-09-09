(ns blocks-editor.blocks)

(def new-blocks
  [{:type "game_init",
    :message0 "INIT",
    :nextStatement nil,
    :colour 30,
    :tooltip "It's called when creating an instace of your robot",
    :helpUrl ""}
   {:type "game_update",
    :lastDummyAlign0 "RIGHT",
    :message0 "UPDATE %1",
    :args0 [{:type "field_variable", :name "state", :variable "state"}],
    :nextStatement nil,
    :colour 30,
    :tooltip "Receive the game state and updates it with an action",
    :helpUrl ""}
   {:type "game_return",
    :lastDummyAlign0 "RIGHT",
    :message0 "return action %1",
    :args0 [{:type "input_value", :name "ACTION", :check "ActionBlock"}],
    :previousStatement nil,
    :colour 30,
    :tooltip "Return an action to be handled by the game",
    :helpUrl ""}
   {:type "game_action",
    :lastDummyAlign0 "RIGHT",
    :message0 "action %1 args %2",
    :args0
    [{:type "field_input",
      :name "ACTION_NAME",
      :text "action_name",
      :spellcheck false}
     {:type "input_value", :name "ARGS", :check "Array"}],
    :output "ActionBlock",
    :colour 0,
    :tooltip "Something to do and its args",
    :helpUrl ""}
   {:type "associative_get",
    :lastDummyAlign0 "RIGHT",
    :message0 "get %1 from %2",
    :args0
    [{:type "field_input", :name "KEY", :text "key", :spellcheck false}
     {:type "input_value", :name "ASSOCIATIVE", :check "Associative"}],
    :colour 200,
    :output "",
    :tooltip "Gets something (key) from a dictionary",
    :helpUrl ""}
   {:type "associative_set",
    :lastDummyAlign0 "RIGHT",
    :message0 "set %1 from %2 to %3",
    :args0
    [{:type "field_input", :name "KEY", :text "key", :spellcheck false} 
     {:type "input_value", :name "ASSOCIATIVE", :check "Associative"}
     {:type "input_value", :name "VALUE"}],
    :colour 200,
    :output "",
    :tooltip
    "Stores something into a dictionary with under a label (key)",
    :helpUrl ""}
   {:type "associative_new",
    :lastDummyAlign0 "RIGHT",
    :message0 "create new dictionary",
    :output "Associative",
    :colour 200,
    :tooltip "Creates a new dictionary",
    :helpUrl ""}
   {:type "associative_from_list",
    :lastDummyAlign0 "RIGHT",
    :message0 "create new dictionary from a list %1",
    :args0 [{:type "input_value", :name "LIST", :check "Array"}],
    :output "Associative",
    :colour 200,
    :tooltip "Creates a new dictionary from a list",
    :helpUrl ""}
   {:type "associative_kv",
    :lastDummyAlign0 "LEFT",
    :message0 "key-value %1 %2",
    :args0
    [{:type "field_input", :name "KEY", :text "key", :spellcheck false}
     {:type "input_value", :name "VALUE"}],
    :output "KVPair",
    :colour 200, 
    :tooltip "Creates a new kvpair", 
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

