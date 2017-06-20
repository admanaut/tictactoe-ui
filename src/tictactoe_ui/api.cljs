(ns tictactoe-ui.api)

(defn board
  [db]
  (get-in db [:board]))

(defn player
  [db]
  (get-in db [:player]))

(defn size
  [db]
  (get-in db [:size]))

(defn result
  [db]
  (get-in db [:result]))
