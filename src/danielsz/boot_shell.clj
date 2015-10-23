(ns danielsz.boot-shell
  {:boot/export-tasks true}
  (:require
   [me.raynes.conch :refer [with-programs]]
   [clojure.data.codec.base64 :as b64]
   [boot.util       :as util]
   [boot.core       :as core]))

(defn encode [string]
  (String. (b64/encode (.getBytes string))))

(defn decode [string]
  (String. (b64/decode (.getBytes string))))

(core/deftask shell
  "Runs a shell script, optionally as root"
  [s script VAL str "the shell script to run"
   p password VAL str "the root password"]
  (core/with-pre-wrap fileset
    (let [out-files (core/output-files fileset)
          script  (first (core/by-name [script] out-files))]
      (util/info (str "Shelling out " (core/tmp-path script)))
      (with-programs [echo sudo sh]
        (if password
          (sudo "-S" "sh" (core/tmp-path script) {:in (echo (decode password))
                                                  :dir (core/tmp-dir script)})
          (sh (core/tmp-path script) {:dir (core/tmp-dir script)}))))
    fileset))
