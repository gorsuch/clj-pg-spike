(ns pg-spike.core
  (:use [korma.db]
        [korma.core]
        [cheshire.core]))

(defdb db (postgres { :db "gorsuch" }))

(def create-string
  "create table if not exists report (id SERIAL PRIMARY KEY, token varchar, created_at integer, data text)")

(defn createdb! []
  (exec-raw create-string))

(def drop-string "drop table report")

(defn dropdb! []
  (exec-raw drop-string))

(defentity report)

(defn now []
  (int (/ (System/currentTimeMillis) 1000)))

(defn uuid []
  (str (java.util.UUID/randomUUID)))

(defn insert-report [data]
  (insert report (values {:token (uuid) :created_at (now) :data (generate-string data)})))

(defn select-report [token]
  (first (select report (where {:token token}))))

(defn select-report-data [token]
  (parse-string (:data (select-report token)) true))