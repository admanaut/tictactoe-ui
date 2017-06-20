(ns tictactoe-ui.events
  (:require [tictactoe-ui.handlers :refer [handlers]]))

(defn handle-event
  [id event]
  (when-let [handler (get handlers id)]
    (handler event)))

(defn emit!
  [[id :as event] ]
  (handle-event id event))
