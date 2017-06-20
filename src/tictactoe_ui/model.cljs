(ns tictactoe-ui.model
  (:require [cljs-http.client :as http]))


(defn <new-game
  [size]
  (http/get (str "http://localhost:4444/new-game/" size)
            {:headers {"accept" "application/json"}}))

(defn <valid-move
  [board move]
  (http/put (str "http://localhost:4444/valid-move")
            {:headers {"accept" "application/json"}
             :json-params {"move" move "board" board}}))

(defn <move
  [board move player]
  (http/put (str "http://localhost:4444/move")
            {:headers {"accept" "application/json"}
             :json-params {"move" move "board" board "player" player}}))

(defn <winner
  [board]
  (http/put (str "http://localhost:4444/winner")
            {:headers {"accept" "application/json"}
             :json-params {"board" board}}))
