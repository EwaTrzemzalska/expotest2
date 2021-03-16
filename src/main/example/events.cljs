(ns example.events
  (:require
   [re-frame.core :refer [reg-event-db after]]
   [clojure.spec.alpha :as s]
   [example.db :as db :refer [app-db]]))

;; -- Interceptors ------------------------------------------------------------
;;
;; See https://github.com/Day8/re-frame/blob/master/docs/Interceptors.md
;;
(defn check-and-throw
  "Throw an exception if db doesn't have a valid spec."
  [spec db [event]]
  (when-not (s/valid? spec db)
    (let [explain-data (s/explain-data spec db)]
      (throw (ex-info (str "Spec check after " event " failed: " explain-data) explain-data)))))

;; (def validate-spec
;;   (if goog.DEBUG
;;     (after (partial check-and-throw ::db/app-db))
;;     []))

;; -- Handlers --------------------------------------------------------------

(reg-event-db
 :initialize-db
;;  validate-spec
 (fn [_ _]
   app-db))

(reg-event-db
 :inc-counter
;;  validate-spec
 (fn [db [_ _]]
   (update db :counter inc)))

(reg-event-db
 :add-todo
 (fn [db [_ todo-title]]
   (let [id (random-uuid)
         todo {:title todo-title
               :done false}]
     (-> db
         (update :todos-by-id assoc id todo)
         (update :todos-by-order conj id)
         (assoc :input "")))))

(reg-event-db
 :switch-done
 (fn [db [_ id]]
   (update-in db [:todos-by-id id :done] not)))

(reg-event-db
 :update-input
 (fn [db [_ user-input]]
   (assoc db :input user-input)))

(reg-event-db
 :remove-todo
 (fn [db [_ todo-id]]
   (-> db
       (update :todos-by-order (fn [todos-by-order]
                                 (remove #{todo-id} todos-by-order))))))