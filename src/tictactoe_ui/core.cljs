(ns tictactoe-ui.core
  (:require [reagent.core :as r]
            [reagent.ratom :refer-macros [reaction]]
            [tictactoe-ui.api :as api]
            [tictactoe-ui.events :as ev]))

(enable-console-print!)

(def initial-state {:board nil
                    :player "x"
                    :size 3
                    :result nil})

(defonce app-state (r/atom initial-state))

(defn board-comp
  [board]
  (when board
    [:table {:style {:border-collapse "collapse" :width "75%" :height "75%"}}
     (into [:tbody]
           (reduce-kv (fn [acc idx row]
                        (conj acc
                              [:tr {}
                               (map-indexed (fn [idxx c]
                                              ^{:key (str "cell-" idx "-" idxx)}
                                              [:td
                                               {:style {:border "1px solid black" :height "50px" :vertical-align "centre"}
                                                :on-click #(ev/emit! [:game/cell-clicked app-state [idx idxx]])}
                                               (when-let [val (get-in board [idx idxx])]
                                                 val)])
                                            row)]))
                      []
                      board))]))

(defn game
  [!db]
  (let [!board (reaction (api/board @!db))
        !player (reaction (api/player @!db))
        !result (reaction (api/result @!db))]
    [:div
     [:h1 "!!! Welcome to tic-tac-toe !!!"]
     [:button {:on-click #(ev/emit! [:game/start-pressed app-state])} "Start!"]
     (when @!player
       [:h3 (str "It's your turn " @!player " !")])
     [board-comp @!board]
     (when @!result
       [:div
        (case @!result
          :draw [:h2 "It's a draw ! Congratulations you're equally smart ;-|"]
          [:h2 (str "Congratulations " @!result " you're awesome !")])])]))


(r/render-component [game app-state]
                    (. js/document (getElementById "app")))
