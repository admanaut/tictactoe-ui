(ns tictactoe-ui.handlers
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [tictactoe-ui.api :as api]
            [tictactoe-ui.model :as model]
            [cljs.core.async :as a :refer [<!]]))

(defn decorate-win-moves
  [board win-moves]
  (reduce (fn [acc coord]
            (update-in acc coord (fn [v] (keyword (str (name v) "*")))))
          board win-moves))

(def handlers
  {:game/start-pressed
   (fn [[_ !db]]
     (go
       (let [response (<! (model/<new-game (api/size @!db)))]
         (swap! !db assoc
                :board (:body response)
                :result nil))))

   :game/cell-clicked
   (fn [[_ !db coords]]
     (go
       (let [player (api/player @!db)
             board (api/board @!db)
             next-player ({"x" "0" "0" "x"} player)
             valid-move? (:body (<! (model/<valid-move board coords)))]
         (when valid-move?
           (let [new-board (:body (<! (model/<move board coords player)))
                 winner (:body (<! (model/<winner new-board)))]
             (if-let [[w win-seq] winner]
               (swap! !db assoc
                      :result w
                      :board (decorate-win-moves new-board win-seq))
               (swap! !db assoc
                      :board new-board
                      :player next-player)))))))})
